# -*- coding: utf-8 -*-
"""
    this is a spider for crawling news from tencent.
"""
import re
import scrapy
from newsspider.items import NewsspiderItem
from scrapy.http import Request

categories = ['24hours', 'antip', 'bj', 'ent', 'sports',
              'milite', 'world', 'nba', 'tech', 'finance', 'auto']


class TencentSpider(scrapy.Spider):
    """
        a spider for tencent news
    """
    name = 'tencent'
    allowed_domains = ['qq.com']
    limit = 10
    # 来存放所有的js包地址
    allLink = []
    for category in categories:
        # 构造每个js文件的地址
        url = 'https://i.news.qq.com/trpc.qqnews_web.kv_srv.kv_srv_' \
              'http_proxy/list?sub_srv_id=' + category + \
              '&srv_id=pc&offset=0&limit=' + str(limit) + '&strategy=1&ext={%22pool%22:' \
              '[%22top%22,%22top%22],%22is_filter%22:10,%22check_type%22:true}'
        allLink.append(url)  # j将构造的js包网址追加到allLink列表中

    def start_requests(self):
        # 爬取不同栏目下的数据
        for i, link in enumerate(self.allLink):
            yield Request(link, meta={'category': categories[i]}, callback=self.next)

    def next(self, response):
        """
        :param response:
        :return:
        """
        # 获取每个js包内存放的新闻标题和新闻链接
        data = response.body.decode("utf-8", "ignore")
        link_pat = '"url":"(.*?)"'
        links = re.compile(link_pat).findall(data)
        category = response.meta["category"]
        for link in links:
            yield Request(link, meta={'category': category}, callback=self.get_detail)


    def get_detail(self, response):
        """
        :param response:
        :return:
        """
        item = NewsspiderItem()
        sel = response.selector
        title = sel.xpath("//div[@class='LEFT']/h1/text()").extract()
        summary = "".join(sel.xpath("//p[@class='one-p']/text()").extract())
        img_list = sel.xpath("//img[@class='content-picture']/@src").extract()
        img = "" if len(img_list) == 0 else img_list[0]
        src = sel.xpath(".").re(r'"media":\s*"(\S*)",?')
        pubtime = sel.xpath(".").re(r'"pubtime":\s*"(.*)",?')
        newsid = sel.xpath(".").re(r'"cms_id":\s*"(\S*)",?')

        pattern = re.compile(r"\\n|'|‘|’|“|”")
        summary = re.sub(pattern, "", summary)
        pattern = re.compile(r'\s+')
        summary = re.sub(pattern, " ", summary)

        if len(newsid) != 0:
            item["link"] = response.url
            item["title"] = title[0]
            item["summary"] = summary
            item["pic_url"] = img
            item["src"] = "未知" if len(src) == 0 else src[0]
            item["time"] = pubtime[0]
            item["newsid"] = newsid[0]
            item["category"] = response.meta["category"]
            yield item
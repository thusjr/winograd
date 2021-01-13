"""
    this is a spider for crawling news from xinhua.
"""
import re
import scrapy
from xinhuaSpider.items import XinhuaspiderItem


class XinhuaSpider(scrapy.Spider):
    """
        a spider for xinhua news
    """
    name = 'xinhua'
    allowed_domains = ['xinhuanet.com']
    allLink = []
    for i in range(1, 5):
        url = "http://qc.wa.news.cn/nodeart/list?nid=113352&pgnum=" + str(i) + \
              "&cnt=10&tp=1&orderby=1?callback=jQuery1124014630931162188587_" \
              "1605770215435&_=1605770215" + str(i+435)
        allLink.append(url)
    for i in range(1, 101):
        url = "http://qc.wa.news.cn/nodeart/list?nid=113321&pgnum=" + str(i) + \
              "&cnt=10&tp=1&orderby=1?callback=jQuery112409060286339067016_" \
              "1605771995402&_=1605771995" + str(i+402)
        allLink.append(url)
    for i in range(1, 101):
        url = "http://qc.wa.news.cn/nodeart/list?nid=113207&pgnum=" + str(i) + \
              "&cnt=10&tp=1&orderby=1?callback=jQuery112405961146821543153_" \
              "1605772541851&_=1605772541" + str(i+851)
        allLink.append(url)
    for i in range(1, 70):
        url = "http://qc.wa.news.cn/nodeart/list?nid=11147664&pgnum=" + str(i) + \
              "&cnt=16&tp=1&orderby=1?callback=jQuery17106396932329474299_1605772998" + str(i+616)
        allLink.append(url)
    for i in range(1, 101):
        url = "http://qc.wa.news.cn/nodeart/list?nid=11209214&pgnum=" + str(i) + \
              "&cnt=10&tp=1&orderby=1?callback=jQuery112409528316708376174_" \
              "1605773791582&_=1605773791" +str(i+582)
        allLink.append(url)
    for i in range(1, 34):
        url = "http://da.wa.news.cn/nodeart/page?nid=11214127&pgnum=" + str(i) + \
              "&cnt=6&attr=63&tp=1&orderby=1&callback=jQuery1124046111873572596984_" \
              "1605773965422&_=1605773965" + str(i+424)
        allLink.append(url)

    def __init__(self):
        super().__init__()
        self.record = 0
        self.timer = 0

    def start_requests(self):
        for link in self.allLink:
            yield scrapy.Request(url=link, callback=self.first)

    def first(self,response):
        """
        :param response:
        :return:
        """
        data = response.body.decode("utf-8", "ignore")
        link_pat = '"LinkUrl":"(.*?)"'
        links = re.compile(link_pat).findall(data)
        for link in links:
            yield scrapy.Request(url=link, callback=self.next, meta={"handle_httpstatus_all": True})


    def next(self, response):
        """
        解析得到的html文件
        :param response:
        :return:
        """
        item = XinhuaspiderItem()
        sel = response.selector
        title = sel.xpath("//div[@class='h-title']/text()").extract()  # 解析标题
        summary = "".join(sel.xpath("//div[@id='p-detail']/p/text()").extract())  # 正文
        img_list = sel.xpath("//div[@id='p-detail']/p//img/@src").extract()
        img = "" if len(img_list) == 0 else response.url[:-16]+img_list[0]  # 第一张图片链接
        pubtime = sel.xpath("//span[@class='h-time']/text()").extract()  # 发布时间
        newsid = sel.xpath("head/meta[@name='publishid']/@content").extract()  # 新闻id

        pattern = re.compile(r"\\n|'|‘|’|“|”")
        summary = re.sub(pattern, "", summary)
        pattern = re.compile(r'\s+')
        summary = re.sub(pattern, " ", summary)

        if len(newsid) != 0 and len(title) != 0:
            item["link"] = response.url
            item["title"] = title[0].strip()
            item["summary"] = summary
            item["pic_url"] = img
            item["src"] = "新华网"
            item["time"] = pubtime[0]
            item["newsid"] = newsid[0]
            yield item

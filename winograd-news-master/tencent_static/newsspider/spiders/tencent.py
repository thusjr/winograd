# -*- coding: utf-8 -*-
'''
    this is a spider for crawling news from tencent.
'''
import re
from newsspider.items import *
from scrapy.http import Request

import time
from datetime import date
import re

'''
    a spider for tencent news
'''


class TencentSpider(scrapy.Spider):
    name = 'tencent'
    allowed_domains = ['qq.com']

    def __init__(self):
        super().__init__()
        self.record = 0
        self.timer = 0

    def isValidDate(self, year, month, day):
        try:
            date(year, month, day)
        except:
            return False
        else:
            return True

    def dateGen(self):
        for year in range(2020, 2017, -1):
            for month in range(12, 0, -1):
                for day in range(31, 0, -1):
                    if not self.isValidDate(year, month, day):
                        continue
                    t = "%04d%02d%02d" % (year, month, day)
                    today = time.strftime("%Y%m%d", time.localtime())
                    if t > today:
                        continue
                    yield t

    def tokenGen(self):
        charset = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                   'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
        for i in charset:
            for j in charset:
                for k in charset:
                    for m in charset:
                        yield f"{i}{j}{k}{m}"

    def preDate(self, curDate):
        year = int(curDate[0:4])
        month = int(curDate[4:6])
        day = int(curDate[6:8])
        day -= 1
        while not self.isValidDate(year, month, day):
            if day <= 0:
                day = 31
                month -= 1
            else:
                day -= 1
            if month <= 0:
                month = 12
                year -= 1
        return "%04d%02d%02d" % (year, month, day)

    def start_requests(self):
        # 生成date和中间的一段来获取新闻
        # url = "https://new.qq.com/rain/a/20200705A0001V00.html" # 测试用例
        # yield scrapy.Request(url=url, callback=self.next, meta={"handle_httpstatus_all": True})
        _date = "20201026"
        for _token in self.tokenGen():
            time_interval = (time.time() - self.timer)
            if time_interval > 60 or self.record > 1e5: # 超过一分钟没有新的内容就更新日期,或者超过10万条了
                _date = self.preDate(_date)
                print(_date)
                self.timer = time.time()
                self.record = 0
            url = f'https://new.qq.com/rain/a/{_date}A0{_token}00.html'
            yield scrapy.Request(url=url, callback=self.next, meta={"handle_httpstatus_all": True})


    def next(self, response):
        # 解析得到的html文件. 一个例子是在项目下的example.html
        item = NewsspiderItem()
        sel = response.selector
        data = response.body.decode("utf-8", "ignore")

        title = sel.xpath("//div[@class='LEFT']/h1/text()").extract()  # 解析标题
        summary = "".join(sel.xpath("//p[@class='one-p']/text()").extract())  # 正文
        imgList = sel.xpath("//img[@class='content-picture']/@src").extract()
        img = "" if len(imgList) == 0 else imgList[0]  # 第一张图片链接
        src = sel.xpath(".").re('"media":\s*"(\S*)",?')  # 来源
        pubtime = sel.xpath(".").re('"pubtime":\s*"(.*)",?')  # 发布时间
        newsid = sel.xpath(".").re('"cms_id":\s*"(\S*)",?')  # 新闻id

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

            self.record += 1
            if self.record % 100 == 0:
                print(pubtime[0].split(' ')[0])
                print(self.record)

            self.timer = time.time()

            yield item

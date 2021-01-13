"""
    this is items in scrapy framework

    See documentation in:
    https://docs.scrapy.org/en/latest/topics/items.html
"""

import scrapy


class NewsspiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    """
        NewsspiderItem is the models for scraped items
        link:url of the news
        pic_url:if news contains a picture, this is url of the picture
    """
    title = scrapy.Field()
    src = scrapy.Field()
    time = scrapy.Field()
    link = scrapy.Field()
    pic_url = scrapy.Field()
    summary = scrapy.Field()
    newsid = scrapy.Field() # 新闻id；为了变量名不覆盖python内置的id，故取为newsid

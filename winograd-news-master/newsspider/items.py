"""
    this is items in scrapy framework

    See documentation in:
    https://docs.scrapy.org/en/latest/topics/items.html
"""

import scrapy


class NewsspiderItem(scrapy.Item):
    # define the fields for your item here like:
    """
        NewsspiderItem is the models for scraped items
        id:id of the news
        category
    """
    newsid = scrapy.Field()
    category = scrapy.Field()
    link = scrapy.Field()
    title = scrapy.Field()
    src = scrapy.Field()
    time = scrapy.Field()
    pic_url = scrapy.Field()
    summary = scrapy.Field()
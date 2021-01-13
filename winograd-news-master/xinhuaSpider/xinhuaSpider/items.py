"""
    this is items in scrapy framework

    See documentation in:
    https://docs.scrapy.org/en/latest/topics/items.html
"""
import scrapy


class XinhuaspiderItem(scrapy.Item):
    # define the fields for your item here like:
    """
        XinhuaspiderItem is the models for scraped items
        link:url of the news
        pic_url:if news contains a picture, this is url of the picture
    """
    newsid = scrapy.Field()
    link = scrapy.Field()
    title = scrapy.Field()
    src = scrapy.Field()
    time = scrapy.Field()
    pic_url = scrapy.Field()
    summary = scrapy.Field()

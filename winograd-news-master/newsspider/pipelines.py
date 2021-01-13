"""
    this is pipelines in scrapy framework
"""

# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
import pymysql


class NewsspiderPipeline:
    """
        Define my item pipelines here
    """

    def __init__(self):
        host = 'localhost'
        self.conn = pymysql.connect(host=host, user="winograd", password="winograd", port=32768)
        self.cursor = self.conn.cursor()
        # 如果不存在的话，创建数据库tencent_news
        sql = "CREATE DATABASE IF NOT EXISTS tencent_news;"
        self.cursor.execute(sql)
        self.cursor.execute("USE tencent_news;")

        # 如果不存在的话，建立合适的数据库表（table）
        # id: 每条新闻都有唯一的ID，例如example.html里的cms_id为20200705A0001V00
        # time: mysql的timestamp类型，格式为 YYYY-MM-DD HH:MM:SS，固定为19个字符
        sql = """CREATE TABLE IF NOT EXISTS classified_tencent_news (
                        id         CHAR(20) PRIMARY KEY,
                        title      TEXT,
                        link       TEXT,
                        time       TIMESTAMP,  
                        src        TEXT,
                        pic_url    TEXT,
                        category   TEXT,
                        summary    TEXT
                    )"""

        self.cursor.execute(sql)
        self.conn.commit()

    def process_item(self, item, spider):
        """
        :param item:
        :param spider:if delected, this function may not execute
        :return:
        """
        sql = (
            "insert into classified_tencent_news"
            "(id, title, link, time, src, pic_url, category, summary) "
            "values('{}', '{}', '{}', '{}', '{}', '{}', '{}', '{}')".format(
                item["newsid"],
                item["title"],
                item["link"],
                item["time"],
                item["src"],
                item["pic_url"],
                item["category"],
                item["summary"],
            )
        )
        # 将sql语句写入数据库
        try:
            self.cursor.execute(sql)
            self.conn.commit()
        except Exception as err:
            # 如果发生错误，立即回滚
            self.conn.rollback()
            # 并打印出错误
            print(err)
        return item

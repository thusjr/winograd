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
        host = "localhost"
        # TODO: 在服务器上需要使用使用端口32768，用户名密码均为winograd。本地也可以建一个这样的用户
        self.conn = pymysql.connect(host=host, user="winograd", password="winograd", port=3306)
        self.cursor = self.conn.cursor()
        
        # 如果不存在的话，创建数据库tencent_news
        sql = "CREATE DATABASE IF NOT EXISTS tencent_news;"
        self.cursor.execute(sql)
        self.cursor.execute("USE tencent_news;")

        # 如果不存在的话，建立合适的数据库表（table）
        # id: 每条新闻都有唯一的ID，例如example.html里的cms_id为20200705A0001V00
        # time: mysql的timestamp类型，格式为 YYYY-MM-DD HH:MM:SS，固定为19个字符
        # summary: 可以把所有正文都提取出来保存
        sql = """CREATE TABLE IF NOT EXISTS tencent_news (
                id         CHAR(16) PRIMARY KEY,
                title      TEXT,
                link       TEXT,
                time       TIMESTAMP,  
                src        TEXT,
                summary    TEXT,
                pic_url    TEXT
            )"""
        self.cursor.execute(sql)
        self.conn.commit()

    def process_item(self, item, spider):
        """
        :param item:
        :param spider:
        :return:
        """
        # 每次只插入一条新闻
        sql = (
            "insert into tencent_news(id, title, link, time, src, summary, pic_url) "
            "values('{}', '{}', '{}', '{}', '{}', '{}', '{}')".format(
                item["newsid"],
                item["title"],
                item["link"],
                item["time"],
                item["src"],
                item["summary"],
                item["pic_url"],
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

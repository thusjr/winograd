"""
    初步动态爬取了www.news.qq.com上的新闻列表，包括标题、链接、来源和时间。
    仅使用了sqlite3
"""
import os
import time
import sqlite3
import bs4
import selenium


def boot_webdriver(url):
    """
        功能：启动webdriver，用于爬取动态网页
        参数：网页的url
        返回：driver
    """
    driver = selenium.webdriver.Chrome(executable_path="chromedriver.exe")
    driver.get(url)
    for i in range(1, 400):
        time.sleep(1)
        driver.execute_script("window.scrollTo(window.scrollX, %d);" % (i * 200))
    return driver


def get_data(url):
    """
        功能:根据url获取html文件，数据处理
        参数：url
        返回：列表datalist，其元素为data，data中有title,url,src,time,
              e.g.  datalist = [['震惊！世一大居然……','www.tsinghua.edu.cn','某日报',一天前]]
    """
    datalist = []
    driver = boot_webdriver(url)
    html = driver.page_source
    bs_parser = bs4.BeautifulSoup(html, "html.parser")
    jxtits = bs_parser.find_all("div", {"class": "jx-tit"})[0].find_next_sibling().find_all("li")
    for jxtit in jxtits:
        data = []
        try:
            title = jxtit.find_all("img")[0]["alt"].strip()
        except IndexError:
            title = jxtit.find_all("div", {"class": "lazyload-placeholder"})[0].text.strip()
        data.append(title)
        try:
            url = jxtit.find_all("a")[0]["href"].strip()
        except IndexError:
            print(jxtit)
        data.append(url)
        src = jxtit.find_all("a", class_="source")[0].text.strip() \
            if jxtit.find_all("a", class_="source") else ""
        data.append(src)
        time_ = jxtit.find_all("span", class_="time")[0].text.strip() \
            if jxtit.find_all("span", class_="time") else ""
        data.append(time_)
        datalist.append(data)
    return datalist


def init_db(dbpath):
    """
        功能：初始化数据库
        参数：数据库存储路径
        注意：
    """
    sql = '''
        CREATE TABLE news
        (
            id integer primary key autoincrement,
            title text,
            url text,
            src text,
            time text
        )
    '''
    connect = sqlite3.connect(dbpath)
    cursor = connect.cursor()
    cursor.execute(sql)
    connect.commit()
    connect.close()


def save_data(datalist, dbpath):
    """
        功能：将数据存到数据库中
        参数:要存储的新闻列表，数据库路径
    """
    if not os.path.exists(dbpath):
        init_db(dbpath)
    conn = sqlite3.connect(dbpath)
    cur = conn.cursor()
    for data in datalist:
        data_len = len(data)
        for i in range(data_len):
            data[i] = '"' + data[i] + '"'
        sql = '''
            insert into news(
            title, url, src, time)
            values(%s)''' % ",".join(data)
        cur.execute(sql)
        conn.commit()
    cur.close()
    conn.close()


def return_data(dbpath):
    """
        参数：指定数据库 ，指定返回新闻的序号、属性(如title,url,source,time)
        返回所有新闻，每一条新闻类型为tuple，包含标题、链接、来源、时间
        注：目前暂时只实现了返回全部新闻的所有属性 2020/10/09
    """
    conn = sqlite3.connect(dbpath)
    cursor = conn.cursor()
    sql = '''
        select * from news
    '''
    results = cursor.execute(sql)
    all_news = results.fetchall()
    for news in all_news:
        print(news)


def main():
    """
        功能:根据请求返回新闻
        参数：新闻编号范围/或者限定其他要求，还未与另一后端对接，所以暂时没有实现 2020/10/09
    """
    url = "https://news.qq.com"
    newslist = get_data(url)
    dbpath = "news.db"
    save_data(newslist, dbpath)
    return_data(dbpath)


if __name__ == '__main__':
    main()

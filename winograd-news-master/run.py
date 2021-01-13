"""
    execute the spider every 6000s
"""
import time
import os

while True:
    os.system("scrapy crawl tencent --nolog")
    time.sleep(6000)

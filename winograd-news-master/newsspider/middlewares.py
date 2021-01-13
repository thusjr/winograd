"""
    this is middlewares in scrapy framework (automatically generated)
"""
# Define here the models for your spider middleware
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals

# useful for handling different item types with a single interface


class NewsspiderSpiderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the spider middleware does not modify the
    # passed objects.
    """
        define middlewares of the newsspider
    """
    @classmethod
    def from_crawler(cls, crawler):
        """
        :param crawler:
        :return:
        """
        # This method is used by Scrapy to create your spiders.
        spider = cls()
        crawler.signals.connect(spider.spider_opened, signal=signals.spider_opened)
        return spider

    def process_spider_input(self):
        """
        :param response:
        :param spider:
        :return:
        """
        # Called for each response that goes through the spider
        # middleware and into the spider.

        # Should return None or raise an exception.
        return None

    def process_spider_output(self, result):
        # Called with the results returned from the Spider, after
        # it has processed the response.

        # Must return an iterable of Request, or item objects.
        """
        :param response:
        :param result:
        :param spider:
        :return:
        """
        for i in result:
            yield i

    def process_spider_exception(self):
        # Called when a spider or process_spider_input() method
        # (from other spider middleware) raises an exception.

        # Should return either None or an iterable of Request or item objects.
        """
        :param response:
        :param exception:
        :param spider:
        :return:
        """
        flag = True
        if flag:
            pass

    def process_start_requests(self, start_requests):
        # Called with the start requests of the spider, and works
        # similarly to the process_spider_output() method, except
        # that it doesn’t have a response associated.

        # Must return only requests (not items).
        """
        :param start_requests:
        :param spider:
        :return:
        """

        for start_request in start_requests:
            yield start_request

    def spider_opened(self, spider):
        """
        :param spider:
        :return:
        """
        spider.logger.info('Spider opened: %s' % spider.name)


class NewsspiderDownloaderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.
    """
        define downloadmiddleware of the newsspider
    """
    @classmethod
    def from_crawler(cls, crawler):
        """
        used by Scrapy to create my spiders.
        :param crawler:
        :return:
        """
        spider = cls()
        crawler.signals.connect(spider.spider_opened, signal=signals.spider_opened)
        return spider

    def process_request(self):
        # Called for each request that goes through the downloader
        # middleware.

        # Must either:
        # - return None: continue processing this request
        # - or return a Response object
        # - or return a Request object
        # - or raise IgnoreRequest: process_exception() methods of
        #   installed downloader middleware will be called
        """
        :param request:
        :param spider:
        :return:
        """
        return None

    def process_response(self, response):
        # Called with the response returned from the downloader.

        # Must either;
        # - return a Response object
        # - return a Request object
        # - or raise IgnoreRequest
        """
        :param request:
        :param response:
        :param spider:
        :return:
        """
        return response

    def process_exception(self):
        # Called when a download handler or a process_request()
        # (from other downloader middleware) raises an exception.

        # Must either:
        # - return None: continue processing this exception
        # - return a Response object: stops process_exception() chain
        # - return a Request object: stops process_exception() chain
        """
        :param request:
        :param exception:
        :param spider:
        :return:
        """
        flag = True
        if flag:
            pass

    def spider_opened(self, spider):
        """
        :param spider:
        :return:
        """
        spider.logger.info('Spider opened: %s' % spider.name)
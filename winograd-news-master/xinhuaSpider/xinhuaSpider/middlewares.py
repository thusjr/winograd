"""
    this is middlewares in scrapy framework (automatically generated)
"""
from scrapy import signals
from scrapy.exceptions import IgnoreRequest


class XinhuaspiderSpiderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the spider middleware does not modify the
    # passed objects.
    """
            define middlewares of the xinhuaspider
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

    def process_spider_input(self, response, spider):
        """
        :param response:
        :param spider:
        :return:
        """
        # Called for each response that goes through the spider
        # middleware and into the spider.

        # Should return None or raise an exception.
        return None

    def process_spider_output(self, response, result, spider):
        """
        :param response:
        :param result:
        :param spider:
        :return:
        """
        # Called with the results returned from the Spider, after
        # it has processed the response.

        # Must return an iterable of Request, or item objects.
        for i in result:
            yield i

    def process_spider_exception(self, response, exception, spider):
        """
        :param response:
        :param exception:
        :param spider:
        :return:
        """
        # Called when a spider or process_spider_input() method
        # (from other spider middleware) raises an exception.

        # Should return either None or an iterable of Request or item objects.
        flag = True
        if flag:
            pass

    def process_start_requests(self, start_requests, spider):
        """
        :param start_requests:
        :param spider:
        :return:
        """
        # Called with the start requests of the spider, and works
        # similarly to the process_spider_output() method, except
        # that it doesn’t have a response associated.

        # Must return only requests (not items).
        for start_request in start_requests:
            yield start_request

    def spider_opened(self, spider):
        """
        :param spider:
        :return:
        """
        spider.logger.info('Spider opened: %s' % spider.name)


class XinhuaspiderDownloaderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.
    """
        define downloadmiddleware of the xinhuaspider
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

    def process_request(self, request, spider):
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

    def process_response(self, request, response, spider):
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
        if response.status == 200:
            return response
        else:
            raise IgnoreRequest('URL is dead, skipping')

    def process_exception(self, request, exception, spider):
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

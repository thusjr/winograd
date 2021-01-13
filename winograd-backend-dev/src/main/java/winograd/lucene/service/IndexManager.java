package winograd.lucene.service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import winograd.lucene.dao.NewsDao;
import winograd.lucene.dao.NewsDaoImpl;
import winograd.lucene.pojo.News;


public class IndexManager {
    public void createIndex(int pageSize, int pageNum, int startId,boolean isIncrement) throws Exception {
        createIndex(pageSize, pageNum, startId, "data",isIncrement);
    }

    public void createIndex(int pageSize, int pageNum, int startId, String dataLocation, boolean isIncrement) throws Exception {

        System.err.println("页面大小：" + pageSize + ", 页面数量：" + pageNum + ", 起始页面编号：" + startId);

        Long startTime = System.currentTimeMillis();
        
        // 创建分词器, IKAnalyzer分词器,
        Analyzer analyzer = new IKAnalyzer();
        // 创建Directory目录对象, 目录对象表示索引库的位置
        Directory dir = FSDirectory.open(Paths.get(dataLocation));

        // 创建IndexWriterConfig对象, 这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建IndexWriter输出流对象, 指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);

        // 采集数据
        NewsDao newsDao = new NewsDaoImpl();
        for (int pageCount = 0; pageCount < pageNum; pageCount++) {
            int pageId = startId + pageCount;
            List<News> newsList = newsDao.queryNewsList(pageSize, pageId, isIncrement);

            // 文档集合
            List<Document> docList = new ArrayList<>();

            for (News news : newsList) {
                // 2. 创建文档对象
                Document document = new Document();

                // 创建域对象并且放入文档对象中
                /**
                 * 是否分词: 否, 因为主键分词后无意义 是否索引: 是, 如果根据id主键查询, 就必须索引 是否存储: 是, 因为主键id比较特殊,
                 * 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储 存储后, 才可以获取到id具体的内容
                 */
                document.add(new StringField("id", news.getId(), Field.Store.YES));

                /**
                 * 是否分词: 是, 因为标题字段需要查询, 并且分词后有意义所以需要分词 是否索引: 是, 因为需要根据标题字段查询 是否存储: 是,
                 * 因为页面需要展示标题, 所以需要存储 新闻来源同理
                 */
                document.add(new TextField("title", news.getTitle(), Field.Store.YES));
                document.add(new TextField("src", news.getSrc(), Field.Store.YES));
                document.add(new TextField("summary", news.getSummary(), Field.Store.YES));

                /**
                 * 是否分词: 是(因为lucene底层算法规定, 如果根据时间范围查询, 必须分词) 是否索引: 是, 需要根据时间进行范围查询, 所以必须索引 是否存储:
                 * 是, 因为页面需要展示时间
                 */
                document.add(new TextField("time", news.getTime(), Field.Store.YES));

                /**
                 * 是否分词: 否, 因为不查询, 所以不索引, 因为不索引所以不分词 是否索引: 否, 因为不需要根据图片地址路径查询 是否存储: 是,
                 * 因为页面需要展示商品图片
                 */
                document.add(new StoredField("url", news.getUrl()));
                document.add(new StoredField("picUrl", news.getPicUrl()));

                // 将文档对象放入到文档集合中
                docList.add(document);
            }
            // 写入文档到索引库
            for (Document doc : docList) {
                indexWriter.addDocument(doc);
            }
            Long curTime = System.currentTimeMillis();
            System.err.println("已经建立："+((pageCount+1)*pageSize)+"条索引，用时：" + (curTime-startTime)/(1000*60) + "分钟");
        }
        // 释放资源
        indexWriter.close();

        Long endTime = System.currentTimeMillis();
        System.err.println("建立索引条数：" + (pageSize*pageNum) + ", 总共用时： " + (endTime-startTime)/(1000*60) + "分钟");
    }
    
    public void createIncreIndex(String dataLocation) throws Exception {

        Long startTime = System.currentTimeMillis();

        // 创建分词器, IKAnalyzer分词器,
        Analyzer analyzer = new IKAnalyzer();
        // 创建Directory目录对象, 目录对象表示索引库的位置
        Directory dir = FSDirectory.open(Paths.get(dataLocation));

        // 创建IndexWriterConfig对象, 这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建IndexWriter输出流对象, 指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);

        // 采集数据
        NewsDao newsDao = new NewsDaoImpl();
        List<News> newsList = newsDao.queryNewsList(10, 2, true);

        // 文档集合
        List<Document> docList = new ArrayList<>();

        for (News news : newsList) {
            // 2. 创建文档对象
            Document document = new Document();

            // 创建域对象并且放入文档对象中
            /**
             * 是否分词: 否, 因为主键分词后无意义 是否索引: 是, 如果根据id主键查询, 就必须索引 是否存储: 是, 因为主键id比较特殊,
             * 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储 存储后, 才可以获取到id具体的内容
             */
            document.add(new StringField("id", news.getId(), Field.Store.YES));

            /**
             * 是否分词: 是, 因为标题字段需要查询, 并且分词后有意义所以需要分词 是否索引: 是, 因为需要根据标题字段查询 是否存储: 是,
             * 因为页面需要展示标题, 所以需要存储 新闻来源同理
             */
            document.add(new TextField("title", news.getTitle(), Field.Store.YES));
            document.add(new TextField("src", news.getSrc(), Field.Store.YES));
            document.add(new TextField("summary", news.getSummary(), Field.Store.YES));

            /**
             * 是否分词: 是(因为lucene底层算法规定, 如果根据时间范围查询, 必须分词) 是否索引: 是, 需要根据时间进行范围查询, 所以必须索引 是否存储:
             * 是, 因为页面需要展示时间
             */
            document.add(new TextField("time", news.getTime(), Field.Store.YES));

            /**
             * 是否分词: 否, 因为不查询, 所以不索引, 因为不索引所以不分词 是否索引: 否, 因为不需要根据图片地址路径查询 是否存储: 是,
             * 因为页面需要展示商品图片
             */
            document.add(new StoredField("url", news.getUrl()));
            document.add(new StoredField("picUrl", news.getPicUrl()));

            // 将文档对象放入到文档集合中
            docList.add(document);
        }
        // 写入文档到索引库
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }

        // 释放资源
        indexWriter.close();

        Long endTime = System.currentTimeMillis();
        System.err.println("执行增量索引：, 总共用时： " +
         (endTime-startTime)/(1000*60) + "分钟");
    }
}

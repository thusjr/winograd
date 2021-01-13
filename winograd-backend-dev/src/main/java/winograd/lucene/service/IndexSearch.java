package winograd.lucene.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import winograd.lucene.pojo.News;

public class IndexSearch {
    public List<News> searchIndex(String entry) throws Exception {
        return searchIndex(entry, "data");
    }

    public List<News> searchIndex(String entry, String dataLocation) throws IOException, ParseException {


        // 1. 创建分词器(对搜索的关键词进行分词使用)
        // 注意: 分词器要和创建索引的时候使用的分词器一模一样
        Analyzer analyzer = new IKAnalyzer();

        // 2. 创建查询对象,
        // 第一个参数: 默认查询域, 如果查询的关键字中带搜索的域名, 则从指定域中查询, 如果不带域名则从, 默认搜索域中查询
        // 第二个参数: 使用的分词器
        QueryParser queryParser = new QueryParser("title", analyzer);

        // 3. 设置搜索关键词
        // 华 OR 为 手 机
        Query query = queryParser.parse(entry);

        // 4. 创建Directory目录对象, 指定索引库的位置
        Directory dir = FSDirectory.open(Paths.get(dataLocation));
        // 5. 创建输入流对象
        IndexReader indexReader = DirectoryReader.open(dir);
        // 6. 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 7. 搜索, 并返回结果
        // 第二个参数: 是返回多少条数据用于展示, 分页使用
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 获取查询到的结果集的总数, 打印
        System.err.println("=======count=======" + topDocs.totalHits);

        // 8. 获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        ArrayList<News> searchResult = new ArrayList<>();

        // 9. 遍历结果集
        if (topDocs.totalHits != 0) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取查询到的文档唯一标识, 文档id, 这个id是lucene在创建文档的时候自动分配的
                int docID = scoreDoc.doc;
                // 通过文档id, 读取文档
                Document doc = indexSearcher.doc(docID);

                News n = new News();
                n.setId(doc.get("id"));
                n.setTitle(doc.get("title"));
                n.setUrl(doc.get("url"));
                n.setSrc(doc.get("src"));
                n.setTime(doc.get("time"));
                System.out.println(doc.get("time"));
                n.setPicUrl(doc.get("picUrl"));
                n.setSummary(doc.get("summary"));
                searchResult.add(n);
            }
        }
        indexReader.close();

        return searchResult;
    }

}

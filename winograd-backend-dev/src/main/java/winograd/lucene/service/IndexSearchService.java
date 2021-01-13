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
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import winograd.lucene.pojo.News;
import winograd.lucene.pojo.ResultModel;

@Service
public class IndexSearchService {

    private String prefixHTML = "<font color='red'>";
    private String suffixHTML = "</font>";

    public static final Integer PAGE_SIZE = 15; // 以15页分页

    public ResultModel query(String queryString, Integer page) throws Exception {
        return query(queryString, page, "data");
    }

    public ResultModel query(String queryString, Integer page, String datalocation)
            throws ParseException, IOException, InvalidTokenOffsetsException {
        long startTime = System.currentTimeMillis();
        ResultModel resultModel = new ResultModel();// 包含搜索结果列表和页码信息的封装类
        Analyzer analyzer = new IKAnalyzer();
        QueryParser queryParser = new QueryParser("title", analyzer);
        Query query1 = null;
        query1 = queryParser.parse(queryString);
        Directory directory = FSDirectory.open(Paths.get(datalocation));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        int start = page * PAGE_SIZE; // 起始条目
        // Integer end = page * PAGE_SIZE; //终止条目
        TopDocs topDocs = indexSearcher.search(query1, start); // 查询数据， 结束页面自前的数据都会查询到，但是只取本页的数据
        if (topDocs.totalHits == 0) {
            resultModel.setRecordCount((long) 0);
            resultModel.setCurPage((long) 0);
            resultModel.setPageCount((long) 0);
            resultModel.setEntry(queryString);
            resultModel.setNewsList(null);
        } else {
            Integer end = topDocs.scoreDocs.length - 1;
            ScoreDoc preScore = topDocs.scoreDocs[end]; // 获取到上一页最后一条
            topDocs = indexSearcher.searchAfter(preScore, query1, PAGE_SIZE);// 查询最后一条后的数据的一页数据
            resultModel.setRecordCount(topDocs.totalHits);// 获取查询到的总条数
            ScoreDoc[] scoreDocs = topDocs.scoreDocs; // 结果集
            long endTime = System.currentTimeMillis();
            long timeToSearch = endTime - startTime; // 单位 ms --耗时
            System.err.println("====查询====" + query1 + "====消耗时间为====" + (timeToSearch) + "ms"); // for debug

            List<News> newsList = new ArrayList<>(); // 遍历结果集封装返回的数据
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(prefixHTML, suffixHTML);
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query1, "title"));
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docID = scoreDoc.doc;
                Document doc = reader.document(docID);
                // 封装News对象
                News n = new News();
                // 标题飘红
                String text_Title = doc.get("title");
                String highLightText_Title = highlighter.getBestFragment(analyzer, "title", text_Title);
                n.setTitle(highLightText_Title);
                // 摘要飘红
                String text_Sum = doc.get("summary");
                String highLightText_Sum = highlighter.getBestFragment(analyzer, "summary", text_Sum);
                n.setSummary(highLightText_Sum);
                // 普通部分
                n.setId(doc.get("id"));
                n.setUrl(doc.get("url"));
                n.setSrc(doc.get("src"));
                n.setTime(doc.get("time"));
                n.setPicUrl(doc.get("picUrl"));
                
                newsList.add(n);
            }
            resultModel.setNewsList(newsList);
            resultModel.setCurPage(page);
            Long pageCount = topDocs.totalHits % PAGE_SIZE > 0 ? (topDocs.totalHits / PAGE_SIZE) + 1
                    : topDocs.totalHits / PAGE_SIZE;
            resultModel.setPageCount(pageCount);
            resultModel.setEntry(queryString);
        }
        return resultModel;
    }
}

package winograd.lucene.dao;

import winograd.lucene.pojo.News;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {
    private String sql;
    private String idIncrement; // 每次存下上次的最后一条数据的id，增量索引时候直接从这里开始读下一条。

    private Connection connectToMysql() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://152.136.237.143:32768/tencent_news", "winograd", "winograd");
    }

    @Override
    public List<News> queryNewsList(int pageSize, int pageId, boolean isIncrement) throws SQLException, IOException {
        List<News> list = new ArrayList<>();
        Connection conn = connectToMysql();
        // idIncrement="20201126A008L400";//false
        if(isIncrement){
            BufferedReader in = new BufferedReader(new FileReader("idIncrement.txt"));
            idIncrement = in.readLine();
            in.close();
            sql = "select * from tencent_news.classified_tencent_news where id>="+idIncrement+" order by id asc";
            System.err.println("增量索引起始id =  " + idIncrement);
        }
        else {
            sql = "SELECT * FROM tencent_news LIMIT ?,?";
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(isIncrement==false){
            pstmt.setInt(1, pageId * pageSize);
            pstmt.setInt(2, pageSize);
        }
        // 获取结果集并解析
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            News news = new News();
            news.setId(resultSet.getString("id"));
            System.err.println("我看到了id =  " + resultSet.getString("id"));
            news.setTitle(resultSet.getString("title"));
            news.setUrl(resultSet.getString("link"));
            news.setSrc(resultSet.getString("src"));
            news.setTime(resultSet.getString("time"));
            news.setPicUrl(resultSet.getString("pic_url"));
            news.setSummary(resultSet.getString("summary"));
            list.add(news);
            idIncrement = news.getId();
        }
        idIncrement = idIncrement.substring(0,8);
        BufferedWriter out = new BufferedWriter(new FileWriter("idIncrement.txt"));
        out.write(idIncrement);
        out.close();
        resultSet.close();
        pstmt.close();
        conn.close();
        if(isIncrement){
            System.err.println("增量索引最新id =  " + idIncrement);
        }

        return list;
    }

    @Override
    public List<News> queryLatest(String type) throws SQLException {
        List<News> list = new ArrayList<>();
        Connection conn = connectToMysql();
        PreparedStatement pstmt ;
        if(type != "bj" && type != "tech" && type != "24hours"){
            sql = "select * from tencent_news.classified_tencent_news where category=? order by time desc limit 100";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, type);
        }else{      
            sql = "select * from tencent_news.classified_tencent_news order by time desc limit 100";
            pstmt = conn.prepareStatement(sql);
        }
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            News news = new News();
            news.setId(resultSet.getString("id"));
            news.setTitle(resultSet.getString("title"));
            news.setUrl(resultSet.getString("link"));
            news.setSrc(resultSet.getString("src"));
            news.setTime(resultSet.getString("time"));
            news.setPicUrl(resultSet.getString("pic_url"));
            news.setSummary(resultSet.getString("summary"));
            list.add(news);
        }
        resultSet.close();
        pstmt.close();
        conn.close();
        return list;
    }
}

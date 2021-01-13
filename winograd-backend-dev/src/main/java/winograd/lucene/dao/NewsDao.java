package winograd.lucene.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import winograd.lucene.pojo.News;

public interface NewsDao {
	//查询所有news里面的数据
	public List<News> queryNewsList(int pageSize,int pageId, boolean isIncrement) throws SQLException, IOException;
	
	public List<News> queryLatest(String type) throws SQLException;

}

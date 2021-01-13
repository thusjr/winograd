package winograd.lucene.pojo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 自定义分页实体类
 */
public class ResultModel implements Serializable{

    private static final long serialVersionUID = 7747557552933114547L;
    @JSONField(ordinal = 1)
    private Long recordCount;       //总条目数
    @JSONField(ordinal = 2)
    private Long pageCount;         //页码总数
    @JSONField(ordinal = 3)
    private long curPage;           //当前页码
    @JSONField(ordinal = 4)
    private String entry;
    @JSONField(ordinal = 5)
    private List<News> newsList;    // 新闻<列表>

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public long getCurPage() {
        return curPage;
    }

    public void setCurPage(long curPage) {
        this.curPage = curPage;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getEntry() {
        return this.entry;
    }
}

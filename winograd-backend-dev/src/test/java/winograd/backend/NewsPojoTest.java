package winograd.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import winograd.lucene.pojo.News;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NewsPojoTest {

    private News news = new News();

    @Test
    public void testGetterSetter(){
        news.setId("id");
        news.setPicUrl("picUrl");
        news.setSrc("src");
        news.setSummary("summary");
        news.setTime("time");
        news.setTitle("title");
        news.setUrl("url");
        assertEquals(news.getId(), "id");
        assertEquals(news.getPicUrl(), "picUrl");
        assertEquals(news.getSrc(), "src");
        assertEquals(news.getSummary(), "summary");
        assertEquals(news.getTime(), "time");
        assertEquals(news.getTitle(), "title");
        assertEquals(news.getUrl(), "url");
    }
}
package winograd.backend;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import winograd.lucene.service.IndexManager;
import winograd.lucene.service.IndexSearchService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearchService {
    private IndexManager iManager = new IndexManager();
    @Test
    public void testInedxSearch() {
        IndexSearchService ts = new IndexSearchService();
        try {
            iManager.createIndex(100, 1, 0, "data_test",false); //增加覆盖率，true的分支也要覆盖？
            ts.query("中国", 1, "data_test");
            ts.query("bbbb", 1, "data_test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
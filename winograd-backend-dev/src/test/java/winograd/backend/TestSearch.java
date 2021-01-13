package winograd.backend;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import winograd.lucene.service.IndexManager;
import winograd.lucene.service.IndexSearch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {

    private IndexManager iManager = new IndexManager();

    @Test
    public void testInedxSearch() {
        IndexSearch ts = new IndexSearch();
        try {
            iManager.createIndex(100, 1, 0, "data_test",false); //增加覆盖率，true的分支也要覆盖？
            ts.searchIndex("中国", "data_test");
            ts.searchIndex("bbbb", "data_test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

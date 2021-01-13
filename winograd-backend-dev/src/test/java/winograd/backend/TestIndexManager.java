package winograd.backend;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import winograd.lucene.service.IndexManager;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndexManager {

    private IndexManager iManager = new IndexManager();

    @Test
    public void testCreatIndex() {
        try {
            iManager.createIndex(100, 1, 0, "data_test",false); ////增加覆盖率，true的分支也要覆盖？
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

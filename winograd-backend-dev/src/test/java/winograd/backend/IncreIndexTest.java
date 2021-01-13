package winograd.backend;

import org.junit.Test;

import winograd.backend.controller.IncreController;
import winograd.lucene.service.IndexManager;

public class IncreIndexTest {
    private IndexManager iManager = new IndexManager();
    private IncreController iIncre = new IncreController();

    @Test
    public void increindexTest_() throws Exception {
        iIncre.incrementTasks();
        iManager.createIndex(100, 1, 0, "data_test",false);
    }
}

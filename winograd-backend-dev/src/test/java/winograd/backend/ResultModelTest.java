package winograd.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import winograd.lucene.pojo.ResultModel;

@SpringBootTest
@RunWith(SpringRunner.class)

public class ResultModelTest {
    private ResultModel res = new ResultModel();

    @Test
    public void testGetterSetter(){
        res.setCurPage(0);
        res.setPageCount((long) 0);
        res.setRecordCount((long) 0);
        res.setNewsList(null);
        assertEquals(res.getCurPage(), 0);
        assertEquals(res.getPageCount(), 0);
        assertEquals(res.getRecordCount(), 0);
        assertEquals(res.getNewsList(), null);
    }
}
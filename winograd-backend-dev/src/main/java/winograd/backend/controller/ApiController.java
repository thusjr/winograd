package winograd.backend.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import winograd.lucene.dao.NewsDao;
import winograd.lucene.dao.NewsDaoImpl;
import winograd.lucene.pojo.News;
import winograd.lucene.pojo.ResultModel;
import winograd.lucene.service.IndexManager;
import winograd.lucene.service.IndexSearchService;

@RestController
@RequestMapping("/api")
public class ApiController {

	// 使用新编写的服务，包含页码
	@RequestMapping(value = "search", method = RequestMethod.GET)
	ResponseEntity<String> handleSearch(@RequestParam String entry, @RequestParam int page,
			@RequestParam(required = false) String datalocation)
			throws ParseException, IOException, InvalidTokenOffsetsException {
		IndexSearchService iSearch = new IndexSearchService();
		if (datalocation == null)
			datalocation = "data";
		ResultModel resultmodel = iSearch.query(entry, page, datalocation);
		if (resultmodel.getRecordCount() > 0) {
			return new ResponseEntity<>(JSON.toJSONString(resultmodel), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("NO RESULT", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "setupindex", method = RequestMethod.POST)
	ResponseEntity<String> setUpIndex(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum,
			@RequestParam("startPageId") int startId) throws Exception {
		IndexManager iManager = new IndexManager();
		boolean isIncrement = false;      
		try {
			iManager.createIndex(pageSize, pageNum, startId, isIncrement);
			return new ResponseEntity<>("INDEX SETUP", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("INDEX FAIL", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "latest", method = RequestMethod.GET)
	ResponseEntity<String> getLatest(@RequestParam String type) throws SQLException {
		NewsDao iDao = new NewsDaoImpl();
		ArrayList<News> res = (ArrayList<News>) iDao.queryLatest(type);
		if (!res.isEmpty()) {
			return new ResponseEntity<>(JSON.toJSONString(res), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("NO RESULT", HttpStatus.BAD_REQUEST);
		}
	}
}
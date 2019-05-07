package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.grammar.service.GrammarService;
import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.util.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GrammarController {

    private Jedis jedis = new Jedis("localhost");

    @RequestMapping(value = "/api/grammar/get-test-dir-list", method = RequestMethod.GET)
    public List<Map<String, String>> getTestDirectoryList() {
        HashMap<String, Object> map = FileUtils.getTestDirList(jedis.get("TESS_STORAGE_LIBRARY"));
        List<Map<String, String>> res = new ArrayList<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("name", entry.getKey());
            item.put("path", entry.getValue().toString());
            res.add(item);
        }

        return res;

    }

    @RequestMapping(value = "/api/grammar/load-recog-output", method = RequestMethod.GET)
    public String loadFile(@RequestParam(value = "testDirPath") String testDirPath) {
        return FileUtils.loadTextFile(testDirPath + "/output.txt");
    }

    @RequestMapping(value = "/api/grammar/process", method = RequestMethod.GET)
    public Map<String, Object> process(@RequestParam(value = "testDirPath") String testDirPath) {
        List<WordObj> result = GrammarService.process(testDirPath);

        Map<String, Object> res = new HashMap<>();
        res.put("output", result);
        res.put("input", loadFile(testDirPath));

        return res;
    }

    @RequestMapping(value = "/api/grammar/save-modified-output", method = RequestMethod.GET)
    public String saveModifiedFile(@RequestParam(value = "text") String text, @RequestParam(value = "testDirPath") String testDirPath) {
        FileUtils.saveTextFile(text, testDirPath + "/output_modified.txt");
        return "DONE";
    }

}

package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.service.GrammarService;
import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final DocumentServices documentServices;

    @Autowired
    public GrammarController(final DocumentServices documentServices) {
        this.documentServices = documentServices;
    }

    private Jedis jedis = new Jedis("localhost");

    @RequestMapping(value = "/api/grammar/process", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public Map<String, Object> process(@CurrentUser UserPrincipal currentUser,
                                       @RequestParam(value = "documentId") int documentId) {

        String text = this.documentServices.getDocumentContent(documentId, currentUser.getUsername());
        List<WordObj> result = GrammarService.process(text);

        Map<String, Object> res = new HashMap<>();
        res.put("input", text);
        res.put("output", result);

        return res;
    }

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


    @RequestMapping(value = "/api/grammar/save-modified-output", method = RequestMethod.GET)
    public String saveModifiedFile(@RequestParam(value = "text") String text, @RequestParam(value = "testDirPath") String testDirPath) {
        FileUtils.saveTextFile(text, testDirPath + "/output_modified.txt");
        return "DONE";
    }

}

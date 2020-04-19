package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.service.GrammarService;
import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GrammarController {
    private final GrammarService grammarService;
    private final TaskServices taskServices;

    public GrammarController(GrammarService grammarService, TaskServices taskServices) {
        this.grammarService = grammarService;
        this.taskServices = taskServices;
    }

    @RequestMapping(value = "/api/grammar/process", method = RequestMethod.GET)
    public Map<String, Object> process(@CurrentUser UserPrincipal currentUser, @RequestParam(value = "taskId") int taskId) {

        String text = this.taskServices.getTaskOutputContent(taskId, currentUser.getEmail());
        List<WordObj> result = grammarService.process(text);

        Map<String, Object> res = new HashMap<>();
        res.put("input", text);
        res.put("output", result);

        return res;
    }

//    @RequestMapping(value = "/api/grammar/get-test-dir-list", method = RequestMethod.GET)
//    public List<Map<String, String>> getTestDirectoryList() {
//        HashMap<String, Object> map = FileUtils.getTestDirList(jedis.get("TESS_STORAGE_LIBRARY"));
//        List<Map<String, String>> res = new ArrayList<>();
//
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            Map<String, String> item = new HashMap<>();
//            item.put("name", entry.getKey());
//            item.put("path", entry.getValue().toString());
//            res.add(item);
//        }
//
//        return res;
//    }

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

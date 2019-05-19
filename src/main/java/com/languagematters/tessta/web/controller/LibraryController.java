package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.service.GrammarService;
import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.services.LibraryTaskServices;
import com.languagematters.tessta.library.services.LibraryUserFileServices;
import com.languagematters.tessta.library.services.model.Task;
import com.languagematters.tessta.library.services.model.UserFile;
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
public class LibraryController {
    @RequestMapping(value = "/api/library/userfiles", method = RequestMethod.GET)
    public List<UserFile> getUserFiles() {
        return LibraryUserFileServices.getInstance().getUserFiles();
    }

    @RequestMapping(value = "/api/library/tasks", method = RequestMethod.GET)
    public List<Task> getTasks(@RequestParam(value = "userfilename") String userFileName) {
        return LibraryTaskServices.getInstance().getTasks(userFileName);
    }

    @RequestMapping(value = "/api/library/test", method = RequestMethod.GET)
    public String createUserFile() {

        // TESTS
        LibraryTaskServices.getInstance().getTasks("Mawbima");
        return "hello";
    }
}

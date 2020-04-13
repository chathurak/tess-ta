package com.languagematters.tessta.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TessconfigController {

    @RequestMapping(value = "/api/tessconfig/traineddata/all", method = RequestMethod.GET)
    public ArrayList<Object> getTrainedData() {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/tessconfig/traineddata/setdefault", method = RequestMethod.POST)
    public void setDefaultTrainedData(@RequestParam(value = "id") String id) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/tessconfig/traineddata/save", method = RequestMethod.POST)
    public void setTrainedDataName(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "newName") String newName,
                                   @RequestParam(value = "newDescription") String newDescription) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/tessconfig/traineddata/delete", method = RequestMethod.DELETE)
    public void deleteTrainedData(@RequestParam(value = "id") String id) {
        throw new UnsupportedOperationException();
    }

}

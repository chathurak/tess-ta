package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.admin.model.TrainedDataProperties;
import com.languagematters.tessta.admin.service.TessdataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class TessconfigController {

    @RequestMapping(value = "/api/tessconfig/traineddata/all", method = RequestMethod.GET)
    public ArrayList<TrainedDataProperties> getTrainedData() {
        return TessdataService.getTrainedData();
    }

    @RequestMapping(value = "/api/tessconfig/traineddata/setdefault", method = RequestMethod.POST)
    public void setDefaultTrainedData(@RequestParam(value = "id") String id) {
        try {
            TessdataService.setDefaultTrainedData(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/api/tessconfig/traineddata/save", method = RequestMethod.POST)
    public void setTrainedDataName(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "newName") String newName,
                                   @RequestParam(value = "newDescription") String newDescription) {
        TessdataService.setTrainedDataName(id, newName);
        TessdataService.setTrainedDataDescription(id, newDescription);
    }

    @RequestMapping(value = "/api/tessconfig/traineddata/delete", method = RequestMethod.DELETE)
    public void deleteTrainedData(@RequestParam(value = "id") String id) {
        try {
            TessdataService.deleteTrainedData(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

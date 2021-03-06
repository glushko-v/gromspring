package com.hw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


public class MyController {


    private Route route;


    private Step step;


    private Service service;

    @RequestMapping(method = RequestMethod.GET, value = "/calledByBean", produces = "text/plain")
    public @ResponseBody
    String calledByBean() {
        route.getId();


        route.getSteps();

        step.getId();

        step.getParamsServiceFrom();

        step.getParamsServiceTo();

        step.getServiceTo();
        step.getServiceFrom();
        service.getId();
        service.getName();
        service.getParamsToCall();
        return "OK";


    }


}

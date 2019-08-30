package com.hw1;


import java.util.List;

public class Route {
    private String id;
    private List<Step> steps;

    public Route(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }



    public void setId(String id) {
        this.id = id;
    }

    public List<Step> getSteps() {

        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}

package com.hw2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping(method = RequestMethod.POST, value = "/itemsave", produces = "text/plain")
    public @ResponseBody String save(Item item){

        itemService.save(item);

        return "Saving...";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/itemupdate", produces = "text/plain")
    public @ResponseBody String update(Item item){

        itemService.update(item);


        return "Updating...";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/itemdelete", produces = "text/plain")
    public @ResponseBody String delete(){

        itemService.delete();


        return "Deleting...";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/itembyid", produces = "text/plain")
    public @ResponseBody String findById(){

        itemService.findById();


        return "Found by ID...";
    }



}

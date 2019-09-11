package com.hw2;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ItemController {


    private ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/itemsave", produces = "text/plain")
    public @ResponseBody String save(Item item){

        try {
            itemService.save(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Saving...";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/itemupdate", produces = "text/plain")
    public @ResponseBody String update(Item item, long id){

        try {
            itemService.update(item, id);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Updating...";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/itemdelete/{id}", produces = "text/plain")
    public @ResponseBody String delete(@PathVariable("id") long id){


        try {
            itemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ("Deleted item " + id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/itembyid/{id}", produces = "text/plain")
    public @ResponseBody String findById(@PathVariable("id") long id){

        Item item = null;
        try {
            item = itemService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return item.toString();
    }



}

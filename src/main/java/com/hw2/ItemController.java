package com.hw2;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;

import org.springframework.web.bind.annotation.*;

import java.io.*;


//@Controller
public class ItemController {


    private ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/itemsave", produces = "text/plain")
    public @ResponseBody Item save(HttpServletRequest req, Item item) throws IOException {





        try(BufferedReader br = new BufferedReader(req.getReader())) {
            itemService.save(mapJSONtoItem(br));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return item;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/itemupdate/{id}", produces = "text/plain")
    public @ResponseBody Item update(@PathVariable("id") long id, HttpServletRequest req, Item item) throws IOException {




        try(BufferedReader br = new BufferedReader(req.getReader())) {
            itemService.update(mapJSONtoItem(br), id);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return item;
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

    Item mapJSONtoItem(BufferedReader br) {


        ObjectMapper mapper = new ObjectMapper();
        try {


            Item item = mapper.readValue(br, Item.class);

            return item;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }






}

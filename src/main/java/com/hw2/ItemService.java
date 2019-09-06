package com.hw2;

import org.springframework.beans.factory.annotation.Autowired;

public class ItemService {

    @Autowired
    ItemDAO itemDAO;

    Item save(Item item){

        return itemDAO.save(item);
    }

    Item update(Item item){
        return itemDAO.update(item);
    }

    void delete(){

        itemDAO.delete();
    }

    void findById(){

        itemDAO.findById();

    }

}

package com.lesson3.HomeWork;

import com.lesson3.HomeWork.DAO.StorageDAO;
import com.lesson3.HomeWork.Service.FileService;
import com.lesson3.HomeWork.Service.StorageService;
import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.FormatsSupported;
import com.lesson3.HomeWork.model.Storage;

public class Demo {
    public static void main(String[] args) throws Exception {

        String formats = FormatsSupported.jpeg.toString() + ", " + FormatsSupported.txt.toString() + ", " + FormatsSupported.rar;

        Storage storage = new Storage(formats, "UAE", 9876541);
        File file = new File("Test", "bla", 321, storage);

        StorageDAO sd = new StorageDAO();
        StorageService ss = new StorageService();
        FileService fs = new FileService();

        System.out.println(ss.findById(32165));





//        sd.update(storage, 25);




    }
}

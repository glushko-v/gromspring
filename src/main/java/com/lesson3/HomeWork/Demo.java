package com.lesson3.HomeWork;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson3.HomeWork.DAO.FileDAO;
import com.lesson3.HomeWork.DAO.StorageDAO;
import com.lesson3.HomeWork.Service.FileService;
import com.lesson3.HomeWork.Service.StorageService;
import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.FormatsSupported;
import com.lesson3.HomeWork.model.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Demo {
    public static void main(String[] args) throws Exception {

        String formats = FormatsSupported.txt.toString() + ", " + FormatsSupported.rar;

        StorageDAO sd = new StorageDAO();

        Storage storage = new Storage("jpg, txt", "UAE", 9876541);
        File file = new File("TestTestTest", "txt", 21, null);
        String jsonFile = "{\"Filename\":\"TEST\",\"Format\":\"txt\",\"Filesize\":23, \"Storage\" : {\"Formats\": \"txt, jpg\", \"Country\": \"Tunisia\", \"Size\": 123654}}";
        String jsonFile1 = "{\"Filename\":\"TEST\",\"Format\":\"txt\",\"Filesize\":23, \"Storage\" : {\"Storage_id\": 61}}";


//        ObjectMapper mapper = new ObjectMapper();
//        JsonFactory jsonFactory = new JsonFactory();
//        JsonParser parser = jsonFactory.createParser(jsonFile);
//        parser.setCodec(new ObjectMapper());
//        JsonNode node = parser.readValueAsTree();
//        JsonNode storageNode = node.findValue("Storage");
//
//        Storage storage1 = mapper.convertValue(storageNode, Storage.class);


//        File file1 = mapper.readValue(jsonFile1, File.class);
//        System.out.println(storage1);
//        System.out.println(file1);
//        System.out.println(file1.getStorage().getId());


        FileDAO fd = new FileDAO();


       FileService fs = new FileService(fd);

//       fs.transferFile(fs.findById(112), );

//        fd.transferFile(sd.findById(112), 5);
//        fd.transferFile(sd.findById(112), 4);
//        fd.transferFile(sd.findById(112), 3);













//        FileService fs = new FileService();

//        ss.update(storage, 25);


//        sd.update(storage, 25);


    }
}

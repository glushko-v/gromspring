package com.lesson3.HomeWork.Controller;


//проверка допустимого формата файла
//проверка размера хранилища


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson3.HomeWork.Service.FileService;

import com.lesson3.HomeWork.Service.StorageService;
import com.lesson3.HomeWork.model.Exceptions.FileAlreadyInStorageException;
import com.lesson3.HomeWork.model.Exceptions.FormatNotSupportedException;
import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.Exceptions.NullFieldsException;
import com.lesson3.HomeWork.model.Storage;
import com.lesson3.HomeWork.model.Exceptions.WrongIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;


@org.springframework.stereotype.Controller
public class Controller {


    private FileService fileService;


    private StorageService storageService;

    @Autowired
    public Controller(FileService fileService, StorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/storagesave", produces = "text/plain")
    public @ResponseBody
    String saveStorage(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        try (BufferedReader br = new BufferedReader(req.getReader())) {
            Storage storage = storageService.mapJSONtoStorage(br);
            storageService.save(storage);
        } catch (IOException e) {
//            e.printStackTrace();
            resp.sendError(500, "Can't save storage");
        } catch (NullFieldsException e) {
            resp.sendError(400, "Storage contains null fields");
        }

        resp.setStatus(200);
        return "Saving storage...";

    }

    @RequestMapping(method = RequestMethod.POST, value = "/filesave", produces = "text/plain")
    public @ResponseBody
    String saveFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        ObjectMapper mapper = new ObjectMapper();


        try (BufferedReader br = new BufferedReader(req.getReader())) {

            File file = mapper.readValue(br, File.class);

            fileService.save(file);


        } catch (IOException e) {
//            e.printStackTrace();
            resp.sendError(500, "Can't save file");

        } catch (NullFieldsException e) {

            resp.sendError(400, "File contains null fields");
        }

        resp.setStatus(200);
        return "Saving file...";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/storagedelete/{id}", produces = "text/plain")
    public @ResponseBody
    String deleteStorage(@PathVariable("id") long id, HttpServletResponse resp) throws IOException {

        try {
            storageService.delete(id);
        } catch (WrongIdException e) {
            resp.sendError(404, ("There's no storage with id " + id));
        }

        resp.setStatus(200);
        return "deleting storage...";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/filedelete/{id}", produces = "text/plain")
    public @ResponseBody
    String deleteFile(@PathVariable("id") long id, HttpServletResponse resp) throws IOException {

        try {
            fileService.delete(id);
        } catch (WrongIdException e) {
            resp.sendError(404, "There's no file with id " + id);
        }

        return "deleting file...";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findstorage/{id}", produces = "text/plain")
    public @ResponseBody
    void findStorage(@PathVariable("id") long id, HttpServletResponse resp) throws IOException {

        try {
            storageService.findById(id);
            PrintWriter pw = resp.getWriter();
            pw.print(storageService.findById(id).toString());
            resp.setStatus(200);
        } catch (WrongIdException e) {
            resp.sendError(404, ("There's no storage with id " + id));
        }


    }

    @RequestMapping(method = RequestMethod.GET, value = "/findfile/{id}", produces = "text/plain")
    public @ResponseBody
    void findFile(@PathVariable("id") long id, HttpServletResponse resp) throws IOException {

        try {
            fileService.findById(id);
            PrintWriter pw = resp.getWriter();
            pw.print(fileService.findById(id).toString());
            resp.setStatus(200);
        } catch (WrongIdException e) {
            resp.sendError(404, ("There's no file with id " + id));
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/storageupdate/{id}", produces = "text/plain")
    public @ResponseBody
    String updateStorage(@PathVariable("id") long id, HttpServletResponse resp, HttpServletRequest req) throws IOException {


        try (BufferedReader br = new BufferedReader(req.getReader())) {

            Storage storage = storageService.mapJSONtoStorage(br);

            storageService.update(storage, id);
            resp.setStatus(200);


        } catch (IOException e) {
//            e.printStackTrace();
            resp.sendError(500, "Can't update storage");
        } catch (WrongIdException e) {
            resp.sendError(404, ("There's no storage with id " + id));
        } catch (NullFieldsException e) {
            resp.sendError(400, "Storage contains null fields");
        }


        return ("Updating storage " + id);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/fileupdate/{id}", produces = "text/plain")
    public @ResponseBody
    String updateFile(@PathVariable("id") long id, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(req.getReader())) {

            File file = mapper.readValue(br, File.class);
            fileService.update(file, id);
            resp.setStatus(200);

        } catch (IOException e) {
//            e.printStackTrace();
            resp.sendError(500, "Can't update file");
        } catch (WrongIdException e) {
            resp.sendError(404, ("There's no file with id " + id));
        } catch (NullFieldsException e) {
            resp.sendError(400, "File contains null fields");
        }


        return ("Updating file " + id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/putFile/file_{fileID}/storage_{storageID}", produces = "text/plain")
    public @ResponseBody
    void putFile(@PathVariable("fileID") long fileID, @PathVariable("storageID") long storageID, HttpServletResponse resp) throws IOException {

        try {
            File file = fileService.findById(fileID);

            Storage storage = storageService.findById(storageID);

            fileService.put(storage, file);
            resp.setStatus(200);


        } catch (WrongIdException e) {
            resp.sendError(404, ("Wrong ID. Please check file and storage ID"));
        } catch (FileAlreadyInStorageException e) {
            resp.sendError(404, "File is already in storage");
        } catch (FormatNotSupportedException e) {
            resp.sendError(404, "File format is not supported by storage");
        }


    }

    @RequestMapping(method = RequestMethod.PUT, value = "/deleteFile/file_{fileID}/storage_{storageID}", produces = "text/plain")
    public @ResponseBody
    void deleteFileFromStorage(@PathVariable("fileID") long fileID, @PathVariable("storageID") long storageID, HttpServletResponse resp) throws IOException {

        try {
            File file = fileService.findById(fileID);
            Storage storage = storageService.findById(storageID);
            fileService.deleteFromStorage(storage, file);
            resp.setStatus(200);

        } catch (WrongIdException e) {
            resp.sendError(404, ("Wrong ID. Please check file and storage ID"));
        } catch (FileAlreadyInStorageException e) {
            resp.sendError(404, "There is no file in storage");
        } catch (NullFieldsException e) {
            resp.sendError(404, "File has no storage");
        }


    }

    @RequestMapping(method = RequestMethod.PUT, value = "/transferFile/file_{fileID}/fromStorage_{storageFromID}/toStorage_{storageToID}", produces = "text/plain")
    public @ResponseBody
    void transferFile(@PathVariable("fileID") Long fileID, @PathVariable("storageToID") Long storageToID, HttpServletResponse resp, @PathVariable("storageFromID") Long storageFromID) throws IOException {

        try {

            Storage storageTo = storageService.findById(storageToID);
            Storage storageFrom = storageService.findById((storageFromID));
            fileService.transferFile(storageFrom, storageTo, fileID);
            resp.setStatus(200);

        } catch (WrongIdException e) {
            resp.sendError(404, ("Wrong ID. Please check file ID"));
        } catch (FileAlreadyInStorageException e) {
            resp.sendError(404, "File is already in storage");
        } catch (NullFieldsException e) {
            resp.sendError(404, "File has no storage");
        }


    }

    @RequestMapping(method = RequestMethod.PUT, value = "/transferall/from_{storageFromID}/to_{storageToID}")
    public @ResponseBody
    void transferAll(@PathVariable("storageFromID") long storageFromID, @PathVariable("storageToID") long storageToID, HttpServletResponse resp) throws IOException {

        try {
            Storage storageTo = storageService.findById(storageToID);
            Storage storageFrom = storageService.findById((storageFromID));
            fileService.transferAll(storageFrom, storageTo);
            resp.setStatus(200);

        } catch (WrongIdException e) {
            resp.sendError(404, ("Wrong ID. Please check storage ID"));
        } catch (NullFieldsException e) {
            resp.sendError(404, "Storage(s) do(es) not exist(s)");
        }

    }


}



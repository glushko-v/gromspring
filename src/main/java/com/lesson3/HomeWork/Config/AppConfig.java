package com.lesson3.HomeWork.Config;

import com.lesson3.HomeWork.Controller.Controller;
import com.lesson3.HomeWork.DAO.FileDAO;
import com.lesson3.HomeWork.DAO.StorageDAO;
import com.lesson3.HomeWork.Service.FileService;
import com.lesson3.HomeWork.Service.StorageService;
import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.beans.ConstructorProperties;


@Configuration
@EnableWebMvc
@ComponentScan("com")
public class AppConfig extends WebMvcConfigurerAdapter{

    @Bean
    public File getFile() {
        File file = new File();
        return file;
    }

    @Bean
    public FileDAO getFileDAO() {
        FileDAO fileDAO = new FileDAO();
        return fileDAO;
    }

    @Bean
    public FileService getFileService() {
        FileService fileService = new FileService(getFileDAO());
        return fileService;

    }

    @Bean
    public Storage getStorage(){
        Storage storage = new Storage();
        return storage;
    }

    @Bean
    public StorageDAO getStorageDAO(){
        StorageDAO storageDAO = new StorageDAO();
        return storageDAO;
    }

    @Bean
    public StorageService getStorageService(){
        StorageService storageService = new StorageService(getStorageDAO());
        return storageService;
    }

    @Bean
    public Controller getController(){
        Controller controller = new Controller(getFileService(), getStorageService());
        return controller;
    }

}

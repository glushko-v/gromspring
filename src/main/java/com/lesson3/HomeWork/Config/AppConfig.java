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




@Configuration
@EnableWebMvc
@ComponentScan("com")
public class AppConfig  {

    @Bean
    public File getFile() {

        return new File();
    }

    @Bean
    public FileDAO getFileDAO() {

        return new FileDAO();
    }

    @Bean
    public FileService getFileService() {
        return new FileService(getFileDAO());


    }

    @Bean
    public Storage getStorage(){
        return new Storage();

    }

    @Bean
    public StorageDAO getStorageDAO(){
        return new StorageDAO();

    }

    @Bean
    public StorageService getStorageService(){
        return new StorageService(getStorageDAO());

    }

    @Bean
    public Controller getController(){
        return new Controller(getFileService(), getStorageService());

    }

}

package com.lesson3.HomeWork.Service;

import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.Storage;

public class FileService implements Service<File> {

    //проверка допустимого формата файла

    @Override
    public File save(File file) throws Exception {
        return null;
    }

    @Override
    public void delete(long id) throws Exception {

    }

    @Override
    public File update(File file, long id) throws Exception {
        return null;
    }

    @Override
    public File findById(long id) throws Exception {
        return null;
    }

    public boolean isFormatSupported(Storage storage, File file) {

        int count = 0;

        String[] formatsSupported = storage.getFormatsSupported().split(", ");

        for (String format : formatsSupported) {
            if (format.equals(file.getFormat())) count++;
        }


        return count > 0;
    }
}

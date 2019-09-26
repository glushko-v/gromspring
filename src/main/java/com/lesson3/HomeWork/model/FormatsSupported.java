package com.lesson3.HomeWork.model;



public enum FormatsSupported {

    txt,
    jpg,
    zip,
    rar,
    doc;

   public static String[] formats = {FormatsSupported.rar.toString(), FormatsSupported.txt.toString(),
    FormatsSupported.jpg.toString(), FormatsSupported.doc.toString(), FormatsSupported.zip.toString()};


}

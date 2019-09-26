package com.lesson3.HomeWork.model;


import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FILES")
public class File {

    private long id;
    private String name;
    private String format;
    private long size;
    private Storage storage;



    public File(String name, String format, long size, Storage storage) {

        this.name = name;
        this.format = format;
        this.size = size;
        this.storage = storage;
    }

    public File() {
    }

    @Id
    @Column(name = "FILEID")
    @SequenceGenerator(name = "F_SEQ", sequenceName = "FILE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "F_SEQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "FILENAME")
    @JsonProperty("Filename")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FILEFORMAT")
    @JsonProperty("Format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Column(name = "FILESIZE")
    @JsonProperty("Filesize")
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "STORAGE_ID")
    @JsonProperty("Storage")
    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id == file.id &&
                name.equals(file.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "File " + "id= " + id + " " + name + "." + format + ", " + "storage " + storage;
    }


}

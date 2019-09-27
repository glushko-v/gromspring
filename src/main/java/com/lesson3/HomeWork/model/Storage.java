package com.lesson3.HomeWork.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STORAGES")
public class Storage {

    private long id;
    private String formatsSupported;
    private String storageCountry;
    private long storageSize;
    private List<File> files;



    public Storage(String formatsSupported, String storageCountry, long storageSize) {

        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    public Storage() {
    }

    @Id
    @Column(name = "STORAGEID")
    @SequenceGenerator(name = "S_SEQ", sequenceName = "ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_SEQ")
    @JsonProperty("Storage_id")
    public long getId() {
        return id;
    }

    @Column(name = "FORMATSSUPPORTED")
    @JsonProperty("Formats")
    public String getFormatsSupported() {
        return formatsSupported;
    }

    @Column(name = "STORAGECOUNTRY")
    @JsonProperty("Country")
    public String getStorageCountry() {
        return storageCountry;
    }

    @Column(name = "STORAGEMAXSIZE")
    @JsonProperty("Size")
    public long getStorageSize() {
        return storageSize;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setFormatsSupported(String formatsSupported) {
        this.formatsSupported = formatsSupported;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    @Override
    public String toString() {
        return "Storage " + id + " " + storageCountry;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage", fetch = FetchType.EAGER)
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return id == storage.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

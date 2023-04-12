package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("documents")
public class Document {
    @Id
    private Long id;

    private String fileName;
    private byte[] file;
    private String hash512;
    private String hash256;
    private Long clientId;

    public Document(String fileName, byte[] file, String hash512, String hash256, Long clientId) {
        this(null, fileName, file, hash512, hash256, clientId);
    }

    @PersistenceCreator
    public Document(Long id, String fileName, byte[] file, String hash512, String hash256, Long clientId) {
        this.id = id;
        this.fileName = fileName;
        this.file = file;
        this.hash512 = hash512;
        this.hash256 = hash256;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {

        return file;
    }

    public void setFile(byte[] file) {

        this.file = file;
    }

    public String getHash512() {
        return hash512;
    }
    public String getHash256() {
        return hash256;
    }

    public void setHash512(String hash512) {
        this.hash512 = hash512;
    }
    public void setHash256(String hash256) {
        this.hash256 = hash256;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {

        return "Client{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", hash512='" + hash512 + '\'' +
                ", hash256='" + hash256 + '\'' +
                ", clientId=" + clientId +
                '}';

    }
}
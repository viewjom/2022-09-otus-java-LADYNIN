package ru.otus.json.model;

import java.util.List;

public class Result {

    private String id;
    private String name;
    private int zip;
    private String type;
    private String typeShort;
    private String okato;
    private String contentType;
    private String guid;
    private String ifnsfl;
    private String ifnsul;
    private String oktmo;
    private String parentGuid;
    private String cadnum;
    private List<Parents> parents;

    public List<Parents> getParents() {

        return parents;
    }

    /*
        public List<String> getStreets() {

            return parents.stream().map(s->s.getName()).toList();
        }
    */
    public void setParents(List<Parents> parents) {
        this.parents = parents;
    }

    public Result() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeShort() {
        return typeShort;
    }

    public void setTypeShort(String typeShort) {
        this.typeShort = typeShort;
    }

    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getIfnsfl() {
        return ifnsfl;
    }

    public void setIfnsfl(String ifnsfl) {
        this.ifnsfl = ifnsfl;
    }

    public String getIfnsul() {
        return ifnsul;
    }

    public void setIfnsul(String ifnsul) {
        this.ifnsul = ifnsul;
    }

    public String getOktmo() {
        return oktmo;
    }

    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getCadnum() {
        return cadnum;
    }

    public void setCadnum(String cadnum) {
        this.cadnum = cadnum;
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", zip=" + zip +
                ", type='" + type + '\'' +
                ", parents=" + parents +
                '}';
    }
}

package com.example.project;

public class Modelsupplier {

    private int id_sup;
    private String suppliername, supplierdescription;
    private long start;

    public Modelsupplier() {
    }

    public Modelsupplier(int id_sup, String suppliername, String supplierdescription, long start) {
        this.id_sup = id_sup;
        this.suppliername = suppliername;
        this.supplierdescription = supplierdescription;
        this.start = start;
    }

    public Modelsupplier(String suppliername, String supplierdescription, long start) {
        this.suppliername = suppliername;
        this.supplierdescription = supplierdescription;
        this.start = start;
    }

    public int getId_sup() {
        return id_sup;
    }

    public void setId_sup(int id_sup) {
        this.id_sup = id_sup;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getSupplierdescription() {
        return supplierdescription;
    }

    public void setSupplierdescription(String supplierdescription) {
        this.supplierdescription = supplierdescription;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}

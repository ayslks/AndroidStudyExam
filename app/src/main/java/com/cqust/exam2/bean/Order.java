package com.cqust.exam2.bean;

public class Order {
    private int oid;
    private String name;
    private String size;
    private String context;
    private int pid;

    public Order(int oid, String name, String size, String context, int pid) {
        this.oid = oid;
        this.pid = pid;
        this.name = name;
        this.context = context;
        this.size = size;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Order() {
    }

    public Order(String name, String size, String context, int pid) {
        this.name = name;
        this.size = size;
        this.context = context;
        this.pid = pid;
    }

    public Order(int oid, String name, String size, String context) {
        this.oid = oid;
        this.name = name;
        this.size = size;
        this.context = context;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}

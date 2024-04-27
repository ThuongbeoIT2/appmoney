package com.example.dtapp.model;

public class Storerage {
    private static int cateId;
    private static String catename;
    private static int spenId;

    public static String getCatename() {
        return catename;
    }

    public static void setCatename(String catename) {
        Storerage.catename = catename;
    }

    public static int getCateId() {
        return cateId;
    }

    public static void setCateId(int cateId) {
        Storerage.cateId = cateId;
    }

    public static int getSpenId() {
        return spenId;
    }

    public static void setSpenId(int spenId) {
        Storerage.spenId = spenId;
    }
}

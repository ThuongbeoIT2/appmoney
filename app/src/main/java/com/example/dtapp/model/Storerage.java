package com.example.dtapp.model;

public class Storerage {
    private static int cateId;
    private static String catename;
    private static int spenId;
    private static long cost;
    private static String title;
    private static String date;

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Storerage.title = title;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Storerage.date = date;
    }

    public static long getCost() {
        return cost;
    }

    public static void setCost(long cost) {
        Storerage.cost = cost;
    }

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

package com.example.dtapp.model;


public class TradeResponse {

    private int tradeId;

    private String title;

    private String date;

    private long cost;

    private String cateName;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public TradeResponse(int tradeId, String title, String date, long cost, String cateName) {
        this.tradeId = tradeId;
        this.title = title;
        this.date = date;
        this.cost = cost;
        this.cateName = cateName;
    }
}

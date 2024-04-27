package com.example.dtapp.model;


public class SpendingLimitResponse {

    private int spendId;
    private long remainingAmount;
    private long spendingLimit;

    private long expenditure;

    private String date;

    private String cateName;

    public void setRemainingAmount(long remainingAmount) {
        this.remainingAmount=this.spendingLimit-this.expenditure;
    }

    public int getSpendId() {
        return spendId;
    }

    public void setSpendId(int spendId) {
        this.spendId = spendId;
    }

    public long getRemainingAmount() {
        return remainingAmount;
    }

    public long getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(long spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    public long getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(long expenditure) {
        this.expenditure = expenditure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}

package com.example.dtapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dtapp.model.TradeResponse;

import java.util.ArrayList;
import java.util.List;

public class TradeViewModel extends ViewModel {
    private MutableLiveData<List<TradeResponse>> tradeListLiveData;

    public LiveData<List<TradeResponse>> getTradeListLiveData() {
        if (tradeListLiveData == null) {
            tradeListLiveData = new MutableLiveData<>();
            loadTradeList();
        }
        return tradeListLiveData;
    }

    private void loadTradeList() {

        List<TradeResponse> tradeItemList = new ArrayList<>();
        // Add sample data
        tradeItemList.add(new TradeResponse(1, "Title 1", "Date 1", 1000, "Category 1"));
        tradeItemList.add(new TradeResponse(2, "Title 2", "Date 2", 2000, "Category 2"));
        tradeItemList.add(new TradeResponse(3, "Title 3", "Date 3", 3000, "Category 3"));
        tradeListLiveData.setValue(tradeItemList);
    }
}
package com.biog.unihiveandroid;

import com.google.gson.JsonArray;

public interface DataListener {
    void onDataReceived(JsonArray adminData);
    void onFetchFailure(Throwable t);
}

package com.biog.unihiveandroid;

import com.google.gson.JsonArray;

public interface EventDataListener {
    void onEventDataReceived(JsonArray eventData);
    void onFetchFailure(Throwable t);
}


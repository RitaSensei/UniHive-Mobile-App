package com.biog.unihiveandroid;

import com.google.gson.JsonArray;

public interface ClubDataListener {
    void onClubDataReceived(JsonArray clubData);
    void onFetchFailure(Throwable t);
}

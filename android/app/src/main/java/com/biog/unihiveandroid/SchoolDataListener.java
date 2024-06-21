package com.biog.unihiveandroid;

import com.google.gson.JsonArray;

public interface SchoolDataListener {
    void onSchoolDataReceived(JsonArray schoolData);
    void onFetchFailure(Throwable t);
}

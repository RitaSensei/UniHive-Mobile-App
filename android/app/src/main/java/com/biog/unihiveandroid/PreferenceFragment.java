package com.biog.unihiveandroid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
//        addPreferencesFromResource(R.xml.preferences);
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}

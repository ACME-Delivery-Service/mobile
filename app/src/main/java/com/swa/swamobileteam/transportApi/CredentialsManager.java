package com.swa.swamobileteam.transportApi;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A manager for user credentials.
 */
public class CredentialsManager {
    private static String PERF_TOKEN_KEY = "nikita-lox-282";

    private SharedPreferences preferences;

    public CredentialsManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveApiAuthenticationToken(@NonNull String token) {
        preferences.edit().putString(PERF_TOKEN_KEY, token).apply();
    }

    @Nullable
    public String getApiAuthenticationToken() {
        return preferences.getString(PERF_TOKEN_KEY, null);
    }
}

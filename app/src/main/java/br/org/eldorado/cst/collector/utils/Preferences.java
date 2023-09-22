package br.org.eldorado.cst.collector.utils;

import android.content.Context;
import android.content.SharedPreferences;

import br.org.eldorado.cst.collector.constants.Constants;

public class Preferences {

    private final Context context;
    private final SharedPreferences prefs;

    public Preferences(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        if (notContains(context, Constants.PREFERENCES.PROTOCOL_KEY))
            setProtocol(Constants.PREFERENCES.PROTOCOL_VALUE);

        if (notContains(context, Constants.PREFERENCES.ADDRESS_KEY))
            setAddress(Constants.PREFERENCES.ADDRESS_VALUE);

        if (notContains(context, Constants.PREFERENCES.PORT_KEY))
            setPort(Constants.PREFERENCES.PORT_VALUE);
    }

    public Boolean getProtocol() {
        return prefs.getBoolean(Constants.PREFERENCES.PROTOCOL_KEY,
                                Constants.PREFERENCES.PROTOCOL_VALUE);
    }

    public String getAddress() {
        return prefs.getString(Constants.PREFERENCES.ADDRESS_KEY,
                               Constants.PREFERENCES.ADDRESS_VALUE);
    }

    public Integer getPort() {
        return prefs.getInt(Constants.PREFERENCES.PORT_KEY,
                            Constants.PREFERENCES.PORT_VALUE);
    }

    public void setProtocol(Boolean value) {
        prefs.edit().putBoolean(Constants.PREFERENCES.PROTOCOL_KEY,
                                value).apply();
    }

    public void setAddress(String value) {
        prefs.edit().putString(Constants.PREFERENCES.ADDRESS_KEY,
                               value).apply();
    }

    public void setPort(Integer value) {
        prefs.edit().putInt(Constants.PREFERENCES.PORT_KEY,
                               value).apply();
    }

    private Boolean notContains(Context ctx, String key) {
        return !prefs.contains(key);
    }
}

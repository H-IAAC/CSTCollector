package br.org.eldorado.cst.collector.data.api;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import br.org.eldorado.cst.collector.utils.Preferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    private Retrofit retrofit = null;

    private Preferences preferences;

    public ClientApi(Context context) {
        preferences = new Preferences(context);
    }

    public Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(5, TimeUnit.MINUTES)
                .callTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();

        String protocol = preferences.getProtocolAsString();
        String baseUrl = protocol + "://" + preferences.getAddress() + ":" + preferences.getPort();

        Log.i(TAG, "Configure to sent data to: " + baseUrl);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}

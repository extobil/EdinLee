package com.project.edwinuas_nasmoco.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {

    public static String BASE_URL="https://www.candratri.my.id/uas_nasmocoapp/";



    public static String IMAGE_BASE_URL= BASE_URL + "images/";


    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient() // Tambahkan ini untuk mengatasi JSON malformed
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Gunakan Gson yang sudah di-set lenient
                    .build();
        }
        return retrofit;
    }


}

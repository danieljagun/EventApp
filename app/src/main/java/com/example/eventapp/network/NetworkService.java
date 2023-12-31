package com.example.eventapp.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://api.predicthq.com/";
    private static Retrofit retrofit = null;

    public static PredictAPI getPredictAPI() {
        if (retrofit == null) {
            ObjectMapper mapper = new ObjectMapper();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .build();
        }
        return retrofit.create(PredictAPI.class);
    }
}

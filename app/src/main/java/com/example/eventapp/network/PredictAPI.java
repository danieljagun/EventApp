package com.example.eventapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PredictAPI {
    @GET("/v1/events")
    Call<EventResponse> getEventsInIreland(
            @Query("country") String countryCode,
            @Query("category") String categories,
            @Query("limit") int limit, // Add the limit parameter
            @Header("Authorization") String apiKey
    );
}


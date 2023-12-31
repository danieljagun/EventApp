package com.example.eventapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.models.Event;
import com.example.eventapp.models.EventAdapter;
import com.example.eventapp.network.EventResponse;
import com.example.eventapp.network.NetworkService;
import com.example.eventapp.network.PredictAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        recyclerView = findViewById(R.id.recycler_view);

        eventAdapter = new EventAdapter(new ArrayList<>(), new EventAdapter.OnEventItemClickListener() {
            @Override
            public void onEventClick(Event event) {
                // Handle event click
            }

            @Override
            public void onEventBook(Event event) {
                // Handle event booking
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);

        // Get PredictHQ API service using NetworkService
        PredictAPI predictAPI = NetworkService.getPredictAPI();

        // Fetch events
        String apiKey = "HpNJmTdJxAMbsrySq7yAWW3neOOJkXsvT_O1dzKJ"; // Replace with your actual API key
        String countryCode = "IE"; // Replace with your desired locale
        String categories = "concerts,festivals,sports"; // Replace with your desired categories
        int limit = 50;

        Call<EventResponse> call = predictAPI.getEventsInIreland(countryCode, categories, limit, "Bearer " + apiKey);

        // Execute the call asynchronously
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Log the entire response body
                    Log.d("API_RESPONSE", "Response: " + response.body());

                    // Handle successful response here
                    EventResponse eventResponse = response.body();
                    List<Event> events = null;

                    // Check if the response is not null and parse JSON
                    if (eventResponse != null && eventResponse.getEvents() != null) {
                        events = eventResponse.getEvents();
                    }

                    // Now you can work with the 'events' list
                    if (events != null) {
                        Log.d("EVENTS_SIZE", "Number of events retrieved: " + events.size());
                        eventAdapter.setEventList(events);
                    } else {
                        Log.e("API_CALL", "No events found or response structure mismatch");
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                // Handle failure
                Log.e("API_CALL", "Failure: " + t.getMessage());
            }
        });
    }
}

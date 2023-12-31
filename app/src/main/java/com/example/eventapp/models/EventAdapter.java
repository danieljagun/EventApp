package com.example.eventapp.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private static List<Event> eventList;
    private final OnEventItemClickListener listener;

    public EventAdapter(List<Event> eventList, OnEventItemClickListener listener) {
        this.eventList = eventList != null ? eventList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.bind(event);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventClick(event);
            }
        });

        holder.bookButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventBook(event);
            }
        });
    }

    // Add this method to update the event list in the adapter
    public void setEventList(List<Event> events) {
        this.eventList = events;
        notifyDataSetChanged(); // Notify the adapter of changes
    }

    @Override
    public int getItemCount() {
        return eventList != null ? eventList.size() : 0; // Check for null before calling size()
    }

    public interface OnEventItemClickListener {
        void onEventClick(Event event);

        void onEventBook(Event event);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView eventNameTextView;
        private final TextView startDateTextView; // Updated field
        private final TextView eventLocationTextView;
        private final TextView endDateTextView; // Updated field
        private final Button bookButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.text_view_event_title); // Updated ID
            startDateTextView = itemView.findViewById(R.id.text_view_start_date); // Updated ID
            eventLocationTextView = itemView.findViewById(R.id.text_view_event_location);
            endDateTextView = itemView.findViewById(R.id.text_view_end_date); // Updated ID
            bookButton = itemView.findViewById(R.id.button_book_event);
        }

        public void bind(Event event) {
            eventNameTextView.setText(event.getTitle());
            startDateTextView.setText(formatDate(event.getStart()));
            eventLocationTextView.setText(formatLocation(event.getLocation()));
            endDateTextView.setText(formatDate(event.getEnd()));
        }

        private String formatDate(String dateString) {
            try {
                // Parse the date string into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                Date date = dateFormat.parse(dateString);

                // Format the parsed date into the desired format
                SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d' 'yyyy' @ 'HH:mm", Locale.getDefault());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return ""; // Return empty string if date parsing fails
        }

        private String formatLocation(List<Double> location) {
            if (location != null && location.size() == 2) {
                double latitude = location.get(1);
                double longitude = location.get(0);
                return String.format(Locale.getDefault(), "(%.6f, %.6f)", latitude, longitude);
            }
            return ""; // Return empty string or default value if location is not valid
        }
    }
}

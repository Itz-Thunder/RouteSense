package com.example.routesense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TripSetupActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_setup);

        dbHelper = new DatabaseHelper(this);

        EditText sourceInput = findViewById(R.id.inputSource);
        EditText destInput = findViewById(R.id.inputDestination);
        EditText typeInput = findViewById(R.id.inputVehicleType);
        EditText weightInput = findViewById(R.id.inputWeight);
        EditText cargoInput = findViewById(R.id.inputCargo);
        EditText priorityInput = findViewById(R.id.inputPriority);
        Button findRouteBtn = findViewById(R.id.btnFindSafeRoute);

        findRouteBtn.setOnClickListener(v -> {
            String source = sourceInput.getText().toString().trim();
            String dest = destInput.getText().toString().trim();
            String weightStr = weightInput.getText().toString().trim();
            String priority = priorityInput.getText().toString().trim();

            if (source.isEmpty() || dest.isEmpty()) {
                Toast.makeText(this, "Please enter Source and Destination", Toast.LENGTH_SHORT).show();
                return;
            }

            int weight = weightStr.isEmpty() ? 0 : Integer.parseInt(weightStr);
            String tripId = "TRIP_" + System.currentTimeMillis();

            // Save trip to SQLite database
            boolean isInserted = dbHelper.insertTrip(tripId, source, dest, weight, priority);
            if (isInserted) {
                Toast.makeText(this, "Trip saved locally & route calculated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error saving trip to database", Toast.LENGTH_SHORT).show();
            }

            // Launch Map Activity
            Intent intent = new Intent(TripSetupActivity.this, MapActivity.class);
            startActivity(intent);
        });
    }
}
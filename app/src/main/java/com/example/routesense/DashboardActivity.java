package com.example.routesense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button startTripBtn = findViewById(R.id.btnStartTrip);

        startTripBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, TripSetupActivity.class);
            startActivity(intent);
        });
    }
}
package com.example.routesense;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TripSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_setup);

        EditText sourceInput = findViewById(R.id.inputSource);
        EditText destInput = findViewById(R.id.inputDestination);
        EditText typeInput = findViewById(R.id.inputVehicleType);
        EditText weightInput = findViewById(R.id.inputWeight);
        EditText cargoInput = findViewById(R.id.inputCargo);
        EditText priorityInput = findViewById(R.id.inputPriority);
        Button findRouteBtn = findViewById(R.id.btnFindSafeRoute);

        findRouteBtn.setOnClickListener(v -> {
            String source = sourceInput.getText().toString();
            String dest = destInput.getText().toString();

            if (source.isEmpty() || dest.isEmpty()) {
                Toast.makeText(this, "Please enter Source and Destination", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simulating the passing of data as per the blueprint demo example (Aizawl to Silchar, 18-ton)
            String message = "Routing " + weightInput.getText().toString() + "-ton truck from " + source + " to " + dest;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // TODO: Intent to Member 2's Map screen will go here once they push their code
        });
    }
}
package com.example.routesense;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable top-left back button navigation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Route Comparison");
        }

        // CRITICAL: Initialize OSMDroid configuration and user agent to load map tiles properly
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        // Define exact Start (Source) and End (Destination) coordinates
        GeoPoint sourcePoint = new GeoPoint(23.7271, 92.7176); // Aizawl
        GeoPoint destPoint = new GeoPoint(24.8170, 92.7932);   // Silchar

        // Center map midway between source and destination
        GeoPoint centerPoint = new GeoPoint(
                (sourcePoint.getLatitude() + destPoint.getLatitude()) / 2,
                (sourcePoint.getLongitude() + destPoint.getLongitude()) / 2
        );
        map.getController().setZoom(9.0);
        map.getController().setCenter(centerPoint);

        // Evaluate routes dynamically via AI Risk Engine (18-ton heavy truck)
        int vehicleWeight = 18;
        RiskEngine.RouteEvaluation evalA = RiskEngine.evaluateRoute("Route A", 50, vehicleWeight, true, false);
        RiskEngine.RouteEvaluation evalB = RiskEngine.evaluateRoute("Route B", 20, vehicleWeight, false, true);
        RiskEngine.RouteEvaluation evalC = RiskEngine.evaluateRoute("Route C", 35, vehicleWeight, false, true);

        // Draw Route A (Northern detour with hazard)
        List<GeoPoint> pointsA = new ArrayList<>();
        pointsA.add(sourcePoint);
        pointsA.add(new GeoPoint(24.2000, 92.6000));
        pointsA.add(destPoint);
        drawRoute(pointsA, Color.RED, evalA);

        // Draw Route B (Direct recommended path)
        List<GeoPoint> pointsB = new ArrayList<>();
        pointsB.add(sourcePoint);
        pointsB.add(new GeoPoint(24.0000, 92.8500));
        pointsB.add(destPoint);
        drawRoute(pointsB, Color.GREEN, evalB);

        // Draw Route C (Alternative eastern path)
        List<GeoPoint> pointsC = new ArrayList<>();
        pointsC.add(sourcePoint);
        pointsC.add(new GeoPoint(24.1000, 93.1000));
        pointsC.add(destPoint);
        drawRoute(pointsC, Color.rgb(255, 165, 0), evalC);

        // Add Source Marker
        Marker sourceMarker = new Marker(map);
        sourceMarker.setPosition(sourcePoint);
        sourceMarker.setTitle("Origin: Aizawl Hub");
        map.getOverlays().add(sourceMarker);

        // Add Hazard Marker along Route A
        Marker hazardMarker = new Marker(map);
        hazardMarker.setPosition(new GeoPoint(24.2000, 92.6000));
        hazardMarker.setTitle("⚠️ Active Landslide Zone (Severity: High)");
        map.getOverlays().add(hazardMarker);

        // Add Destination Marker
        Marker destMarker = new Marker(map);
        destMarker.setPosition(destPoint);
        destMarker.setTitle("Destination Hub: Silchar");
        map.getOverlays().add(destMarker);

        map.invalidate();
    }

    private void drawRoute(List<GeoPoint> points, int color, RiskEngine.RouteEvaluation eval) {
        Polyline line = new Polyline();
        line.setPoints(points);
        line.setColor(color);
        if (eval.isRecommended) {
            line.setWidth(12f);
        } else {
            line.setWidth(6f);
        }
        line.setTitle(eval.routeName + " | Risk: " + eval.riskScore + " | " + eval.status);
        map.getOverlays().add(line);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Returns to Trip Setup screen
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onResume() { super.onResume(); if (map != null) map.onResume(); }
    @Override protected void onPause() { super.onPause(); if (map != null) map.onPause(); }
}
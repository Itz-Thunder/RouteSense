package com.example.routesense;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

        // OSMDroid configuration
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map);

        // Request permissions if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        // Center map on demo region (e.g., Aizawl area coordinates)
        GeoPoint startPoint = new GeoPoint(23.7271, 92.7176);
        map.getController().setZoom(11.0);
        map.getController().setCenter(startPoint);

        drawDemoRoutes();
    }

    private void drawDemoRoutes() {
        // Route A (High Risk / Not Allowed) - Red Line
        List<GeoPoint> routeAPoints = new ArrayList<>();
        routeAPoints.add(new GeoPoint(23.7271, 92.7176));
        routeAPoints.add(new GeoPoint(23.7500, 92.8000));
        routeAPoints.add(new GeoPoint(23.8000, 92.9000));

        Polyline lineA = new Polyline();
        lineA.setPoints(routeAPoints);
        lineA.setColor(android.graphics.Color.RED);
        lineA.setTitle("Route A: 102 km / Risk 81 (Not Allowed)");
        map.getOverlays().add(lineA);

        // Route B (Recommended) - Green Line
        List<GeoPoint> routeBPoints = new ArrayList<>();
        routeBPoints.add(new GeoPoint(23.7271, 92.7176));
        routeBPoints.add(new GeoPoint(23.6500, 92.7500));
        routeBPoints.add(new GeoPoint(23.6000, 92.9000));

        Polyline lineB = new Polyline();
        lineB.setPoints(routeBPoints);
        lineB.setColor(android.graphics.Color.GREEN);
        lineB.setWidth(10f); // Make recommended route thicker
        lineB.setTitle("Route B: 115 km / Risk 29 (Recommended)");
        map.getOverlays().add(lineB);

        // Route C (Alternative) - Orange Line
        List<GeoPoint> routeCPoints = new ArrayList<>();
        routeCPoints.add(new GeoPoint(23.7271, 92.7176));
        routeCPoints.add(new GeoPoint(23.7000, 92.8200));
        routeCPoints.add(new GeoPoint(23.6000, 92.9000));

        Polyline lineC = new Polyline();
        lineC.setPoints(routeCPoints);
        lineC.setColor(android.graphics.Color.YELLOW);
        lineC.setTitle("Route C: 110 km / Risk 52 (Alternative)");
        map.getOverlays().add(lineC);

        // Add Hazard Marker
        Marker hazardMarker = new Marker(map);
        hazardMarker.setPosition(new GeoPoint(23.7500, 92.8000));
        hazardMarker.setTitle("⚠️ Landslide Hazard (Severity: High)");
        map.getOverlays().add(hazardMarker);

        // Add Destination Marker
        Marker destMarker = new Marker(map);
        destMarker.setPosition(new GeoPoint(23.6000, 92.9000));
        destMarker.setTitle("Destination: Silchar Hub");
        map.getOverlays().add(destMarker);

        map.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) map.onPause();
    }
}
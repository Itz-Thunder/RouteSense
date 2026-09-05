package com.example.routesense;

public class Hazard {
    public String id;
    public String type;
    public String severity;
    public double latitude;
    public double longitude;
    public String roadSegmentId;
    public long timestamp;
    public boolean synced;

    public Hazard(String id, String type, String severity, double latitude, double longitude, String roadSegmentId, long timestamp, boolean synced) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadSegmentId = roadSegmentId;
        this.timestamp = timestamp;
        this.synced = synced;
    }
}
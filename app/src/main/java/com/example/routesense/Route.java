package com.example.routesense;

public class Route {
    public String id;
    public String name;
    public double distance;
    public int riskScore;
    public boolean truckAllowed;

    public Route(String id, String name, double distance, int riskScore, boolean truckAllowed) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.riskScore = riskScore;
        this.truckAllowed = truckAllowed;
    }
}
package com.example.routesense;

public class Trip {
    public String id;
    public String source;
    public String destination;
    public Vehicle vehicle;
    public int weight;
    public String priority;

    public Trip(String id, String source, String destination, Vehicle vehicle, int weight, String priority) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.vehicle = vehicle;
        this.weight = weight;
        this.priority = priority;
    }
}
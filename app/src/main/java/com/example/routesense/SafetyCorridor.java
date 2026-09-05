package com.example.routesense;

public class SafetyCorridor {
    public String tripId;
    public Route primaryRoute;
    public Route alternativeRoute;
    public String cachedHazards;
    public int cachedRisk;
    public String cachedWeather;
    public String restrictions;
    public String emergencyLocations;

    public SafetyCorridor(String tripId, Route primaryRoute, Route alternativeRoute, String cachedHazards, int cachedRisk, String cachedWeather, String restrictions, String emergencyLocations) {
        this.tripId = tripId;
        this.primaryRoute = primaryRoute;
        this.alternativeRoute = alternativeRoute;
        this.cachedHazards = cachedHazards;
        this.cachedRisk = cachedRisk;
        this.cachedWeather = cachedWeather;
        this.restrictions = restrictions;
        this.emergencyLocations = emergencyLocations;
    }
}
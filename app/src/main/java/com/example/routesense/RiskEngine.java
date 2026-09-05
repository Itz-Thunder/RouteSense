package com.example.routesense;

public class RiskEngine {

    // Mathematical formula for dynamic route risk scoring:
    // Risk Score = Base Risk + (Vehicle Weight * Weight Factor) + (Hazard Severity Index * 10) - (Corridor Safety Rating)
    public static RouteEvaluation evaluateRoute(String routeName, double baseRisk, int vehicleWeightTons, boolean hasLandslideHazard, boolean isHeavyVehicleAllowed) {

        // Hard constraint check: If heavy trucks are banned on this route, disqualify immediately
        if (vehicleWeightTons > 15 && !isHeavyVehicleAllowed) {
            return new RouteEvaluation(routeName, 999, "Truck Not Allowed (Bridge/Weight Limit Exceeded)", false);
        }

        double weightPenalty = vehicleWeightTons * 0.8;
        double hazardMultiplier = hasLandslideHazard ? 25.0 : 0.0;

        // Final computed risk score calculation
        double finalRiskScore = baseRisk + weightPenalty + hazardMultiplier;
        finalRiskScore = Math.min(Math.max(finalRiskScore, 0), 100); // Clamp between 0 and 100

        String recommendation;
        boolean isRecommended = false;

        if (finalRiskScore < 40) {
            recommendation = "Recommended (Optimal Safety)";
            isRecommended = true;
        } else if (finalRiskScore <= 70) {
            recommendation = "Alternative (Moderate Caution)";
        } else {
            recommendation = "High Risk (Avoid if possible)";
        }

        return new RouteEvaluation(routeName, (int) finalRiskScore, recommendation, isRecommended);
    }

    public static class RouteEvaluation {
        public String routeName;
        public int riskScore;
        public String status;
        public boolean isRecommended;

        public RouteEvaluation(String routeName, int riskScore, String status, boolean isRecommended) {
            this.routeName = routeName;
            this.riskScore = riskScore;
            this.status = status;
            this.isRecommended = isRecommended;
        }
    }
}

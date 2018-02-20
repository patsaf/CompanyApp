package com.example.patrycja.companyapp.company.predicates;

/**
 * This class is needed for GUI purposes
 */
public class PredicateInfo {

    private final Predicates condition;
    private final String conditionDetails;

    public PredicateInfo(Predicates condition, String conditionDetails) {
        this.condition = condition;
        this.conditionDetails = conditionDetails;
    }

    public Predicates getCondition() {
        return condition;
    }

    public String getConditionDetails() {
        return conditionDetails;
    }

    public String toString() {
        String output = "";
        switch(condition) {
            case EMAIL:
                output = "Employs developers only with " + conditionDetails + " email domain";
                break;
            case GENDER:
                output =  "Employs only " + conditionDetails + " developers";
                break;
            case COUNTRY:
                output =  "Employs only developers from " + conditionDetails;
                break;
            case UNIVERSITY:
                output = "Employs only " + conditionDetails + " graduates";
                break;
        }
        return output + "\n";
    }
}


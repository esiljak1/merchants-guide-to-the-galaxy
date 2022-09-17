package com.esiljak.models;

public abstract class Query {
    private IntergalacticConversion conversion;

    public Query(IntergalacticConversion conversion) {
        this.conversion = conversion;
    }

    public abstract String queryAnswer(String query);
}

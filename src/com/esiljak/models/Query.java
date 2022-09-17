package com.esiljak.models;

import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;

public abstract class Query {
    private IntergalacticConversion conversion;

    public Query(IntergalacticConversion conversion) {
        this.conversion = conversion;
    }

    public IntergalacticConversion getConversion() {
        return conversion;
    }

    public abstract String queryAnswer(String query) throws IllegalQueryException, IllegalSellingItemFormatException, IllegalRomanNumeralException;
}

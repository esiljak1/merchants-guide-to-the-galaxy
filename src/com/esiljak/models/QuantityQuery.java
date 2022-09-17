package com.esiljak.models;

import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;
import com.esiljak.helpers.StringParser;

import java.util.Optional;

public class QuantityQuery extends Query {
    public QuantityQuery(IntergalacticConversion conversion) {
        super(conversion);
    }

    @Override
    public String queryAnswer(String query) throws IllegalQueryException, IllegalSellingItemFormatException, IllegalRomanNumeralException {
        var list = StringParser.getNumberCodeForQuantityQuery(query);
        String romanNumeral = getConversion().extractRomanNumber(list);
        RomanNumeral roman = new RomanNumeral(romanNumeral);

        Optional<String> answer = list.stream().reduce((s1, s2) -> s1.trim() + " " + s2.trim());
        if (answer.isEmpty())
            return "";

        return answer.get() + " is " + roman.getValue();
    }
}

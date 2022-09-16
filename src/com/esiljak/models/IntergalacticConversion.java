package com.esiljak.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntergalacticConversion {
    private Map<String, String> entries;

    public IntergalacticConversion() {
        entries = new HashMap<>();
    }

    public IntergalacticConversion(Map<String, String> entries) {
        this.entries = entries;
    }

    public Map<String, String> getEntries() {
        return Collections.unmodifiableMap(entries);
    }

    public void setEntries(Map<String, String> entries) {
        this.entries = entries;
    }

    public void addEntry(String key, String value){
        entries.put(key, value);
    }
}

package org.mql.java.common;

import java.util.HashMap;
import java.util.Map;

public enum Modifiers {
    PUBLIC("-"),
    PRIVATE("+"),
    PROTECTED("#"),
    PACKAGE("~");

    public final String label;

    private static final Map<String, Modifiers> LABEL_MAP = new HashMap<>();

    static {
        for (Modifiers modifier : Modifiers.values()) {
            LABEL_MAP.put(modifier.label, modifier);
        }
    }

    private Modifiers(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Modifiers fromLabel(String label) {
        return LABEL_MAP.get(label);
    }
    
    
    
}

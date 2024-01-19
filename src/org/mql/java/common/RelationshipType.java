package org.mql.java.common;

import java.util.HashMap;
import java.util.Map;

public enum RelationshipType {
    
	
	DEPENDENCY(".....>"),
    ASSOCIATION("-----"),
    AGGREGATION("-----o"),
    COMPOSITION("-----*"),
    GENERALIZATION("-----|>"),
    IMLEMENTATION(".....|>"),
    ;

    private String symbol;

    private static final Map<String, RelationshipType> SYMBOL_MAP = new HashMap<>();

    static {
        for (RelationshipType type : RelationshipType.values()) {
            SYMBOL_MAP.put(type.symbol, type);
        }
    }

    private RelationshipType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public static RelationshipType fromSymbol(String symbol) {
        return SYMBOL_MAP.get(symbol);
    }
}

package com.envyleague.cricket.domain;

import java.io.Serializable;

public enum MatchType implements Serializable {
    LEAGUE(1),
    QUARTER_FINAL(2),
    SEMI_FINAL(3),
    FINAL(4)
    ;

    private int multiplier;

    private MatchType(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}

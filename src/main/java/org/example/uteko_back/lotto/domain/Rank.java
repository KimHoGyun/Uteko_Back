package org.example.uteko_back.lotto.domain;

public enum Rank {
    FIRST(6, 2000000_000, "1등 (6개 일치)"),
    SECOND(5, 30000000, "2등 (5개 일치, 보너스 볼 일치)"),
    THIRD(5, 1500_000, "3등 (5개 일치)"),
    FOURTH(4, 50000, "4등 (4개 일치)"),
    FIFTH(3, 5000, "5등 (3개 일치)"),
    MISS(0, 0, "낙첨");

    private final int matchCount;
    private final long defaultPrizeMoney;
    private final String description;

    Rank(int matchCount, long defaultPrizeMoney, String description) {
        this.matchCount = matchCount;
        this.defaultPrizeMoney = defaultPrizeMoney;
        this.description = description;
    }

    public long getDefaultPrizeMoney() {
        return defaultPrizeMoney;
    }

    public String getDescription() {
        return description;
    }

    public static Rank valueOf(int matchCount, boolean bonusMatch) {
        if (matchCount == 6) { return FIRST; }
        if (matchCount == 5 && bonusMatch) { return SECOND; }
        if (matchCount == 5) { return THIRD; }
        if (matchCount == 4) { return FOURTH; }
        if (matchCount == 3) { return FIFTH; }
        return MISS;
    }

    public long getPrizeMoney(long firstPrize) {
        if (this == FIRST) {
            return firstPrize;
        }
        return defaultPrizeMoney;
    }
}
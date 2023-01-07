package com.example.foodies.enums;

public enum RecipeMadeTimeEnum {
    FIVE_TEN(5, 10, "5-10 min"),
    TEN_TWENTY(10, 30, "10-30 min"),
    THIRTY_SIXTY(30, 60, "30-60 min"),
    SIXTY_PLUS(60, 240, "60+ min");

    private String timeByText;
    private final int minTime;
    private final int maxTime;

    RecipeMadeTimeEnum(int minTime, int maxTime, String timeByText) {
        this.timeByText = timeByText;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public String getTimeByText() {
        return timeByText;
    }

    public void setTimeByText(String timeByText) {
        this.timeByText = timeByText;
    }

    public static String getTextByTime(int time) {
        if(time >= FIVE_TEN.minTime && time < FIVE_TEN.maxTime) {
            return FIVE_TEN.timeByText;
        } else if (time >= TEN_TWENTY.minTime && time < TEN_TWENTY.maxTime) {
            return TEN_TWENTY.timeByText;
        } else if (time >= THIRTY_SIXTY.minTime && time < THIRTY_SIXTY.maxTime) {
            return THIRTY_SIXTY.timeByText;
        } else {
            return SIXTY_PLUS.timeByText;
        }
    }

    @Override
    public String toString() {
        return timeByText;
    }
}
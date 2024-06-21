package com.biog.unihiveandroid.model;

public enum Month {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    private final String monthName;
    Month(String monthName) {
        this.monthName = monthName;
    }
    public String getMonthName() {
        return monthName;
    }
}

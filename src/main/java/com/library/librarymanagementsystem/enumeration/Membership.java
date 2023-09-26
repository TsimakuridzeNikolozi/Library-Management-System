package com.library.librarymanagementsystem.enumeration;

public enum Membership {
    SILVER(15), // Can borrow books for 15 days
    GOLD(30), // Can borrow books for 30 days
    DIAMOND(60); // Can borrow books for 60 days

    private final int numDays;

    Membership(int numDays) {
        this.numDays = numDays;
    }

    public int getNumDays() {
        return numDays;
    }
}

package com.canigetafiver.lifemyway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class BudgetTest {

    //  construction 

    @Test
    void constructor_setsAmountAndPeriodAndCategory() {
        Budget b = new Budget(500, Period.WEEKLY, Category.FOOD);
        assertEquals(500,            b.getAmount());
        assertEquals(Period.WEEKLY,  b.getPeriod());
        assertEquals(Category.FOOD,  b.getCategory());
    }

    @Test
    void constructor_initialSpentIsZero_soRemainingEqualsAmount() {
        Budget b = new Budget(300, Period.MONTHLY, Category.RENT);
        assertEquals(300, b.getRemaining(), 0.001);
        assertFalse(b.isExceeded());
    }

    //  calculateRemaining 

    @Test
    void calculateRemaining_reducesRemainingCorrectly() {
        Budget b = new Budget(200, Period.WEEKLY, Category.ENTERTAINMENT);
        b.calculateRemaining(50);
        assertEquals(150, b.getRemaining(), 0.001);
    }

    @Test
    void calculateRemaining_accumulatesAcrossMultipleCalls() {
        Budget b = new Budget(200, Period.WEEKLY, Category.ENTERTAINMENT);
        b.calculateRemaining(60);
        b.calculateRemaining(40);
        assertEquals(100, b.getRemaining(), 0.001);
    }

    @Test
    void isExceeded_falseWhenEqualToAmount() {
        Budget b = new Budget(100, Period.WEEKLY, Category.FOOD);
        b.calculateRemaining(100);
        assertFalse(b.isExceeded());
    }

    @Test
    void isExceeded_trueWhenOverAmount() {
        Budget b = new Budget(100, Period.WEEKLY, Category.FOOD);
        b.calculateRemaining(150);
        assertTrue(b.isExceeded());
    }

    // resetBudget 

    @Test
    void resetBudget_clearsAllValues() {
        Budget b = new Budget(500, Period.MONTHLY, Category.RENT);
        b.calculateRemaining(200);
        b.resetBudget();
        assertEquals(0, b.getAmount(),    0.001);
        assertEquals(0, b.getRemaining(), 0.001);
    }

    //  setters

    @Test
    void setAmount_updatesAmount() {
        Budget b = new Budget(100, Period.WEEKLY, Category.HEALTH);
        b.setAmount(300);
        assertEquals(300, b.getAmount(), 0.001);
    }

    @Test
    void setPeriod_updatesPeriod() {
        Budget b = new Budget(100, Period.WEEKLY, Category.HEALTH);
        b.setPeriod(Period.MONTHLY);
        assertEquals(Period.MONTHLY, b.getPeriod());
    }

    @Test
    void setCategory_updatesCategory() {
        Budget b = new Budget(100, Period.WEEKLY, Category.HEALTH);
        b.setCategory(Category.UTILITIES);
        assertEquals(Category.UTILITIES, b.getCategory());
    }
}

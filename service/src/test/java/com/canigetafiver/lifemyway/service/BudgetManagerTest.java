package com.canigetafiver.lifemyway.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetManagerTest {

    private BudgetManager manager;

    @BeforeEach
    void setUp() {
        manager = new BudgetManager();
    }

    // ── addBudget ────────────────────────────────────────────────────────────

    @Test
    void addBudget_increasesListSize() {
        manager.addBudget(500, Period.WEEKLY, Category.FOOD);
        assertEquals(1, manager.getBudgetList().size());
    }

    @Test
    void addBudget_storesCorrectValues() {
        manager.addBudget(200, Period.MONTHLY, Category.ENTERTAINMENT);
        Budget b = manager.getBudgetList().get(0);
        assertEquals(200, b.getAmount());
        assertEquals(Period.MONTHLY, b.getPeriod());
        assertEquals(Category.ENTERTAINMENT, b.getCategory());
    }

    @Test
    void addBudget_multipleCategories_allStored() {
        manager.addBudget(100, Period.WEEKLY, Category.FOOD);
        manager.addBudget(150, Period.WEEKLY, Category.RENT);
        manager.addBudget(50,  Period.WEEKLY, Category.HEALTH);
        assertEquals(3, manager.getBudgetList().size());
    }

    // ── deleteBudget ─────────────────────────────────────────────────────────

    @Test
    void deleteBudget_removesMatchingCategory() {
        manager.addBudget(100, Period.WEEKLY, Category.FOOD);
        manager.addBudget(200, Period.WEEKLY, Category.RENT);
        manager.deleteBudget(Category.FOOD);
        assertEquals(1, manager.getBudgetList().size());
        assertEquals(Category.RENT, manager.getBudgetList().get(0).getCategory());
    }

    @Test
    void deleteBudget_nonExistentCategory_noError() {
        manager.addBudget(100, Period.WEEKLY, Category.FOOD);
        assertDoesNotThrow(() -> manager.deleteBudget(Category.ENTERTAINMENT));
        assertEquals(1, manager.getBudgetList().size());
    }

    @Test
    void deleteBudget_removesAllMatchingEntries() {
        manager.addBudget(100, Period.WEEKLY, Category.OTHER);
        manager.addBudget(200, Period.WEEKLY, Category.OTHER);
        manager.deleteBudget(Category.OTHER);
        assertTrue(manager.getBudgetList().isEmpty());
    }

    // ── getGeneralBudget ─────────────────────────────────────────────────────

    @Test
    void getGeneralBudget_returnsGeneralBudget() {
        manager.addBudget(1000, Period.MONTHLY, Category.GENERAL);
        manager.addBudget(200,  Period.MONTHLY, Category.FOOD);
        Budget general = manager.getGeneralBudget();
        assertNotNull(general);
        assertEquals(Category.GENERAL, general.getCategory());
        assertEquals(1000, general.getAmount());
    }

    @Test
    void getGeneralBudget_returnsNull_whenNotSet() {
        manager.addBudget(200, Period.WEEKLY, Category.FOOD);
        assertNull(manager.getGeneralBudget());
    }

    // ── getCategoryBudgets ───────────────────────────────────────────────────

    @Test
    void getCategoryBudgets_excludesGeneral() {
        manager.addBudget(1000, Period.MONTHLY, Category.GENERAL);
        manager.addBudget(200,  Period.MONTHLY, Category.FOOD);
        manager.addBudget(150,  Period.MONTHLY, Category.RENT);
        List<Budget> cats = manager.getCategoryBudgets();
        assertEquals(2, cats.size());
        cats.forEach(b -> assertNotEquals(Category.GENERAL, b.getCategory()));
    }

    @Test
    void getCategoryBudgets_emptyWhenOnlyGeneral() {
        manager.addBudget(1000, Period.WEEKLY, Category.GENERAL);
        assertTrue(manager.getCategoryBudgets().isEmpty());
    }

    // ── getTotalAllocated ────────────────────────────────────────────────────

    @Test
    void getTotalAllocated_sumsNonGeneralAmounts() {
        manager.addBudget(1000, Period.MONTHLY, Category.GENERAL);
        manager.addBudget(300,  Period.MONTHLY, Category.FOOD);
        manager.addBudget(200,  Period.MONTHLY, Category.RENT);
        assertEquals(500, manager.getTotalAllocated(), 0.001);
    }

    @Test
    void getTotalAllocated_zeroWhenEmpty() {
        assertEquals(0, manager.getTotalAllocated(), 0.001);
    }

    // ── editBudgetAmount ─────────────────────────────────────────────────────

    @Test
    void editBudgetAmount_updatesCorrectly() {
        manager.addBudget(100, Period.WEEKLY, Category.FOOD);
        Budget original = manager.getBudgetList().get(0);
        manager.editBudgetAmount(original, 250);
        assertEquals(250, manager.getBudgetList().get(0).getAmount(), 0.001);
    }

    @Test
    void editBudgetAmount_preservesPeriodAndCategory() {
        manager.addBudget(100, Period.WEEKLY, Category.FOOD);
        Budget original = manager.getBudgetList().get(0);
        manager.editBudgetAmount(original, 300);
        Budget updated = manager.getBudgetList().get(0);
        assertEquals(Period.WEEKLY,   updated.getPeriod());
        assertEquals(Category.FOOD,   updated.getCategory());
    }
}

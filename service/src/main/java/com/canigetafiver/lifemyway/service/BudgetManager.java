package com.canigetafiver.lifemyway.service;

import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private ArrayList<Budget> budgetList;

    public BudgetManager() {
        budgetList = new ArrayList<>();
    }

    public void addBudget(double amount, Period period, Category category) {
        Budget b = new Budget(amount, period, category);
        budgetList.add(b);
    }

    public void deleteBudget(Category category) {
        budgetList.removeIf(b -> b.getCategory() == category);
    }

    public ArrayList<Budget> getBudgetList() {
        return budgetList;
    }

    /** Returns the GENERAL budget (the top-level total set on BudgetSystem screen). */
    public Budget getGeneralBudget() {
        return budgetList.stream()
                .filter(b -> b.getCategory() == Category.GENERAL)
                .findFirst()
                .orElse(null);
    }

    /** Returns all category budgets (everything except GENERAL). */
    public List<Budget> getCategoryBudgets() {
        List<Budget> result = new ArrayList<>();
        for (Budget b : budgetList) {
            if (b.getCategory() != Category.GENERAL) {
                result.add(b);
            }
        }
        return result;
    }

    /** Total allocated across all non-GENERAL categories. */
    public double getTotalAllocated() {
        return getCategoryBudgets().stream().mapToDouble(Budget::getAmount).sum();
    }

    public void editBudgetAmount(Budget budget, double amount) {
        int index = budgetList.indexOf(budget);
        Budget nb = new Budget(amount, budget.getPeriod(), budget.getCategory());
        budgetList.set(index, nb);
    }

    public void editBudgetCategory(Budget budget, Category category) {
        int index = budgetList.indexOf(budget);
        Budget nb = new Budget(budget.getAmount(), budget.getPeriod(), category);
        budgetList.set(index, nb);
    }

    public void editBudgetPeriod(Budget budget, Period period) {
        int index = budgetList.indexOf(budget);
        Budget nb = new Budget(budget.getAmount(), period, budget.getCategory());
        budgetList.set(index, nb);
    }
}

package com.canigetafiver.lifemyway.web;

import com.canigetafiver.lifemyway.service.BudgetManager;
import com.canigetafiver.lifemyway.service.Period;

/**
 * Holds budget state across screens so the dashboard can read it from Home.
 */
public class BudgetSession {
    public static BudgetManager budgetManager = null;
    public static Period period = null;
    public static double totalBudget = 0.0;
}

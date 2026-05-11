package com.canigetafiver.lifemyway.service;

public class Budget {
    private double amount;
    private double spent;
    private double remaining;
    private Period period;
    private Category category;

    public Budget(double amount, Period period, Category category){
        this.amount = amount; 
        this.spent = 0; 
        this.remaining = amount; 
        this.period = period;
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getAmount() {
        return this.amount;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
   
    public Category getCategory() {
        return this.category;
    }

    public void calculateRemaining(double spending) {
        spent += spending;
        remaining =this.amount - this.spent;
    }


    public double getRemaining() {
        return this.remaining;
    }
 
    public boolean isExceeded() {
        return this.spent > this.amount;
    }

  
    public void resetBudget() {
        this.amount = 0;
        this.spent = 0; 
        this.remaining = 0;
    }
    
}

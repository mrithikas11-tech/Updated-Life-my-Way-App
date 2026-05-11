package com.canigetafiver.lifemyway.web.expense;

import java.time.LocalDate;

public class Expense {
    
    Category category;
    double amount;
    PaymentMethod paymentMethod;
    String description;
    String vendor;
    LocalDate date;

    public Expense(double amount, Category category, PaymentMethod paymentMethod, String description,
                    String vendor, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.date = date;
        this.vendor = vendor;    
        this.date = date;
    }
      
    public Expense() {
        this.amount = 0.0;
        this.category = null;
        this.paymentMethod = null;
        this.description = "No Description";
        this.date = null;
        this.vendor = "";
                   }

    public Expense(ExpenseBuilder expense) {
        this.amount = expense.amount;
        this.category = expense.category;
        this.paymentMethod = expense.paymentMethod;
        this.description = expense.description;
        this.date = expense.date;
        this.vendor = expense.vendor;
    }

     public String getVendor() {
        return vendor;
        
    }
     public double getAmount() {
        return amount;
    }

     public Category getCategory() {
        return category;
    }

     public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

     public String getDescription() {
        return description;
    }

     public LocalDate getDate() {
        return date;
    }

     public static class ExpenseBuilder {
        
        private double amount = 0.0;
        private Category category;
        private PaymentMethod paymentMethod;
        private String description = "";
        private String vendor;
        private LocalDate date = LocalDate.now();
        // Constructors for required fields
        public ExpenseBuilder(double amount, Category category, PaymentMethod paymentMethod) {
            this.amount = amount;
            this.category = category;
            this.paymentMethod = paymentMethod;
        }
        public ExpenseBuilder(double amount, Category category, LocalDate date) {
            this.amount = amount;
            this.category = category;
            this.date = date;
        }
        // Empty constructor for optional fields
        public ExpenseBuilder() {
        }

        public ExpenseBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public ExpenseBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ExpenseBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }
        public ExpenseBuilder description(String description) {
            this.description = description;
            return this;
        }
        public ExpenseBuilder vendor(String vendor) {
            this.vendor = vendor;
            return this;
        }
        public ExpenseBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }
        public Expense build() {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
            else if (category == null) {
                throw new IllegalArgumentException("Category must be provided");
            }
            else if (paymentMethod == null) {
                throw new IllegalArgumentException("Payment method must be provided");
            }
            else if (date == null) {
                throw new IllegalArgumentException("Date must be provided");
            }
            return new Expense(this);
        }
    }

    
}

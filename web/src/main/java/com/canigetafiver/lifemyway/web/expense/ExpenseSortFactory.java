package com.canigetafiver.lifemyway.web.expense;

import java.util.Comparator;

enum SortType {
    AMOUNT,
    CATEGORY,
    DATE
}
enum Direction {
    ASCENDING,
    DESCENDING
}
public class ExpenseSortFactory {
    public static Comparator<Expense> getDynamicComparator(SortType sortType, Direction direction) {
        Comparator<Expense> comparator = switch (sortType) {
            case AMOUNT -> Comparator.comparing(Expense::getAmount);
            case CATEGORY -> Comparator.comparing(Expense::getCategory);
            case DATE -> Comparator.comparing(Expense::getDate);
            default ->throw new IllegalArgumentException("Invalid sort type");
        };
        if (direction == Direction.DESCENDING) {
            comparator = comparator.reversed();
        }
        return comparator;
    }


}

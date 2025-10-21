package org.tedros.redminetools.gateway;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilterCondition {
	
    private String operator;
    private String[] values;
    private FilterType type;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FilterCondition(FilterType type, String operator, String... values) {
        this.type = type;
        this.operator = operator;
        this.values = formatValues(type, values);
    }

    public static FilterCondition auto(FilterType detectedType, String operator, String... values) {
        return new FilterCondition(detectedType, operator, values);
    }

    public static FilterCondition equalsTo(String value) {
        return new FilterCondition(FilterType.TEXT, "=", value);
    }

    public static FilterCondition notEquals(String value) {
        return new FilterCondition(FilterType.TEXT, "!", value);
    }

    public static FilterCondition betweenDates(LocalDate start, LocalDate end) {
        return new FilterCondition(FilterType.DATE, "><", start.toString(), end.toString());
    }

    public static FilterCondition betweenNumbers(String start, String end) {
        return new FilterCondition(FilterType.NUMBER, "><", start, end);
    }

    private static String[] formatValues(FilterType type, String... values) {
        if (values == null) return null;
        String[] formatted = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            switch (type) {
                case DATE:
                    formatted[i] = LocalDate.parse(values[i]).format(DATE_FORMATTER);
                    break;
                default:
                    formatted[i] = values[i];
            }
        }
        return formatted;
    }

    public String getOperator() { return operator; }
    public String[] getValues() { return values; }
    public FilterType getType() { return type; }
}


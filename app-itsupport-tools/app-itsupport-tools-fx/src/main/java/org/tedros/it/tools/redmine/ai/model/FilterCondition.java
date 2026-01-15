package org.tedros.it.tools.redmine.ai.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("Represents a filter condition for Redmine issue filtering.")
public class FilterCondition {

    @TRequiredProperty
    @JsonPropertyDescription("The operator used in the filter condition (e.g., '=', '!=', '><').")
    private String operator;

    @TRequiredProperty
    @JsonPropertyDescription("The values associated with the filter condition.")
    private String[] values;

    @TRequiredProperty
    @JsonPropertyDescription("The type of the filter condition (e.g., TEXT, NUMBER, DATE, BOOLEAN).")
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

    public static FilterCondition to(String operator, Boolean value) {
        return new FilterCondition(FilterType.BOOLEAN, "=", value.toString());
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

    public static FilterCondition greaterEqualThan(String value) {
        return new FilterCondition(FilterType.TEXT, ">=", value);
    }

    public static FilterCondition lessEqualThan(String value) {
        return new FilterCondition(FilterType.TEXT, "<=", value);
    }

    private static String[] formatValues(FilterType type, String... values) {
        if (values == null)
            return null;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

}

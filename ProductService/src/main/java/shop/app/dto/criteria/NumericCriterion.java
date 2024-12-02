package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.math.BigDecimal;

@JsonTypeName("price")
public class NumericCriterion implements SearchCriteria {
    private String field;
    private BigDecimal value;
    private Operation operation;

    @Override
    public String getField() {
        return field;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }
}

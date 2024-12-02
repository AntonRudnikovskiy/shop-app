package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("description")
public class StringCriterion implements SearchCriteria {
    private String field;
    private Long value;
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

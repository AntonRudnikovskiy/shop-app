package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Date;

@JsonTypeName("createdAt")
public class DateCriterion implements SearchCriteria {
    private String field;
    private Date value;
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

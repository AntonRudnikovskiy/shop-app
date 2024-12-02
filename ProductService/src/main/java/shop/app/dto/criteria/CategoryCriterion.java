package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shop.app.entity.CategoryType;


@JsonTypeName("name")
public class CategoryCriterion implements SearchCriteria {
    private String field;
    private CategoryType value;
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

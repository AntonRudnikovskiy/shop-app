package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import shop.app.entity.CategoryType;

@Data
@AllArgsConstructor
@JsonTypeName("name")
public class CategoryCriterion implements SearchCriteria {
    @NotNull(message = "Category field cannot be null")
    private final String field;
    @NotNull(message = "CategoryType value cannot be null")
    private final CategoryType value;
    @NotNull(message = "Operation cannot be null")
    private final Operation operation;

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

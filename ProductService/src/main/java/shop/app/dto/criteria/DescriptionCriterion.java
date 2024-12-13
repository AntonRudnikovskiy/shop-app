package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeName("description")
public class DescriptionCriterion implements SearchCriteria {
    @NotNull(message = "Description field cannot be null")
    private final String field;
    @NotNull(message = "Description value cannot be null")
    private final String value;
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

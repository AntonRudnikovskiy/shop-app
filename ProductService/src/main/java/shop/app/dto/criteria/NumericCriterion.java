package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonTypeName("price")
public class NumericCriterion implements SearchCriteria {
    @NotNull(message = "Price field cannot be null")
    private final String field;
    @NotNull(message = "Price value cannot be null")
    private final BigDecimal value;
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

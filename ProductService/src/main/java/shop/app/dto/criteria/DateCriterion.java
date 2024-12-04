package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
@JsonTypeName("createdAt")
public class DateCriterion implements SearchCriteria {
    @NotNull(message = "Date field cannot be null")
    private final String field;
    @NotNull(message = "Date value cannot be null")
    private final Date value;
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

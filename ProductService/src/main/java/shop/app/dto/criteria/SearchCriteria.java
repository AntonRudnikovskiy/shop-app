package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "field"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CategoryCriterion.class, name = "name"),
        @JsonSubTypes.Type(value = DateCriterion.class, name = "createdAt"),
        @JsonSubTypes.Type(value = NumericCriterion.class, name = "price"),
        @JsonSubTypes.Type(value = DescriptionCriterion.class, name = "description")
})
public interface SearchCriteria {
    /**
     * @return The field name to apply the criterion on
     */
    @NotBlank(message = "Field name cannot be blank")
    String getField();

    /**
     * @return The value to compare against
     */
    @NotNull(message = "Criterion value cannot be null")
    Object getValue();

    /**
     * @return The comparison operation to apply
     */
    @NotNull(message = "Operation cannot be null")
    Operation getOperation();
}

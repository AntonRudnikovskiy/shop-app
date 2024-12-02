package shop.app.dto.criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
        @JsonSubTypes.Type(value = StringCriterion.class, name = "price")
})
public interface SearchCriteria {
    String getField();

    Object getValue();

    Operation getOperation();
}

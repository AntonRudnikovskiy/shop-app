package shop.app.service.criteria;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.app.dto.criteria.SearchCriteria;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;

import static shop.app.dto.criteria.Operation.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchSpecification<T> implements Specification<T> {
    private final Map<String, BiFunction<CriteriaBuilder, Root<?>, PredicateFactory>> operations = new HashMap<>();

    @PostConstruct
    public void initialize() {
        operations.put(LIKE_TEXT.name(), (cb, root) -> (field, value) -> cb.like(root.get(field), "%" + value + "%"));
        operations.put(LIKE_NUMBER.name(), (cb, root) -> (field, value) -> cb.between(root.get(field), (BigDecimal) value, (BigDecimal) value));
        operations.put(LIKE_DATA.name(), (cb, root) -> (field, value) -> cb.between(root.get(field), (Date) value, (Date) value));
        operations.put(EQUAL.name(), (cb, root) -> (field, value) -> cb.equal(root.get(field), value));
        operations.put(GREATER_THAN_OR_EQUAL.name(), (cb, root) -> (field, value) -> cb.greaterThanOrEqualTo(root.get(field), (Comparable) value));
        operations.put(LESS_THAN_OR_EQUAL.name(), (cb, root) -> (field, value) -> cb.lessThanOrEqualTo(root.get(field), (Comparable) value));
    }

    @Override
    public Predicate createQueryBySearchCriteria(Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query,
                                                 List<SearchCriteria> criteriaList) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criterion : criteriaList) {
            try {
                BiFunction<CriteriaBuilder, Root<?>, PredicateFactory> operation = operations
                        .get(criterion.getOperation().name());

                PredicateFactory predicateFactory = operation.apply(criteriaBuilder, root);
                predicates.add(predicateFactory.create(criterion.getField(), criterion.getValue()));
            } catch (Exception e) {
                log.error("{}, unsupported operation for value: {}", criterion.getOperation(), criterion.getValue());
                throw new UnsupportedOperationException(e.getMessage());
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

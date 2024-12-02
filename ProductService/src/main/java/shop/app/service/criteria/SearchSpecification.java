package shop.app.service.criteria;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.app.dto.criteria.SearchCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static shop.app.dto.criteria.Operation.*;

@Component
@RequiredArgsConstructor
public class SearchSpecification<T> implements Specification<T> {
    private final Map<String, BiFunction<CriteriaBuilder, Root<?>, PredicateFactory>> operations = new HashMap<>();

    @PostConstruct
    public void initialize() {
        operations.put(LIKE.name(), (cb, root) -> (field, value) -> cb.like(root.get(field), "%" + value + "%"));
        operations.put(EQUAL.name(), (cb, root) -> (field, value) -> cb.equal(root.get(field), value));
        operations.put(GREATER_THAN_OR_EQUAL.name(), (cb, root) -> (field, value) -> cb.greaterThanOrEqualTo(root.get(field), (Comparable) value));
        operations.put(LESS_THAN_OR_EQUAL.name(), (cb, root) -> (field, value) -> cb.lessThanOrEqualTo(root.get(field), (Comparable) value));
    }

    @Override
    public Predicate createQueryBySearchCriteria(Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query,
                                                 List<SearchCriteria> criteriaList) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criterion : criteriaList) {
            BiFunction<CriteriaBuilder, Root<?>, PredicateFactory> operation = operations
                    .get(criterion.getOperation().name());

            PredicateFactory predicateFactory = operation.apply(criteriaBuilder, root);
            predicates.add(predicateFactory.create(criterion.getField(), criterion.getValue()));
        }
        return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
    }
}

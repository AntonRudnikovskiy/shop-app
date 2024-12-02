package shop.app.service.criteria;

import jakarta.persistence.criteria.Predicate;

@FunctionalInterface
public interface PredicateFactory {
    Predicate create(String field, Object value);
}

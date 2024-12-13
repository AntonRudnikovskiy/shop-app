package shop.app.service.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import shop.app.dto.criteria.SearchCriteria;

import java.util.List;

public interface Specification<T> {
    Predicate createQueryBySearchCriteria(Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query,
                          List<SearchCriteria> criteriaList);
}

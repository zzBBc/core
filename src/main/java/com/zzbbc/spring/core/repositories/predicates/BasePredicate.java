package com.zzbbc.spring.core.repositories.predicates;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.http.HttpStatus;
import com.zzbbc.spring.core.exceptions.BusinessException;
import com.zzbbc.spring.core.models.BaseModel;
import com.zzbbc.spring.core.models.BaseResponse;
import com.zzbbc.spring.core.utils.DateUtils;
import com.zzbbc.spring.core.validators.impl.CommonValidator;

public class BasePredicate<M extends BaseModel<?>> {
    private final Class<M> modelClass;
    private final Root<M> root;
    private final CriteriaBuilder criteriaBuilder;
    private List<SearchCriteria> criterias = new ArrayList<>();

    public BasePredicate(Class<M> modelClass, Root<M> root, CriteriaBuilder criteriaBuilder) {
        this.modelClass = modelClass;
        this.root = root;
        this.criteriaBuilder = criteriaBuilder;
    }

    private Predicate getPredicate(SearchCriteria searchCriteria) {
        String key = searchCriteria.getKey();
        String value = searchCriteria.getValue();
        String operator = searchCriteria.getOperator();

        boolean isMultiValue = value.contains(",");

        Class<?> propertyType = null;
        try {
            propertyType = getPropertyType(searchCriteria.getKey());
        } catch (NoSuchFieldException | SecurityException e) {
            throw new BusinessException(new BaseResponse(HttpStatus.BAD_REQUEST));
        }

        switch (propertyType.getSimpleName()) {
            case "Integer":
                return getIntegerPredicate(key, operator, value, isMultiValue);
            case "Long":
                return getLongPredicate(key, operator, value, isMultiValue);
            case "Double":
                return getDoublePredicate(key, operator, value, isMultiValue);
            case "String":
                return getStringPredicate(key, operator, value, isMultiValue);
            case "Date":
                return getDatePredicate(key, operator, value);
            case "LocalDateTime":
                return getLocalDateTimePredicate(key, operator, value);
            default:
                throw new BusinessException(new BaseResponse(HttpStatus.BAD_REQUEST));
        }
    }

    private Predicate getLocalDateTimePredicate(String key, String operator, String valueString) {
        Path<LocalDateTime> path = root.get(key);

        LocalDateTime value = CommonValidator.validateNull(DateUtils.toLocalDateTime(valueString),
                key + " không hợp lệ!");

        switch (operator) {
            case "=":
                return criteriaBuilder.equal(path, value);
            case ">=":
                return criteriaBuilder.greaterThanOrEqualTo(path, value);
            case "<=":
                return criteriaBuilder.lessThanOrEqualTo(path, value);
            default:
                return null;
        }
    }

    private Predicate getDatePredicate(String key, String operator, String valueString) {
        Path<Date> path = root.get(key);

        Date value =
                CommonValidator.validateNull(DateUtils.toDate(valueString), key + " không hợp lệ!");
        switch (operator) {
            case "=":
                return criteriaBuilder.equal(path, value);
            case ">=":
                return criteriaBuilder.greaterThanOrEqualTo(path, value);
            case "<=":
                return criteriaBuilder.lessThanOrEqualTo(path, value);
            default:
                return null;
        }
    }

    private Predicate getStringPredicate(String key, String operator, String valueString,
            boolean isMultiValue) {
        Path<String> path = root.get(key);

        if (isMultiValue) {
            String[] value = valueString.split(",");
            switch (operator) {
                case "=":
                    return criteriaBuilder.equal(path, value);
                case "!=":
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        } else {
            String value = CommonValidator.validateNull(valueString, key + " không hợp lệ!");
            switch (operator) {
                case "=":
                    return criteriaBuilder.equal(path, value);
                case "!=":
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Predicate getDoublePredicate(String key, String operator, String numberString,
            boolean isMultiValue) {
        Path<Double> path = root.get(key);

        if (isMultiValue) {
            Object[] value = Stream.of(numberString.split(",")).toArray();
            switch (operator) {
                case ":":
                    return criteriaBuilder.in(path).in(value);
                case "!=":
                    return criteriaBuilder.in(path).not().in(value);
                default:
                    return null;
            }
        } else {
            Double value = CommonValidator.validateNull(Double.parseDouble(numberString),
                    key + " không hợp lệ!");
            switch (operator) {
                case "=":
                    return criteriaBuilder.equal(path, value);
                case ">":
                    return criteriaBuilder.greaterThan(path, value);
                case "<":
                    return criteriaBuilder.lessThan(path, value);
                case ">=":
                    return criteriaBuilder.greaterThanOrEqualTo(path, value);
                case "<=":
                    return criteriaBuilder.lessThanOrEqualTo(path, value);
                case "!=":
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Predicate getIntegerPredicate(String key, String operator, String numberString,
            boolean isMultiValue) {
        Path<Integer> path = root.get(key);

        if (isMultiValue) {
            Object[] value = Stream.of(numberString.split(",")).toArray();
            switch (operator) {
                case "=":
                    return criteriaBuilder.in(path).in(value);
                case "!=":
                    return criteriaBuilder.in(path).not().in(value);
                default:
                    return null;
            }
        } else {
            Integer value = CommonValidator.validateNull(Integer.parseInt(numberString),
                    key + " không hợp lệ!");
            switch (operator) {
                case "=":
                    return criteriaBuilder.equal(path, value);
                case ">":
                    return criteriaBuilder.greaterThan(path, value);
                case "<":
                    return criteriaBuilder.lessThan(path, value);
                case ">=":
                    return criteriaBuilder.greaterThanOrEqualTo(path, value);
                case "<=":
                    return criteriaBuilder.lessThanOrEqualTo(path, value);
                case "!=":
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Predicate getLongPredicate(String key, String operator, String numberString,
            boolean isMultiValue) {
        Path<Long> path = root.get(key);

        if (isMultiValue) {
            Object[] value = Stream.of(numberString.split(",")).toArray();
            switch (operator) {
                case "=":
                    return criteriaBuilder.in(path).in(value);
                case "!=":
                    return criteriaBuilder.in(path).not().in(value);
                default:
                    return null;
            }
        } else {
            Long value = CommonValidator.validateNull(Long.parseLong(numberString),
                    key + " không hợp lệ!");
            switch (operator) {
                case "=":
                    return criteriaBuilder.equal(path, value);
                case ">":
                    return criteriaBuilder.greaterThan(path, value);
                case "<":
                    return criteriaBuilder.lessThan(path, value);
                case ">=":
                    return criteriaBuilder.greaterThanOrEqualTo(path, value);
                case "<=":
                    return criteriaBuilder.lessThanOrEqualTo(path, value);
                case "!=":
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Class<?> getPropertyType(String key) throws NoSuchFieldException, SecurityException {
        Field field = modelClass.getDeclaredField(key);

        return field.getType();
    }

    public Predicate[] toPredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria searchCriteria : criterias) {
            predicates.add(this.getPredicate(searchCriteria));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    public void setSearchCriterias(List<SearchCriteria> filters) {
        this.criterias = filters;
    }
}

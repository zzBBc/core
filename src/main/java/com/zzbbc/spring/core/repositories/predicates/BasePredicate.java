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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import com.zzbbc.spring.core.enums.FilterOperator;
import com.zzbbc.spring.core.enums.PropertyEnum;
import com.zzbbc.spring.core.exceptions.BusinessException;
import com.zzbbc.spring.core.models.BaseResponse;
import com.zzbbc.spring.core.models.Model;
import com.zzbbc.spring.core.utils.DateUtils;
import com.zzbbc.spring.core.validators.impl.CommonValidator;

public class BasePredicate<M extends Model<?>> {
    private static final Logger LOGGER = LogManager.getLogger(BasePredicate.class);

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
        FilterOperator operator = searchCriteria.getOperator();

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
                if (propertyType.isEnum()) {
                    return getEnumPredicate(key, operator, value, propertyType);
                } else {
                    LOGGER.error("Property type not found key=[{}] type=[{}]", key, propertyType);
                    throw new BusinessException(new BaseResponse(HttpStatus.BAD_REQUEST));
                }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends PropertyEnum<?>> Predicate getEnumPredicate(String key,
            FilterOperator operator, String value, Class<?> propertyType) {
        try {
            T[] objects = (T[]) propertyType.getEnumConstants();

            for (T enumValue : objects) {
                if (enumValue.name().equals(value)) {
                    return getEnumPredicate(key, operator, enumValue);
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    private <T extends PropertyEnum<?>> Predicate getEnumPredicate(String key,
            FilterOperator operator, T enumValue) {
        Path<T> path = root.get(key);

        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(path, enumValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(path, enumValue);
            default:
                return null;
        }
    }

    private Predicate getLocalDateTimePredicate(String key, FilterOperator operator,
            String valueString) {
        Path<LocalDateTime> path = root.get(key);

        LocalDateTime value = CommonValidator.validateNull(DateUtils.toLocalDateTime(valueString),
                key + " không hợp lệ!");

        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(path, value);
            case GREATER_EQUAL:
                return criteriaBuilder.greaterThanOrEqualTo(path, value);
            case LESS_EQUAL:
                return criteriaBuilder.lessThanOrEqualTo(path, value);
            default:
                return null;
        }
    }

    private Predicate getDatePredicate(String key, FilterOperator operator, String valueString) {
        Path<Date> path = root.get(key);

        Date value =
                CommonValidator.validateNull(DateUtils.toDate(valueString), key + " không hợp lệ!");
        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(path, value);
            case GREATER_EQUAL:
                return criteriaBuilder.greaterThanOrEqualTo(path, value);
            case LESS_EQUAL:
                return criteriaBuilder.lessThanOrEqualTo(path, value);
            default:
                return null;
        }
    }

    private Predicate getStringPredicate(String key, FilterOperator operator, String valueString,
            boolean isMultiValue) {
        Path<String> path = root.get(key);

        if (isMultiValue) {
            String[] value = valueString.split(",");
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.equal(path, value);
                case NOT_EQUALS:
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        } else {
            String value = CommonValidator.validateNull(valueString, key + " không hợp lệ!");
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.equal(path, value);
                case NOT_EQUALS:
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Predicate getDoublePredicate(String key, FilterOperator operator, String numberString,
            boolean isMultiValue) {
        Path<Double> path = root.get(key);

        if (isMultiValue) {
            Object[] value = Stream.of(numberString.split(",")).toArray();
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.in(path).in(value);
                case NOT_EQUALS:
                    return criteriaBuilder.in(path).not().in(value);
                default:
                    return null;
            }
        } else {
            Double value = CommonValidator.validateNull(Double.parseDouble(numberString),
                    key + " không hợp lệ!");
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.equal(path, value);
                case GREATER:
                    return criteriaBuilder.greaterThan(path, value);
                case LESS:
                    return criteriaBuilder.lessThan(path, value);
                case GREATER_EQUAL:
                    return criteriaBuilder.greaterThanOrEqualTo(path, value);
                case LESS_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(path, value);
                case NOT_EQUALS:
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Predicate getIntegerPredicate(String key, FilterOperator operator, String numberString,
            boolean isMultiValue) {
        Path<Integer> path = root.get(key);

        if (isMultiValue) {
            Object[] value = Stream.of(numberString.split(",")).toArray();
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.in(path).in(value);
                case NOT_EQUALS:
                    return criteriaBuilder.in(path).not().in(value);
                default:
                    return null;
            }
        } else {
            Integer value = CommonValidator.validateNull(Integer.parseInt(numberString),
                    key + " không hợp lệ!");
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.equal(path, value);
                case GREATER:
                    return criteriaBuilder.greaterThan(path, value);
                case LESS:
                    return criteriaBuilder.lessThan(path, value);
                case GREATER_EQUAL:
                    return criteriaBuilder.greaterThanOrEqualTo(path, value);
                case LESS_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(path, value);
                case NOT_EQUALS:
                    return criteriaBuilder.notEqual(path, value);
                default:
                    return null;
            }
        }
    }

    private Predicate getLongPredicate(String key, FilterOperator operator, String numberString,
            boolean isMultiValue) {
        Path<Long> path = root.get(key);

        if (isMultiValue) {
            Object[] value = Stream.of(numberString.split(",")).toArray();
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.in(path).in(value);
                case NOT_EQUALS:
                    return criteriaBuilder.in(path).not().in(value);
                default:
                    return null;
            }
        } else {
            Long value = CommonValidator.validateNull(Long.parseLong(numberString),
                    key + " không hợp lệ!");
            switch (operator) {
                case EQUALS:
                    return criteriaBuilder.equal(path, value);
                case GREATER:
                    return criteriaBuilder.greaterThan(path, value);
                case LESS:
                    return criteriaBuilder.lessThan(path, value);
                case GREATER_EQUAL:
                    return criteriaBuilder.greaterThanOrEqualTo(path, value);
                case LESS_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(path, value);
                case NOT_EQUALS:
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

package com.zzbbc.spring.core.validators.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.zzbbc.spring.core.exceptions.BusinessException;
import com.zzbbc.spring.core.models.BaseResponse;
import com.zzbbc.spring.core.repositories.predicates.SearchCriteria;
import com.zzbbc.spring.core.validators.BaseValidator;
import com.zzbbc.spring.core.validators.Validator;

public class CommonValidator extends BaseValidator {

    public static <T> T validateNull(T object, String failedMessage) {
        return Validator.data(object).validate(Objects::isNull,
                () -> new BusinessException(new BaseResponse(-1, failedMessage))).getData();
    }

    public static <T> T validateNull(T object) {
        return validateNull(object, "Thông tin không hợp lệ");
    }

    public static List<SearchCriteria> validateSearchCriteria(Map<String, String> params) {
        List<SearchCriteria> criterias = new ArrayList<>();
        if (Objects.nonNull(params)) {
            Collection<SearchCriteria> collect = params.entrySet().parallelStream()
                    .map(CommonValidator::validateFilterPattern).collect(Collectors.toList());
            criterias.addAll(collect);
        }
        return criterias;
    }

    private static SearchCriteria validateFilterPattern(Map.Entry<String, String> entry) {
        final Pattern pattern =
                Pattern.compile("(<|>|<=|>=|%|-|\\(\\))([\\w\\s\\(\\),.:-]+?)\\|");
        Matcher m = pattern.matcher(entry.getValue() + "|");
        if (m.find()) {
            return new SearchCriteria(entry.getKey(), m.group(1), m.group(2));
        } else {
            return new SearchCriteria(entry.getKey(), "=", entry.getValue());
        }
    }
}

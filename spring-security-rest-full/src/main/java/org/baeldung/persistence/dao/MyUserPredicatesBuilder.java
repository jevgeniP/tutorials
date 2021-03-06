package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.web.util.SearchCriteria;

import com.mysema.query.types.expr.BooleanExpression;

public class MyUserPredicatesBuilder {
    private List<SearchCriteria> params;

    public MyUserPredicatesBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public MyUserPredicatesBuilder with(final String key, final String operation, final Object value) {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder();
        final List<SearchCriteria> newParams = new ArrayList<SearchCriteria>(params);
        newParams.add(new SearchCriteria(key, operation, value));
        builder.params = newParams;
        return builder;
    }

    public BooleanExpression build() {
        if (params.size() == 0)
            return null;

        final List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
        MyUserPredicate predicate;

        for (final SearchCriteria param : params) {
            predicate = new MyUserPredicate(param);
            final BooleanExpression exp = predicate.getPredicate();
            if (exp != null) {
                predicates.add(exp);
            }
        }

        if (predicates.size() == 0)
            return null;

        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;

    }
}

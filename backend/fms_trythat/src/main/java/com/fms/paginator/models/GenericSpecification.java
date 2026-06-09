package com.fms.paginator.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import com.fms.app.utils.CommonUtil;


public class GenericSpecification<T> {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger logger = LogManager.getLogger(GenericSpecification.class);
    public Specification<T> buildSpecification(FilterCriteria filterCriteria) {
        Specification<T> spec = Specification.where(null);

        if (filterCriteria.getValue() != null) {
            switch (filterCriteria.getMatchMode().toLowerCase()) {
                case "startswith":
                    spec = startsWith(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "contains":
                    spec = contains(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "notcontains":
                    spec = notContains(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "endswith":
                    spec = endsWith(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "equals":
                    spec = equalsTo(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "notequals":
                    spec = notEqualsTo(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "lt":
                    spec = lessThan(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "lte":
                    spec = lessThanOrEqualTo(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "gt":
                    spec = greaterThan(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "gte":
                    spec = greaterThanOrEqualTo(filterCriteria.getFieldName(), filterCriteria.getValue());
                    break;
                case "dateis":
                    spec = dateIs(filterCriteria.getFieldName(), (String) filterCriteria.getValue());
                    break;
                case "dateisnot":
                    spec = dateIsNot(filterCriteria.getFieldName(), (String) filterCriteria.getValue());
                    break;
                case "dateisbefore":
                    spec = dateIsBefore(filterCriteria.getFieldName(), (String) filterCriteria.getValue());
                    break;
                case "dateisafter":
                    spec = dateIsAfter(filterCriteria.getFieldName(), (String) filterCriteria.getValue());
                    break;
                default:
                    break;
            }
        }

        return spec;
    }

    public Specification<T> startsWith(String fieldName, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(getNestedPath(root, fieldName), value + "%");
    }

    public Specification<T> contains(String fieldName, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(getNestedPath(root, fieldName), "%" + value + "%");
    }

    public Specification<T> notContains(String fieldName, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notLike(getNestedPath(root, fieldName), "%" + value + "%");
    }

    public Specification<T> endsWith(String fieldName, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(getNestedPath(root, fieldName), "%" + value);
    }

    public Specification<T> equalsTo(String fieldName, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(getNestedPath(root, fieldName), value);
    }

    public Specification<T> notEqualsTo(String fieldName, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(getNestedPath(root, fieldName), value);
    }
    
    public <Y extends Comparable<? super Y>> Specification<T> lessThan(String fieldName, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(getNestedPath(root, fieldName), value);
    }

    public <Y extends Comparable<? super Y>> Specification<T> lessThanOrEqualTo(String fieldName, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(getNestedPath(root, fieldName), value);
    }

    public <Y extends Comparable<? super Y>> Specification<T> greaterThan(String fieldName, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(getNestedPath(root, fieldName), value);
    }

    public <Y extends Comparable<? super Y>> Specification<T> greaterThanOrEqualTo(String fieldName, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(getNestedPath(root, fieldName), value);
    }
    
    public Specification<T> dateIs(String fieldName, String dateString) {
        return (root, query, criteriaBuilder) -> {
            try {
                Date date = DATE_FORMAT.parse(dateString);
                return criteriaBuilder.equal(getNestedPath(root, fieldName), date);
            } catch (Exception e) {
            	logException(e);
                return null;
            }
        };
    }

    public Specification<T> dateIsNot(String fieldName, String dateString) {
        return (root, query, criteriaBuilder) -> {
            try {
                Date date = DATE_FORMAT.parse(dateString);
                return criteriaBuilder.notEqual(getNestedPath(root, fieldName), date);
            } catch (Exception e) {
            	logException(e);
                return null;
            }
        };
    }

    public Specification<T> dateIsBefore(String fieldName, String dateString) {
        return (root, query, criteriaBuilder) -> {
            try {
                Date date = DATE_FORMAT.parse(dateString);
                return criteriaBuilder.lessThan(getNestedPath(root, fieldName), date);
            } catch (Exception e) {
            	logException(e);
                return null;
            }
        };
    }

    public Specification<T> dateIsAfter(String fieldName, String dateString) {
        return (root, query, criteriaBuilder) -> {
            try {
                Date date = DATE_FORMAT.parse(dateString);
                return criteriaBuilder.greaterThan(getNestedPath(root, fieldName), date);
            } catch (Exception e) {
            	logException(e);
                return null;
            }
        };
    }

    
    private <R> Path<R> getNestedPath(Path<?> root, String fieldName) {
        String[] properties = fieldName.split("\\.");

        Path<R> path = root.get(properties[0]);

        for (int i = 1; i < properties.length; i++) {
            path = path.get(properties[i]);
        }

        return path;
    }
    
    public Specification<T> buildSpecificationMultiple(List<FilterCriteria> filterCriteria){
    	 Specification<T> spec = Specification.where(null);
    	 for(FilterCriteria filter:filterCriteria) {
    		 spec = spec.and(buildSpecification(filter));
    	 }
    	 return spec;
    }
    
    public void logException(Exception e) {
		String stacktrace = CommonUtil.getStakeTrace(e);
		logger.error("Exception in GenericSpecification.buildSpecification: "+stacktrace,e);
    }
}

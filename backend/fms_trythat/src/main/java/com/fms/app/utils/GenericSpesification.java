package com.fms.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.fms.app.dashboard.services.SearchCriteria;

public class GenericSpesification<T> implements Specification<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SearchCriteria> list;

	public GenericSpesification() {
		list = new ArrayList<SearchCriteria>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		// create a new predicate list
		List<Predicate> predicates = new ArrayList<>();

		// add add criteria to predicates
		for (SearchCriteria criteria : list) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				Predicate lestThanPredicte;
				if (criteria.getKey().startsWith("date")) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date localDate;
					try {
						localDate = simpleDateFormat.parse(criteria.getValue().toString());
					} catch (ParseException e) {
						localDate = new Date();
					}
					lestThanPredicte = builder.greaterThanOrEqualTo(root.get(criteria.getKey()), localDate);
				} else {
					lestThanPredicte = builder.greaterThanOrEqualTo(root.get(criteria.getKey()),
							criteria.getValue().toString());
				}
				predicates
						.add(lestThanPredicte);
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				Predicate onStart;
				if (criteria.getKey().startsWith("date")) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date localDate;
					try {
						localDate = simpleDateFormat.parse(criteria.getValue().toString());
					} catch (ParseException e) {
						localDate = new Date();
					}
					onStart = builder.lessThanOrEqualTo(root.get(criteria.getKey()),
							localDate);
				} else {
					onStart = builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
				}

				predicates.add(onStart);
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
			}
			else if (criteria.getOperation().equals(SearchOperation.IN)) {
				predicates.add(root.get(criteria.getKey()).in(criteria.getValue()));
			}
//			else if (criteria.getOperation().equals(SearchOperation.EXISTS)) {
//				predicates.add(builder.exists(getSubQueryByOrder(root, query, builder)));
//			}
//			else if (criteria.getOperation().equals(SearchOperation.NOT_EXISTS)) {
//				if (criteria.getValue().toString().equals("order"))
//					predicates.add(builder.not(builder.exists(getSubQueryByOrder(root, query, builder))));
//			}
		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}

//	private Subquery<?> getSubQueryByOrder(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//		Subquery<AccountPSRPayments> childSubquery = query.subquery(AccountPSRPayments.class);
//		Root<AccountPSRPayments> child = childSubquery.from(AccountPSRPayments.class);
//
//		Path<Object> childAttributePath = child.get("orderNum");
//		Path<Object> attributePath = root.get("acPoId");
//		childSubquery.select(child).where(builder.equal(childAttributePath, attributePath));
//
//		return childSubquery;
//
//	}

}

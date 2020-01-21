package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_all = "select p from BrandPojo p";
	private static String brandCat = "select p from BrandPojo p where brand =:brand and category =:category";
	private static String Cat = "select p from BrandPojo p where category =:category";

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public BrandPojo insert(BrandPojo p) {
		em.persist(p);
		return p;
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public BrandPojo selectBrandCategory(String brand , String category ){
		TypedQuery<BrandPojo> query = getQuery(brandCat, BrandPojo.class);
		query.setParameter("brand",brand);
		query.setParameter("category" , category);
		return getSingle(query);
	}

	public BrandPojo selectCat(String category ){
		TypedQuery<BrandPojo> query = getQuery(Cat, BrandPojo.class);
		query.setParameter("category" , category);
		return getSingle(query);
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
		return query.getResultList();
	}

	public void update(BrandPojo p) {
	}


}

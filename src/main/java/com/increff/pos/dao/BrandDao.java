package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao {

    private static String select_id = "select p from BrandPojo p where id=:id";
    private static String select_all = "select p from BrandPojo p";
    private static String brandCat = "select p from BrandPojo p where brand =:brand and category =:category";
    private static String Cat = "select p from BrandPojo p where category =:category";

    @Transactional
    public BrandPojo insert(BrandPojo p) {
        em.persist(p);
        em.flush();
        return p;
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public BrandPojo selectBrandCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(brandCat, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }

    public BrandPojo selectCat(String category) {
        TypedQuery<BrandPojo> query = getQuery(Cat, BrandPojo.class);
        query.setParameter("category", category);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public void update(BrandPojo p) {
    }


}

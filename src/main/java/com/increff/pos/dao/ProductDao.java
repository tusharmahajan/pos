package com.increff.pos.dao;

import com.increff.pos.pojo.ProductDisplayPojo;
import com.increff.pos.pojo.ProductPojo;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao {

    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select new com.increff.pos.pojo.ProductDisplayPojo(p.id , p.name , p.barcode," +
            "b.brand, b.category,b.id,p.mrp) from ProductPojo p ,BrandPojo b where p.brand_category_id = b.id";
    private static String select_bar = "select p from ProductPojo p where barcode=:barcode";


    @Transactional
    public ProductPojo insert(ProductPojo p) {
        em.persist(p);
        em.flush();
        return p;
    }

//    public int delete(int id) {
//        Query query = em.createQuery(delete_id);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }

    public ProductPojo selectBar(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(select_bar, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductDisplayPojo> selectAll() {
        Query<ProductDisplayPojo> query = (Query<ProductDisplayPojo>) em.createQuery(select_all);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
    }

}

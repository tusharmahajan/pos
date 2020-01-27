package com.increff.pos.dao;


import com.increff.pos.pojo.InventoryDisplayPojo;
import com.increff.pos.pojo.InventoryReportPojo;
import com.increff.pos.pojo.InventoryPojo;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao {
    private static String select_id = "select i from InventoryPojo i where id=:id";

    private static String select_all_1 = "select new com.increff.pos.pojo.InventoryDisplayPojo(p.id , p.name , p.barcode , i.quantity )from InventoryPojo i, " +
            "ProductPojo p where i.inventoryId = p.id";

    @Transactional
    public InventoryPojo insert(InventoryPojo i) {
        em.persist(i);
        em.flush();
        return i;
    }


    public InventoryPojo select(int id) {
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
//
//    public List<InventoryPojo> selectAll() {
//        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
//        return query.getResultList();
//    }

    public List<InventoryDisplayPojo> selectAll() {
        Query<InventoryDisplayPojo> query = (Query<InventoryDisplayPojo>)em.createQuery(select_all_1);
        return query.getResultList();
    }

    public List<InventoryReportPojo> selectByBrandCat(){
        Query query = (Query) em.createQuery("select new com.increff.pos.pojo.InventoryReportPojo(b.brand , b.category ," +
                "sum(i.quantity)) from " + "InventoryPojo i , ProductPojo p , BrandPojo b" +
                " where p.id = i.inventoryId and p.brand_category_id=b.id group by p.brand_category_id ");

        return query.getResultList();
    }
    public void update(InventoryPojo p) {
    }
}

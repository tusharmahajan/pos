package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {

    //    private static String select = "select o from OrderItemPojo o where productId =:productId";
    //    private static String select_all = "select o from OrderItemPojo o";
    private static String select_o = "select p from OrderItemPojo p where order_id =:order_id";
    private static String selectByBrandCat = "select b.category, sum(oi.quantity), sum((oi.quantity)*(oi.sellingPrice)) " +
            "from BrandPojo b, OrderItemPojo oi,OrderPojo o, ProductPojo p where o.time between :startDate and " +
            ":endDate and o.orderId = oi.order_Id and oi.product_id = p.product_id and p.brand_category = b.id ";
    Query query;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo order) {
        em.persist(order);
    }

    public List<OrderItemPojo> selectAll(int order_id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_o, OrderItemPojo.class);
        query.setParameter("order_id", order_id);
        return query.getResultList();
    }

    public List<Object[]> selectByOrderIdDate(String brand, String category, Date startDate, Date endDate) {

        String q = selectByBrandCat;

        q = q + makequery(brand, category);
        System.out.println();
        System.out.println(q);
        query = em.createNativeQuery(q);
        if(!brand.isEmpty()) query.setParameter("brand" , brand) ;
        if(!category.isEmpty()) query.setParameter("category" , category) ;

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();

    }

    private String makequery(String brand, String category) {

        if (brand.isEmpty() && category.isEmpty()) {
            return "group by b.category";
        }
        if (!brand.isEmpty() && category.isEmpty()) {
            return "group by b.brand ,b.category having b.brand =:brand";
        }
        if (brand.isEmpty() && !category.isEmpty()) {
            return "group by b.category having b.category =:category";
        }
        return "group by b.brand, b.category having b.brand =:brand and b.category =:category";


    }
}
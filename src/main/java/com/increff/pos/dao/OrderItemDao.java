package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.SalesReportResult;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {

    //    private static String select = "select o from OrderItemPojo o where productId =:productId";
    private static String select_by_order_id = "select o from OrderItemPojo o where orderId =: orderId";
    private static String select_o = "select p from OrderItemPojo p where orderId =: orderId";
    private static String selectByBrandCat = "select new com.increff.pos.pojo.SalesReportResult(b.category, sum(oi.quantity), sum((oi.quantity)*(oi.sellingPrice))) " +
            "from BrandPojo b, OrderItemPojo oi,OrderPojo o, ProductPojo p where o.time between :startDate and " +
            ":endDate and o.orderId = oi.orderId and oi.productId = p.id and p.brand_category_id = b.id ";


    @Transactional
    public void insert(OrderItemPojo order) {
        em.persist(order);
    }

    @Transactional(readOnly = true)
    public List<OrderItemPojo> selectAll(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(select_o, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<OrderItemPojo>selectByOrderId(int orderId){
        TypedQuery<OrderItemPojo> query = getQuery(select_by_order_id, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<SalesReportResult> selectByOrderIdDate(String brand, String category, Date startDate, Date endDate) {

        String q = selectByBrandCat;

        q = q + makequery(brand, category);
//        System.out.println();
//        System.out.println(q);
        Query query;
        query = em.createQuery(q);
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
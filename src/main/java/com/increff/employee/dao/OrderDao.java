package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao {

    private static String select_order_id = "select p from OrderPojo p where time between :startDate and :endDate";
    private static String select_all = "select p from OrderPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public OrderPojo insert(OrderPojo order) {
        em.persist(order);
        em.flush();
        return order;
    }

    public List<OrderPojo> select(Date startDate, Date endDate) {
        TypedQuery<OrderPojo> query = getQuery(select_order_id, OrderPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }
}
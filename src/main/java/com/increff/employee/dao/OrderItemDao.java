package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {

//    private static String select = "select o from OrderItemPojo o where productId =:productId";
//    private static String select_all = "select o from OrderItemPojo o";
    private static String select_o = "select p from OrderItemPojo p where order_id =:order_id";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo order) {
        em.persist(order);
    }


    public List<OrderItemPojo> selectall(int order_id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_o, OrderItemPojo.class);
        query.setParameter("order_id", order_id);
        return query.getResultList();
    }

}
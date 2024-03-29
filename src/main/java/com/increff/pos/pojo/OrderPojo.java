package com.increff.pos.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = @Index(columnList = "orderId ,time") )

public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date time;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

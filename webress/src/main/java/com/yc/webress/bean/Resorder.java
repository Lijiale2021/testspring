package com.yc.webress.bean;

import lombok.Data;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Data
@Entity
public class Resorder implements Serializable {
    private static final long serialVersionUID = -8216050519124101961L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roid;
    private Integer userid;
    private String address;
    private String tel;
    private Timestamp ordertime;
    private Timestamp deliverytime;
    private String ps;
    private Integer status;

    @Transient
    private String deliverytimeString;

    public Timestamp getDeliverTime() {
        Date d = null;
        if (deliverytimeString != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                d = df.parse(deliverytimeString);
                deliverytime = new Timestamp(d.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            d = new Date();
            deliverytime = new Timestamp(d.getTime());
        }
        return deliverytime;
    }
}

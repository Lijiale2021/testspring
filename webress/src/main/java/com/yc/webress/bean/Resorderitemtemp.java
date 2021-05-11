package com.yc.webress.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Component
@Data
@Entity
public class Resorderitemtemp implements Serializable {
    private static final long serialVersionUID = -2994355703971154525L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roitid;
    private Integer fid;
    private Integer num;
    private String quittime;
    private Integer userid;

}

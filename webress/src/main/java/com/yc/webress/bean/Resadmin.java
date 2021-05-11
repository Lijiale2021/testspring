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
public class Resadmin implements Serializable {
    private static final long serialVersionUID = -8907584968950131243L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer raid;
    private String raname;
    private String rapwd;

}

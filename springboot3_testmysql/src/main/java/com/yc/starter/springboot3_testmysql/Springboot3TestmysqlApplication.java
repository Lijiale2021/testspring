package com.yc.starter.springboot3_testmysql;


import com.yc.stater.mysql_connectionspringbootstater.IDBHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.sql.Connection;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(YcProperties.class)
public class Springboot3TestmysqlApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3TestmysqlApplication.class, args);
    }

    private static Log log= LogFactory.getLog(Springboot3TestmysqlApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(  Springboot3TestmysqlApplication.class );
    }

    @Autowired
    private Environment env;

    @Value("${yc.tname}")
    private String tname;

    @Autowired
    private YcProperties yc;

    @Resource
    private IDBHelper idbHelper;

    @GetMapping("/con")
    public String testCon(){
       Connection con=idbHelper.getConnection();
       String constr=con.toString();
       log.debug("******debug**********");
       log.info("******info************");
       log.error("***********error*********");
       log.fatal("******fatal*****");
       log.info("环境变量:"+env.getProperty("user.home"));
       log.info(""+tname);

       log.info("yc:"+yc.getTname()+yc.getAge());

       log.info("env:"+env.getProperty("yc.tname")+env.getProperty("yc.age"));
       return constr;
    }
}

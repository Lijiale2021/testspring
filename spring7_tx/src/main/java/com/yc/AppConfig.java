package com.yc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Component
@ComponentScan(basePackages = "com.yc")
@EnableTransactionManagement//启用事务管理器
public class AppConfig {

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        //创建一个数据元
        DataSource ds=new ComboPooledDataSource();//这是连接池
        ((ComboPooledDataSource)ds).setDriverClass("com.mysql.cj.jdbc.Driver");
        ((ComboPooledDataSource)ds).setJdbcUrl("jdbc:mysql://127.0.0.1:3306/testBank?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC");
        ((ComboPooledDataSource)ds).setUser("root");
        ((ComboPooledDataSource)ds).setPassword("a");
        return ds;
    }

    @Bean //@Bean优先级是最高的在IOC注解中
    public TransactionManager DataSourceTransactionManager(DataSource ds){
        //创建事务管理器
        return new DataSourceTransactionManager(ds);
    }
}

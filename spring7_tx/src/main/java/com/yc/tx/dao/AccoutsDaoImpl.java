package com.yc.tx.dao;

import com.yc.tx.bean.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccoutsDaoImpl implements AccoutsDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    //在dao中要使用 jdbcTemplate的模板对象，这个对象要通过 datasource创建
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate =new JdbcTemplate(dataSource) ;
    }

    @Override
    public int saveAccount(Accounts account) {
        String sql="insert into accounts(balance) values(?)";

        //利用KeyHolder来获取新增的这条数据id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //方案一:匿名内部类写法
        /*jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pstm=connection.prepareStatement(sql,new String[]{"accountid"});//第二个参数的意思是返回这个字段生成的值
                pstm.setDouble(1,account.getBalance());
                return pstm;
            }
        },keyHolder);*/
        //方案二:lambda表达式写法
         this.jdbcTemplate.update(connection ->{
            PreparedStatement pstm=connection.prepareStatement(sql,new String[]{"accountid"});
            pstm.setDouble(1,account.getBalance());
            return pstm;
        },keyHolder);

// keyHolder.getKey() now contains the generated key
        return  keyHolder.getKey().intValue();
    }

    @Override
    public Accounts updateAccount(Accounts account) {
        String sql="update accounts set balance=? where accountid=?";
        this.jdbcTemplate.update(sql,account.getBalance(),account.getAccountId());
        return account;
    }

    @Override
    public Accounts findAccount(int accountid) {
        String sql="select * from accounts where accountid=?";

        return this.jdbcTemplate.queryForObject(sql,((resultSet, i) -> {
            Accounts a=new Accounts();
            a.setAccountId( resultSet.getInt("accountid"));
            a.setBalance(resultSet.getDouble("balance"));
            return a;
        }),accountid);
    }

    @Override
    public List<Accounts> findAll() {
        String sql="select * from accounts";
          //方案一:匿名内部类写法
//        List<Accounts> list=this.jdbcTemplate.query(sql, new RowMapper<Accounts>() {
//            @Override
//            public Accounts mapRow(ResultSet resultSet, int i) throws SQLException {
//                Accounts a=new Accounts();
//                a.setAccountId( resultSet.getInt("accountid"));
//                a.setBalance(resultSet.getDouble("balance"));
//                return a;
//            }
//        });


        //方案二:lambda表达式写法
        return this.jdbcTemplate.query(sql,((resultSet, i) -> {
                Accounts a=new Accounts();
                a.setAccountId( resultSet.getInt("accountid"));
                a.setBalance(resultSet.getDouble("balance"));
                return a;
        }));
    }

    @Override
    public void deleteAccount(int accountid) {
        String sql="delete from account where accountid=?";
        this.jdbcTemplate.update(sql,accountid);
    }
}

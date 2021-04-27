package com.yc.tx.dao;

import com.yc.tx.bean.OpRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OpRecordDaoImpl implements OpRecordDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    //在dao中要使用 jdbcTemplate的模板对象，这个对象要通过 datasource创建
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate =new JdbcTemplate(dataSource) ;
    }


    @Override
    public void saveOpRecord(OpRecord opRecord) {
        String sql="insert into oprecord(accountid,opmoney,optime,optype,transferid) values(?,?,now(),?,?)";
        this.jdbcTemplate.update(sql,opRecord.getAccountid(),opRecord.getOpmoney(),opRecord.getOptype(),opRecord.getTransferid());
    }

    @Override
    public List<OpRecord> findAll() {
        String sql="select * from oprecord";
        return this.jdbcTemplate.query(sql,((resultSet, i) -> {
            OpRecord o=new OpRecord();
            o.setId(resultSet.getInt("id") );
            o.setAccountid(resultSet.getInt("accountid"));
            o.setOpmoney(resultSet.getDouble("accountid"));
            o.setOptime(resultSet.getTimestamp("optime"));
            o.setOptype(resultSet.getString("optype"));
            o.setTransferid(resultSet.getString("transferid"));
            return o;
        }));
    }

    @Override
    public List<OpRecord> findByAccountid(int accountid) {
        String sql="select * from oprecord where accountid=?";
        return this.jdbcTemplate.query(sql,((resultSet, i) -> {
            OpRecord o=new OpRecord();
            o.setId(resultSet.getInt("id") );
            o.setAccountid(resultSet.getInt("accountid"));
            o.setOpmoney(resultSet.getDouble("accountid"));
            o.setOptime(resultSet.getTimestamp("optime"));
            o.setOptype(resultSet.getString("optype"));
            o.setTransferid(resultSet.getString("transferid"));
            return o;
        }),accountid);
    }


}

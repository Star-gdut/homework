package com.star.dao;

import com.star.domain.Data;
import com.star.domain.User;
import com.star.utils.JDBCUtils;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 检查是否存在该文件路径
     *
     * @param hdfspath 文件路径
     * @return 存在则返回true，不存在则返回false
     */
    public boolean check(String hdfspath) {
        String sql = "select * from data where hdfspath = ?";
        try {
            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), hdfspath);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加文件上传记录
     *
     * @param data 文件记录对象
     * @return 是否添加成功
     */
    public boolean add(Data data) {
        String sql = "insert into data(filename,hdfspath,cachepath,size,userid) value(?,?,?,?,?)";
        try {
            int update = jdbcTemplate.update(sql, data.getFilename(), data.getHdfspath(), data.getCachepath(), data.getSize(), data.getUserid());
            return update == 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件上传记录
     *
     * @param hdfspath 文件路径
     * @return 是否删除成功
     */
    public boolean delete(String hdfspath) {
        String sql = "select * from data where hdfspath = ?";
        try {
            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), hdfspath);
            sql = "delete from data where hdfspath = ?";
            int member = jdbcTemplate.update(sql, hdfspath);
            return member == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除某用户文件上传记录
     *
     * @param userid 用户id
     * @return 是否删除成功
     */
    public boolean deleteByUserId(int userid) {
        String sql = "select * from data where userid = ?";
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, userid);
//            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), userid);
            if (!mapList.isEmpty()) {
                sql = "delete from data where userid = ?";
                int member = jdbcTemplate.update(sql, userid);
                return member >= 1;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 查找该userid下所有文件的文件名称
     *
     * @param userid 被查询的用户id
     * @return 返回一个含有所有文件名称的列表
     */
    public List<String> getAllFilename(int userid) {
        String sql = "select * from data where userid = ?";
        try {
            List<Data> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Data>(Data.class), userid);
            ArrayList<String> result = new ArrayList<>();
            for (Data data : query) {
                result.add(data.getFilename());
            }
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找该userid下所有文件的hdfs路径
     *
     * @param userid 被查询的用户id
     * @return 返回一个含有所有hdfs路径的列表
     */
    public List<String> getAllHdfspath(int userid) {
        String sql = "select * from data where userid = ?";
        try {
            List<Data> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Data>(Data.class), userid);
            ArrayList<String> result = new ArrayList<>();
            for (Data data : query) {
                result.add(data.getHdfspath());
            }
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找该文件的hdfs路径
     *
     * @param filename 被查询的文件名
     * @return 返回一个含有所有hdfs路径的列表
     */
    public String getHdfspath(String filename) {
        String sql = "select * from data where filename = ?";
        try {
            Data query = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), filename);
            return query.getHdfspath();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找该userid下所有文件的cache路径
     *
     * @param userid 被查询的用户id
     * @return 返回一个含有所有cache路径的列表
     */
    public List<String> getAllCachepath(int userid) {
        String sql = "select * from data where userid = ?";
        try {
            List<Data> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Data>(Data.class), userid);
            ArrayList<String> result = new ArrayList<>();
            for (Data data : query) {
                result.add(data.getCachepath());
            }
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找该文件的cache路径
     *
     * @param filename 被查询的文件名
     * @return 返回一个含有所有cache路径的列表
     */
    public String getCachepath(String filename) {
        String sql = "select * from data where filename = ?";
        try {
            Data query = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), filename);
            return query.getCachepath();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取该文件的大小
     *
     * @param filename 文件名
     * @param userid   用户id
     * @return 文件的大小
     */
    public double getSize(String filename, int userid) {
        String sql = "select * from data where filename = ? and userid = ?";
        try {
            Data data = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), filename, userid);
            return data.getSize();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取该文件的上传时间
     *
     * @param filename 文件名
     * @param userid   用户id
     * @return 文件的上传时间
     */
    public String getDate(String filename, int userid) {
        String sql = "select * from data where filename = ? and userid = ?";
        try {
//            Data data = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Data>(Data.class), filename, userid);   这种方法无法封装timestamp
            Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql, filename, userid);
            Timestamp date = (Timestamp) stringObjectMap.get("date");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(date);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}

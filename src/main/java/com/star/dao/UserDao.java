package com.star.dao;

import com.star.domain.User;
import com.star.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 操作用户的类（增删改查数据库）
 */

public class UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查找数据库中是否存在该用户
     *
     * @param loginUser 只包含用户名与密码
     * @return 包含用户所有信息
     */
    public User find(User loginUser) {
        String sql = "select * from user where username = ? and password = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), loginUser.getUsername(), loginUser.getPassword());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 往数据库中新增用户
     *
     * @param user 新增的用户信息
     * @return 成功返回true
     */
    public boolean add(User user) {
        String sql = "select * from user where username = ?";
        try {
            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            sql = "insert into user(username,password,role) values(?,?,?)";
            int member = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), "member");
            return member == 1;
        }
        return false;
    }

    /**
     * 修改密码
     * @param user 新的用户信息
     * @return 成功返回true
     */
    public boolean update(User user) {
        String sql = "select * from user where username = ?";
        try {
            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
            sql = "update user set password = ? where username = ?";
            int member = jdbcTemplate.update(sql, user.getPassword(), user.getUsername());
            return member == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除用户
     * @param user 删除的用户信息
     * @return 成功返回true
     */
    public boolean delete(User user) {
        String sql = "select * from user where username = ?";
        try {
            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
            sql = "delete from user where username = ? and role = 'member'";    // 防止误删管理员账户
            int member = jdbcTemplate.update(sql, user.getUsername());
            return member == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询用户的id
     * @param username 被查询的用户名
     * @return 返回用户id值 不存在则返回-1
     */
    public int showid(String username){
        String sql = "select * from user where username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user.getId();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

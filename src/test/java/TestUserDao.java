import com.star.dao.UserDao;
import com.star.domain.User;
import org.junit.Test;

public class TestUserDao {
    @Test
    public void testFind() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        User user1 = userDao.find(user);
        System.out.println(user1);
    }

    @Test
    public void testAdd() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("test3");
        user.setPassword("tt2");
        boolean add = userDao.add(user);
        if (add) {
            System.out.println("注册成功，用户名为：" + user.getUsername());
        } else {
            System.out.println("注册失败，用户名已被注册");
        }
    }

    @Test
    public void testUpdate() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("test3");
        user.setPassword("tt333");
        boolean add = userDao.update(user);
        if (add) {
            System.out.println("密码修改成功！");
        } else {
            System.out.println("密码修改失败！");
        }
    }

    @Test
    public void testDelete() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("trity");
        boolean delete = userDao.delete(user);
        if (delete) {
            System.out.println("你已成功删除用户：" + user.getUsername());
        } else {
            System.out.println("用户删除失败！");
        }
    }

    @Test
    public void testShowid(){
        UserDao userDao = new UserDao();
        String username = "test2000";
        int showid = userDao.showid(username);
        System.out.println(showid);
    }

}

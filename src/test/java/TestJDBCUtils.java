import com.star.utils.JDBCUtils;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestJDBCUtils {
    @Test
    public void test() throws SQLException {
        DataSource dataSource = JDBCUtils.getDataSource();
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}

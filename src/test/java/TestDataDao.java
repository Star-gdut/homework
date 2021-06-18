import com.star.dao.DataDao;
import com.star.domain.Data;
import org.junit.Test;

import java.util.List;

public class TestDataDao {
    @Test
    public void testCheck(){
        DataDao dataDao = new DataDao();
        String hdfsname = "test hdfspath";
        boolean check = dataDao.check(hdfsname);
        System.out.println(check);
    }
    @Test
    public void testAdd(){
        DataDao dataDao = new DataDao();
        Data data = new Data();
        data.setFilename("testfile");
        data.setHdfspath("test hdfspath");
        data.setCachepath("test cachepath");
        data.setSize(3.37);
        data.setUserid(2);
        boolean add = dataDao.add(data);
        System.out.println(add);
    }
    @Test
    public void testDelete(){
        DataDao dataDao = new DataDao();
        String hdfspath = "test hdfspath";
        boolean delete = dataDao.delete(hdfspath);
        System.out.println(delete);
    }

    @Test
    public void testDeleteById(){
        DataDao dataDao = new DataDao();
        int userid = 1;
        boolean b = dataDao.deleteByUserId(userid);
        System.out.println(b);
    }

    @Test
    public void testAllFilename(){
        DataDao dataDao = new DataDao();
        int userid = 1;
        List<String> allFilename = dataDao.getAllFilename(userid);
        for (String filename : allFilename) {
            System.out.println(filename);
        }
    }

    @Test
    public void testAllHdfspath(){
        DataDao dataDao = new DataDao();
        int userid = 1;
        List<String> allHdfspath = dataDao.getAllHdfspath(userid);
        for (String hdfspath : allHdfspath) {
            System.out.println(hdfspath);
        }
    }

    @Test
    public void testHdfspath(){
        DataDao dataDao = new DataDao();
        String filename = "testfile999";
        String hdfspath = dataDao.getHdfspath(filename);
        System.out.println(hdfspath);
    }

    @Test
    public void testAllCachepath(){
        DataDao dataDao = new DataDao();
        int userid = 1;
        List<String> allCachepath = dataDao.getAllCachepath(userid);
        for (String cachepath : allCachepath) {
            System.out.println(cachepath);
        }
    }

    @Test
    public void testCachepath(){
        DataDao dataDao = new DataDao();
        String filename = "testfile";
        String cachepath = dataDao.getCachepath(filename);
        System.out.println(cachepath);
    }

    @Test
    public void testSize(){
        DataDao dataDao = new DataDao();
        String filename = "testfile2";
        int userid = 1;
        double size = dataDao.getSize(filename, userid);
        System.out.println(size);
    }

    @Test
    public void testDate(){
        DataDao dataDao = new DataDao();
        String filename = "testfile2";
        int userid = 1;
        String date = dataDao.getDate(filename, userid);
        System.out.println(date);
    }
}

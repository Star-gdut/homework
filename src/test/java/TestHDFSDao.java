import com.star.dao.HDFSDao;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestHDFSDao {
    @Test
    public void testFind() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean b = hdfsDao.find("/qkx");
        System.out.println(b);
    }

    @Test
    public void testDownload() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean download = hdfsDao.download("d:\\", "/qkx");
        System.out.println(download);
    }

    @Test
    public void testUpload() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean upload = hdfsDao.upload("d:\\testfile", "/qkx/test");
        System.out.println(upload);
    }

    @Test
    public void testDelete() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean delete = hdfsDao.delete("/qkx/homework/admin");
        System.out.println(delete);
    }

    @Test
    public void testCreate() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean b = hdfsDao.create("/qkx/testfile/new");
        System.out.println(b);
    }

    @Test
    public void testDownToStream() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean b = hdfsDao.downloadToStream(new FileOutputStream("d:\\aaa.txt"), "/qkx/test/aaa.txt");
        System.out.println(b);
    }

    @Test
    public void testUploadFromStream() throws IOException {
        HDFSDao hdfsDao = new HDFSDao();
        boolean b = hdfsDao.uploadFromStream(new FileInputStream("d:\\aaa.txt"), "/qkx/aaa.txt");
        System.out.println(b);
    }
}

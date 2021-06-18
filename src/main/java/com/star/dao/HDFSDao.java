package com.star.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.*;

/**
 * 操作HDFS的类（上传、下载或删除）
 * Stream方法只能上传或下载文件
 * 普通方法可以上传或下载文件夹或文件
 */

public class HDFSDao {
    static Configuration conf = new Configuration();
    static FileSystem fs;

    /**
     * 查找文件或文件夹
     *
     * @param hdfspath hdfs下的文件/文件夹路径
     * @return 是否查找成功
     * @throws IOException
     */
    public boolean find(String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        Path path = new Path(hdfspath);
        FileStatus[] fileStatuses = fs.listStatus(path);
        if (fileStatuses == null) {
            fs.close();
            return false;
        }
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus);
        }
        fs.close();
        return true;
    }

    /**
     * 上传文件
     *
     * @param filepath 本地文件路径
     * @param hdfspath hdfs文件路径（上传位置）
     * @return 是否上传成功
     * @throws IOException
     */
    public boolean upload(String filepath, String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        try {
            fs.copyFromLocalFile(new Path(filepath), new Path(hdfspath));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fs.close();
        }
        return true;
    }

    /**
     * 上传文件（通过流）
     *
     * @param is       上传的文件输入流（指向到文件名）
     * @param hdfspath hdfs文件路径（指向到文件名）
     * @return 是否上传成功
     * @throws IOException
     */
    public boolean uploadFromStream(InputStream is, String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        try {
            FSDataOutputStream fos = fs.create(new Path(hdfspath));
            IOUtils.copyBytes(is, fos, 5 * 1024, true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fs.close();
        }
        return true;
    }

    /**
     * 下载文件
     *
     * @param filepath 本地存放路径
     * @param hdfspath hdfs文件路径
     * @return 是否下载成功
     * @throws IOException
     */
    public boolean download(String filepath, String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        try {
            fs.copyToLocalFile(false, new Path(hdfspath), new Path(filepath), true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fs.close();
        }
        return true;
    }

    /**
     * 下载文件（通过流）
     *
     * @param os       下载的文件输出到该流（指向路径即文件名 路径必须存在）
     * @param hdfspath hdfs文件路径（指向文件）
     * @return 是否下载成功
     * @throws IOException
     */
    public boolean downloadToStream(OutputStream os, String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        try {
            FSDataInputStream fis = fs.open(new Path(hdfspath));
            IOUtils.copyBytes(fis, os, 5 * 1024, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fs.close();
        }
        return true;
    }

    /**
     * 删除文件或文件夹
     *
     * @param hdfspath 删除该路径指向的文件或文件夹的所有内容
     * @return 是否成功删除
     * @throws IOException
     */
    public boolean delete(String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        try {
            fs.delete(new Path(hdfspath));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fs.close();
        }
        return true;
    }

    /**
     * 创建文件夹
     *
     * @param hdfspath 创建的路径
     * @return 是否创建成功
     * @throws IOException
     */
    public boolean create(String hdfspath) throws IOException {
        fs = FileSystem.get(conf);
        try {
            fs.mkdirs(new Path(hdfspath));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fs.close();
        }
        return true;
    }
}

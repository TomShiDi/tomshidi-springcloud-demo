package com.tomshidi.hadoop;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author TomShiDi
 * @since 2024/2/4 16:25
 */
public class HadoopCurdTest {

    private static FileSystem fileSystem;

    @BeforeAll
    public static void init() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://node1:8020");
//        FileSystem.get(new URI("hdfs://node1:8020"), conf, "root");
        fileSystem = FileSystem.get(conf);
    }

    @Test
    public void getFileSystem() throws IOException {
        System.out.println(fileSystem.toString());
    }

    @Test
    public void listFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/"), true);
        while (files.hasNext()) {
            LocatedFileStatus fileStatus = files.next();
            System.out.println(fileStatus.getPath().toString());

            // Linux生成指定大小的文件命令 dd if=/dev/zero of=my.txt bs=100M count=1
            // 获取文件的block信息
            BlockLocation[] locations = fileStatus.getBlockLocations();
            for (BlockLocation location : locations) {
                System.out.println(location.toString());
            }
            System.out.println("******************");
        }
        fileSystem.close();
    }

    /**
     * 创建目录
     * @throws IOException IO异常
     */
    @Test
    public void createDir() throws IOException {
        fileSystem.create(new Path("/xxx/yyy/ccc"));
    }

    /**
     * 下载文件
     * @throws IOException IO异常
     */
    @Test
    public void downloadFile1() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\hadoop-data.txt"));
        FSDataInputStream inputStream = fileSystem.open(new Path("/data.txt"));
        IOUtils.copy(inputStream, fileOutputStream);
        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(inputStream);
    }

    /**
     * 文件下载
     * @throws IOException IO异常
     */
    @Test
    public void downloadFile2() throws IOException {
        fileSystem.copyToLocalFile(new Path("/data.txt"), new Path("E:\\hadoop-data.txt"));
    }

    /**
     * 上传文件
     * @throws IOException IO异常
     */
    @Test
    public void uploadFile() throws IOException {
        fileSystem.copyFromLocalFile(new Path("E:\\hadoop-conf.zip"), new Path("/hadoop-conf.zip"));
    }

    /**
     * 文件合并
     * @throws IOException IO异常
     */
    @Test
    public void mergeFile() throws IOException {
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/mergefile.txt"));
        LocalFileSystem localFileSystem = FileSystem.getLocal(new Configuration());
        FileStatus[] fileStatuses = localFileSystem.listStatus(new Path("E:\\hadoop-2.7.5\\input-data"));
        for (FileStatus fileStatus : fileStatuses) {
            FSDataInputStream inputStream = localFileSystem.open(fileStatus.getPath());
            IOUtils.copy(inputStream, fsDataOutputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    @AfterAll
    public static void destroy() throws IOException {
        fileSystem.close();
    }
}

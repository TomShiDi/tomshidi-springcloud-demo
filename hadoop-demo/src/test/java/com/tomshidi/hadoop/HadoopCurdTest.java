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

/**
 * @author TomShiDi
 * @since 2024/2/4 16:25
 */
public class HadoopCurdTest {

    private static FileSystem fileSystem;

    @BeforeAll
    public static void init() throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://node1:8020");
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

    @Test
    public void downloadFile1() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\hadoop-data.txt"));
        FSDataInputStream inputStream = fileSystem.open(new Path("/data.txt"));
        IOUtils.copy(inputStream, fileOutputStream);
        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(inputStream);
    }

    @Test
    public void downloadFile2() throws IOException {
        fileSystem.copyToLocalFile(new Path("/data.txt"), new Path("E:\\hadoop-data.txt"));
    }

    @Test
    public void uploadFile() throws IOException {
        fileSystem.copyFromLocalFile(new Path("E:\\hadoop-conf.zip"), new Path("/hadoop-conf.zip"));
    }

    @AfterAll
    public static void destroy() throws IOException {
        fileSystem.close();
    }
}

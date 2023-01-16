package com.tomshidi.demo.factory;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * 方便docker挂载自定义配置文件
 * 从classpath与外部挂载目录载入配置，外部挂载目录配置优先级更高
 * @author tomshidi
 * @date 2022/2/17 17:08
 */
public class TomPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        Resource fileSystemResource = null;
        if (encodedResource.getResource() instanceof ClassPathResource) {
            String path = ((ClassPathResource) encodedResource.getResource()).getPath();
            fileSystemResource = new FileSystemResource(path);
        }
        if (fileSystemResource != null && fileSystemResource.exists()) {
            encodedResource = new EncodedResource(fileSystemResource, encodedResource.getEncoding());
        }
        return super.createPropertySource(name, encodedResource);
    }
}

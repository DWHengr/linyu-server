package com.cershy.linyuserver.runner;

import com.cershy.linyuserver.utils.MinioUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class MinioRunner implements ApplicationRunner {
    @Resource
    MinioUtil minioUtil;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Path resourcePath = Paths.get(new ClassPathResource("minio").getURI());
            Files.walk(resourcePath)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            // 保留子目录结构的objectName
                            String objectName = resourcePath.relativize(path).toString().replace("\\", "/");
                            if (!minioUtil.isObjectExist(objectName)) {
                                try (InputStream inputStream = Files.newInputStream(path)) {
                                    long size = Files.size(path);
                                    String contentType = Files.probeContentType(path);
                                    if (contentType == null) {
                                        contentType = "application/octet-stream";
                                    }
                                    minioUtil.upload(inputStream, objectName, contentType, size);
                                } catch (Exception e) {
                                    System.err.println("Error uploading file " + objectName + ": " + e.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Error processing file " + path + ": " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
        System.out.println("-----minio initialization is complete-----");
    }
}

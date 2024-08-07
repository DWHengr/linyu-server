package com.cershy.linyuserver.runner;

import com.cershy.linyuserver.utils.MinioUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class MinioRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MinioRunner.class);

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:minio/**");
            if (resources.length > 0) {
                URI uri = resources[0].getURI();
                if ("jar".equals(uri.getScheme())) {
                    processJarResources(uri);
                } else {
                    processFileSystemResources(resources);
                }
            } else {
                logger.warn("No resources found in minio directory");
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("Error occurred while reading resources", e);
        }
        logger.info("-----minio initialization is complete-----");
    }

    private void processJarResources(URI uri) throws IOException, URISyntaxException {
        try (FileSystem fs = FileSystems.newFileSystem(
                URI.create(uri.toString().split("!")[0]), Collections.emptyMap())) {
            Path myPath = fs.getPath("/BOOT-INF/classes/minio");
            if (Files.exists(myPath)) {
                try (Stream<Path> walk = Files.walk(myPath)) {
                    walk.filter(Files::isRegularFile).forEach(this::processPath);
                }
            } else {
                logger.warn("Minio directory not found in JAR at {}", myPath);
            }
        }
    }

    private void processFileSystemResources(Resource[] resources) throws IOException {
        for (Resource resource : resources) {
            if (resource.isReadable() && resource.isFile()) {
                processResource(resource);
            }
        }
    }

    private void processPath(Path path) {
        String objectName = path.toString().substring("/BOOT-INF/classes/minio/".length());
        try (InputStream inputStream = Files.newInputStream(path)) {
            uploadToMinio(objectName, inputStream, Files.size(path));
        } catch (IOException e) {
            logger.error("Error processing file: {}", objectName, e);
        }
    }

    private void processResource(Resource resource) {
        try {
            String path = resource.getURI().toString();
            String objectName = path.substring(path.indexOf("minio/") + "minio/".length()).replace("\\", "/");
            try (InputStream inputStream = resource.getInputStream()) {
                uploadToMinio(objectName, inputStream, resource.contentLength());
            }
        } catch (Exception e) {
            logger.error("Error processing resource: {}", resource.getFilename(), e);
        }
    }

    private void uploadToMinio(String objectName, InputStream inputStream, long size) throws IOException {
        if (!minioUtil.isObjectExist(objectName)) {
            String contentType = determineContentType(objectName);
            minioUtil.upload(inputStream, objectName, contentType, size);
            logger.info("Uploaded file: {}", objectName);
        } else {
            logger.info("File already exists: {}", objectName);
        }
    }

    private String determineContentType(String filename) {
        String extension = getFileExtension(filename);
        switch (extension.toLowerCase()) {
            case "txt":
                return "text/plain";
            case "html":
            case "htm":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "application/javascript";
            case "json":
                return "application/json";
            case "xml":
                return "application/xml";
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";
            default:
                return "application/octet-stream";
        }
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return filename.substring(lastIndexOf + 1);
    }
}
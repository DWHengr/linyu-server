package com.cershy.linyuserver.controller;

import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.utils.MinioUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.InputStream;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    MinioUtil minioUtil;

    private static RestTemplate restTemplate = new RestTemplate();


    private ByteArrayResource createByteArrayResource(byte[] content, String filename) {
        return new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return filename;
            }
        };
    }

    @UrlFree
    @GetMapping()
    public void test() throws Exception {
        // 从 MinIO 获取文件
        InputStream inputStream = minioUtil.getObject("4/1/d1e6796d-ecd0-4877-ba0c-228e8dfa37c3.wav");

        byte[] content = IOUtils.toByteArray(inputStream);
        ByteArrayResource fileResource = createByteArrayResource(content, "4/1/d1e6796d-ecd0-4877-ba0c-228e8dfa37c3.wav");

        // 准备 HTTP 请求
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);
        body.add("model", "/root/.cache/huggingface/Systran/faster-whisper-small/");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送请求
        String url = "http://123.207.210.163:8000/v1/audio/transcriptions";
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println(response.getBody());
    }
}

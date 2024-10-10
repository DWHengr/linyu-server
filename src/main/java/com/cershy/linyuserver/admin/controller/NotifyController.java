package com.cershy.linyuserver.admin.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.admin.vo.notify.DeleteNotifyVo;
import com.cershy.linyuserver.annotation.UrlResource;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.SystemNotifyDto;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController("AdminNotifyController")
@RequestMapping("/admin/v1/api/notify")
@Slf4j
public class NotifyController {

    @Resource
    NotifyService notifyService;

    @Resource
    MinioUtil minioUtil;

    /**
     * 系统通知列表
     *
     * @return
     */
    @GetMapping("/system/list")
    @UrlResource("admin")
    public JSONObject SystemListNotify(@Userid String userId) {
        List<SystemNotifyDto> result = notifyService.SystemListNotify(userId);
        return ResultUtil.Succeed(result);
    }

    /**
     * 系统通知删除
     *
     * @return
     */
    @PostMapping("/system/delete")
    @UrlResource("admin")
    public JSONObject deleteNotify(@RequestBody DeleteNotifyVo deleteNotifyVo) {
        boolean result = notifyService.deleteNotify(deleteNotifyVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 系统通知创建
     *
     * @return
     */
    @PostMapping("/system/create")
    @UrlResource("admin")
    public JSONObject createNotify(@NotNull(message = "图片不能为空~") @RequestParam("file") MultipartFile file,
                                   @NotNull(message = "标题不能为空~") @RequestParam("title") String title,
                                   @NotNull(message = "内容不能为空~") @RequestParam("text") String text) {
        String url;
        try {
            url = minioUtil.upload(file.getInputStream(), "notify/" + IdUtil.randomUUID(), file.getContentType(), file.getSize());
        } catch (Exception e) {
            throw new LinyuException("图片上传失败~");
        }
        boolean result = notifyService.createNotify(url, title, text);
        return ResultUtil.ResultByFlag(result);
    }
}

package com.cershy.linyuserver.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class MybatisHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), metaObject);
        this.setFieldValByName("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), metaObject);
    }

}

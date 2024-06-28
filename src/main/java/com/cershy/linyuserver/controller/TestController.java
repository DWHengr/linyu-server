package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    WebSocketService webSocketService;

    @UrlFree
    @PostMapping("/send/msg")
    public String sendMsg(@RequestBody Object request) {
        webSocketService.sendMsgAll(request);
        return "succeed";
    }

    @UrlFree
    @PostMapping("/send/file")
    public String sendFile(HttpServletRequest request) throws IOException {
        request.getInputStream();
        return "succeed";
    }

    @UrlFree
    @GetMapping("/users")
    public JSONArray users() {
        String user = "[\n" +
                "    {\n" +
                "        \"address\": \"\",\n" +
                "        \"cn\": \"潘多\",\n" +
                "        \"createdDt\": \"2024-05-14 15: 04: 27\",\n" +
                "        \"createdId\": 60,\n" +
                "        \"description\": \"\",\n" +
                "        \"displayOrder\": \"0390101121/1000\",\n" +
                "        \"email\": \"18957132302@139.com\",\n" +
                "        \"employType\": 1,\n" +
                "        \"endTime\": \"2024-06-12 15: 03: 34\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"firstPhone\": \"18957132302\",\n" +
                "        \"id\": 28633,\n" +
                "        \"idToString\": \"28633\",\n" +
                "        \"idcard\": \"\",\n" +
                "        \"ihrUserCode\": \"\",\n" +
                "        \"leaderName\": \"\",\n" +
                "        \"nation\": \"\",\n" +
                "        \"nationality\": \"\",\n" +
                "        \"new\": false,\n" +
                "        \"orgCode\": \"0390101121\",\n" +
                "        \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "        \"orgShortName\": \"视频物联网产品部\",\n" +
                "        \"organizationJobs\": [\n" +
                "            {\n" +
                "                \"baseInfoId\": 28633,\n" +
                "                \"createdDt\": \"2024-05-14 15: 04: 27\",\n" +
                "                \"createdId\": 60,\n" +
                "                \"id\": 114672,\n" +
                "                \"idToString\": \"114672\",\n" +
                "                \"jobId\": 15799,\n" +
                "                \"jobName\": \"研发工程师\",\n" +
                "                \"new\": false,\n" +
                "                \"orgCode\": \"0390101121\",\n" +
                "                \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "                \"orgId\": 485,\n" +
                "                \"orgShortName\": \"视频物联网产品部\",\n" +
                "                \"primaryJob\": 1,\n" +
                "                \"standardJobId\": 1,\n" +
                "                \"standardJobName\": \"外部员工职位\",\n" +
                "                \"status\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"outUserSource\": 1,\n" +
                "        \"password\": \"ef7413478831561c3fcf002ea48c8640\",\n" +
                "        \"photoUrl\": \"\",\n" +
                "        \"political\": \"\",\n" +
                "        \"post\": \"0390101121-143709\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"postName\": \"研发工程师\",\n" +
                "        \"salt\": \"E7VqunpeMo\",\n" +
                "        \"secondPhone\": \"\",\n" +
                "        \"sequence\": \"product\",\n" +
                "        \"sex\": 1,\n" +
                "        \"sn\": \"潘\",\n" +
                "        \"sortOrder\": 1000,\n" +
                "        \"standardPost\": \"0390101121-340000\",\n" +
                "        \"standardPostName\": \"外部员工职位\",\n" +
                "        \"status\": 0,\n" +
                "        \"superviseDept\": \"\",\n" +
                "        \"supervisor\": \"leiyu\",\n" +
                "        \"supporterCorpContact\": \"雷宇\",\n" +
                "        \"supporterCorpName\": \"\",\n" +
                "        \"supporterDept\": \"视频物联网产品部\",\n" +
                "        \"systemRoles\": [],\n" +
                "        \"userCode\": \"tp202405140011\",\n" +
                "        \"userOldCode\": \"tp202405140011\",\n" +
                "        \"userType\": 2,\n" +
                "        \"username\": \"tp_panduo\",\n" +
                "        \"workOrgCode\": \"\",\n" +
                "        \"workPhone\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"address\": \"\",\n" +
                "        \"cn\": \"陈培\",\n" +
                "        \"createdDt\": \"2024-05-14 15: 05: 15\",\n" +
                "        \"createdId\": 60,\n" +
                "        \"description\": \"\",\n" +
                "        \"displayOrder\": \"0390101121/1000\",\n" +
                "        \"email\": \"18100173796@139.com\",\n" +
                "        \"employType\": 1,\n" +
                "        \"endTime\": \"2024-06-12 15: 04: 28\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"firstPhone\": \"18100173796\",\n" +
                "        \"id\": 28634,\n" +
                "        \"idToString\": \"28634\",\n" +
                "        \"idcard\": \"\",\n" +
                "        \"ihrUserCode\": \"\",\n" +
                "        \"leaderName\": \"\",\n" +
                "        \"nation\": \"\",\n" +
                "        \"nationality\": \"\",\n" +
                "        \"new\": false,\n" +
                "        \"orgCode\": \"0390101121\",\n" +
                "        \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "        \"orgShortName\": \"视频物联网产品部\",\n" +
                "        \"organizationJobs\": [\n" +
                "            {\n" +
                "                \"baseInfoId\": 28634,\n" +
                "                \"createdDt\": \"2024-05-14 15: 05: 15\",\n" +
                "                \"createdId\": 60,\n" +
                "                \"id\": 114673,\n" +
                "                \"idToString\": \"114673\",\n" +
                "                \"jobId\": 15799,\n" +
                "                \"jobName\": \"研发工程师\",\n" +
                "                \"new\": false,\n" +
                "                \"orgCode\": \"0390101121\",\n" +
                "                \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "                \"orgId\": 485,\n" +
                "                \"orgShortName\": \"视频物联网产品部\",\n" +
                "                \"primaryJob\": 1,\n" +
                "                \"standardJobId\": 1,\n" +
                "                \"standardJobName\": \"外部员工职位\",\n" +
                "                \"status\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"outUserSource\": 1,\n" +
                "        \"password\": \"c84d5b6356e63d59d0d99c3b6e46faaf\",\n" +
                "        \"photoUrl\": \"\",\n" +
                "        \"political\": \"\",\n" +
                "        \"post\": \"0390101121-143709\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"postName\": \"研发工程师\",\n" +
                "        \"salt\": \"ZlujMlz8RY\",\n" +
                "        \"secondPhone\": \"\",\n" +
                "        \"sequence\": \"product\",\n" +
                "        \"sex\": 1,\n" +
                "        \"sn\": \"陈\",\n" +
                "        \"sortOrder\": 1000,\n" +
                "        \"standardPost\": \"0390101121-340000\",\n" +
                "        \"standardPostName\": \"外部员工职位\",\n" +
                "        \"status\": 1,\n" +
                "        \"superviseDept\": \"\",\n" +
                "        \"supervisor\": \"leiyu\",\n" +
                "        \"supporterCorpContact\": \"雷宇\",\n" +
                "        \"supporterCorpName\": \"\",\n" +
                "        \"supporterDept\": \"视频物联网产品部\",\n" +
                "        \"systemRoles\": [],\n" +
                "        \"userCode\": \"tp202405140012\",\n" +
                "        \"userOldCode\": \"tp202405140012\",\n" +
                "        \"userType\": 2,\n" +
                "        \"username\": \"tp_chenpei\",\n" +
                "        \"workOrgCode\": \"\",\n" +
                "        \"workPhone\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"address\": \"\",\n" +
                "        \"cn\": \"彭开虎\",\n" +
                "        \"createdDt\": \"2024-05-14 15: 06: 08\",\n" +
                "        \"createdId\": 60,\n" +
                "        \"description\": \"\",\n" +
                "        \"displayOrder\": \"0390101121/1000\",\n" +
                "        \"email\": \"19906780250@139.com\",\n" +
                "        \"employType\": 1,\n" +
                "        \"endTime\": \"2024-06-12 15: 05: 16\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"firstPhone\": \"19906780250\",\n" +
                "        \"id\": 28635,\n" +
                "        \"idToString\": \"28635\",\n" +
                "        \"idcard\": \"\",\n" +
                "        \"ihrUserCode\": \"\",\n" +
                "        \"leaderName\": \"\",\n" +
                "        \"nation\": \"\",\n" +
                "        \"nationality\": \"\",\n" +
                "        \"new\": false,\n" +
                "        \"orgCode\": \"0390101121\",\n" +
                "        \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "        \"orgShortName\": \"视频物联网产品部\",\n" +
                "        \"organizationJobs\": [\n" +
                "            {\n" +
                "                \"baseInfoId\": 28635,\n" +
                "                \"createdDt\": \"2024-05-14 15: 06: 08\",\n" +
                "                \"createdId\": 60,\n" +
                "                \"id\": 114674,\n" +
                "                \"idToString\": \"114674\",\n" +
                "                \"jobId\": 15799,\n" +
                "                \"jobName\": \"研发工程师\",\n" +
                "                \"new\": false,\n" +
                "                \"orgCode\": \"0390101121\",\n" +
                "                \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "                \"orgId\": 485,\n" +
                "                \"orgShortName\": \"视频物联网产品部\",\n" +
                "                \"primaryJob\": 1,\n" +
                "                \"standardJobId\": 1,\n" +
                "                \"standardJobName\": \"外部员工职位\",\n" +
                "                \"status\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"outUserSource\": 1,\n" +
                "        \"password\": \"67e794bad9635cf97ee595d76d46f41c\",\n" +
                "        \"photoUrl\": \"\",\n" +
                "        \"political\": \"\",\n" +
                "        \"post\": \"0390101121-143709\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"postName\": \"研发工程师\",\n" +
                "        \"salt\": \"sVmUjn2bJC\",\n" +
                "        \"secondPhone\": \"\",\n" +
                "        \"sequence\": \"product\",\n" +
                "        \"sex\": 1,\n" +
                "        \"sn\": \"彭\",\n" +
                "        \"sortOrder\": 1000,\n" +
                "        \"standardPost\": \"0390101121-340000\",\n" +
                "        \"standardPostName\": \"外部员工职位\",\n" +
                "        \"status\": 0,\n" +
                "        \"superviseDept\": \"\",\n" +
                "        \"supervisor\": \"leiyu\",\n" +
                "        \"supporterCorpContact\": \"雷宇\",\n" +
                "        \"supporterCorpName\": \"\",\n" +
                "        \"supporterDept\": \"视频物联网产品部\",\n" +
                "        \"systemRoles\": [],\n" +
                "        \"userCode\": \"tp202405140013\",\n" +
                "        \"userOldCode\": \"tp202405140013\",\n" +
                "        \"userType\": 2,\n" +
                "        \"username\": \"tp_pengkaihu\",\n" +
                "        \"workOrgCode\": \"\",\n" +
                "        \"workPhone\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"address\": \"\",\n" +
                "        \"cn\": \"叶蔚\",\n" +
                "        \"createdDt\": \"2024-03-21 09: 04: 00\",\n" +
                "        \"createdId\": 60,\n" +
                "        \"description\": \"\",\n" +
                "        \"displayOrder\": \"0390100586/1000\",\n" +
                "        \"email\": \"18680333215@139.com\",\n" +
                "        \"employType\": 1,\n" +
                "        \"endTime\": \"2024-06-04 00: 00: 00\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"firstPhone\": \"18680333215\",\n" +
                "        \"id\": 28124,\n" +
                "        \"idToString\": \"28124\",\n" +
                "        \"idcard\": \"\",\n" +
                "        \"ihrUserCode\": \"\",\n" +
                "        \"leaderName\": \"\",\n" +
                "        \"nation\": \"\",\n" +
                "        \"nationality\": \"\",\n" +
                "        \"new\": false,\n" +
                "        \"orgCode\": \"0390100586\",\n" +
                "        \"orgFullName\": \"物联网公司\\\\智能硬件产品部\",\n" +
                "        \"orgShortName\": \"智能硬件产品部\",\n" +
                "        \"organizationJobs\": [\n" +
                "            {\n" +
                "                \"baseInfoId\": 28124,\n" +
                "                \"createdDt\": \"2024-03-21 09: 04: 00\",\n" +
                "                \"createdId\": 60,\n" +
                "                \"id\": 111549,\n" +
                "                \"idToString\": \"111549\",\n" +
                "                \"jobId\": 15832,\n" +
                "                \"jobName\": \"安卓开发\",\n" +
                "                \"new\": false,\n" +
                "                \"orgCode\": \"0390100586\",\n" +
                "                \"orgFullName\": \"物联网公司\\\\商客产品部\",\n" +
                "                \"orgId\": 11,\n" +
                "                \"orgShortName\": \"商客产品部\",\n" +
                "                \"primaryJob\": 1,\n" +
                "                \"standardJobId\": 1,\n" +
                "                \"standardJobName\": \"外部员工职位\",\n" +
                "                \"status\": 0,\n" +
                "                \"updatedDt\": \"2024-03-21 10: 13: 01\",\n" +
                "                \"updatedId\": 61\n" +
                "            }\n" +
                "        ],\n" +
                "        \"outUserSource\": 1,\n" +
                "        \"password\": \"a783b642c55069565f33e85b91aa9ec8\",\n" +
                "        \"photoUrl\": \"\",\n" +
                "        \"political\": \"\",\n" +
                "        \"post\": \"0390100586-172749\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"postName\": \"安卓开发\",\n" +
                "        \"salt\": \"E1AaPmtsKf\",\n" +
                "        \"secondPhone\": \"\",\n" +
                "        \"sequence\": \"product\",\n" +
                "        \"sex\": 1,\n" +
                "        \"sn\": \"叶\",\n" +
                "        \"sortOrder\": 1000,\n" +
                "        \"standardPost\": \"0390100586-340000\",\n" +
                "        \"standardPostName\": \"外部员工职位\",\n" +
                "        \"status\": 0,\n" +
                "        \"superviseDept\": \"\",\n" +
                "        \"supervisor\": \"liyutong\",\n" +
                "        \"supporterCorpContact\": \"李雨桐\",\n" +
                "        \"supporterCorpName\": \"\",\n" +
                "        \"supporterDept\": \"智能硬件产品部\",\n" +
                "        \"systemRoles\": [],\n" +
                "        \"userCode\": \"tp202403210001\",\n" +
                "        \"userOldCode\": \"tp202403210001\",\n" +
                "        \"userType\": 2,\n" +
                "        \"username\": \"tp_yewei\",\n" +
                "        \"workOrgCode\": \"\",\n" +
                "        \"workPhone\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"address\": \"\",\n" +
                "        \"cn\": \"琚鹏飞\",\n" +
                "        \"createdDt\": \"2024-05-14 15: 07: 16\",\n" +
                "        \"createdId\": 60,\n" +
                "        \"description\": \"\",\n" +
                "        \"displayOrder\": \"0390101121/1000\",\n" +
                "        \"email\": \"19906789109@139.com\",\n" +
                "        \"employType\": 1,\n" +
                "        \"endTime\": \"2024-06-12 15: 06: 09\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"firstPhone\": \"19906789109\",\n" +
                "        \"id\": 28636,\n" +
                "        \"idToString\": \"28636\",\n" +
                "        \"idcard\": \"\",\n" +
                "        \"ihrUserCode\": \"\",\n" +
                "        \"leaderName\": \"\",\n" +
                "        \"nation\": \"\",\n" +
                "        \"nationality\": \"\",\n" +
                "        \"new\": false,\n" +
                "        \"orgCode\": \"0390101121\",\n" +
                "        \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "        \"orgShortName\": \"视频物联网产品部\",\n" +
                "        \"organizationJobs\": [\n" +
                "            {\n" +
                "                \"baseInfoId\": 28636,\n" +
                "                \"createdDt\": \"2024-05-14 15: 07: 16\",\n" +
                "                \"createdId\": 60,\n" +
                "                \"id\": 114675,\n" +
                "                \"idToString\": \"114675\",\n" +
                "                \"jobId\": 15799,\n" +
                "                \"jobName\": \"研发工程师\",\n" +
                "                \"new\": false,\n" +
                "                \"orgCode\": \"0390101121\",\n" +
                "                \"orgFullName\": \"物联网公司\\\\视频物联网产品部\",\n" +
                "                \"orgId\": 485,\n" +
                "                \"orgShortName\": \"视频物联网产品部\",\n" +
                "                \"primaryJob\": 1,\n" +
                "                \"standardJobId\": 1,\n" +
                "                \"standardJobName\": \"外部员工职位\",\n" +
                "                \"status\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"outUserSource\": 1,\n" +
                "        \"password\": \"ce5fe1c358f07db6562a3ae31cbde66d\",\n" +
                "        \"photoUrl\": \"\",\n" +
                "        \"political\": \"\",\n" +
                "        \"post\": \"0390101121-143709\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"postName\": \"研发工程师\",\n" +
                "        \"salt\": \"FMpn2qY3ys\",\n" +
                "        \"secondPhone\": \"\",\n" +
                "        \"sequence\": \"product\",\n" +
                "        \"sex\": 1,\n" +
                "        \"sn\": \"琚\",\n" +
                "        \"sortOrder\": 1000,\n" +
                "        \"standardPost\": \"0390101121-340000\",\n" +
                "        \"standardPostName\": \"外部员工职位\",\n" +
                "        \"status\": 0,\n" +
                "        \"superviseDept\": \"\",\n" +
                "        \"supervisor\": \"leiyu\",\n" +
                "        \"supporterCorpContact\": \"雷宇\",\n" +
                "        \"supporterCorpName\": \"\",\n" +
                "        \"supporterDept\": \"视频物联网产品部\",\n" +
                "        \"systemRoles\": [],\n" +
                "        \"userCode\": \"tp202405140014\",\n" +
                "        \"userOldCode\": \"tp202405140014\",\n" +
                "        \"userType\": 2,\n" +
                "        \"username\": \"tp_jupengfei\",\n" +
                "        \"workOrgCode\": \"\",\n" +
                "        \"workPhone\": \"\"\n" +
                "    }\n" +
                "]";
        return JSONUtil.parseArray(user);
    }

    @UrlFree
    @GetMapping("/orgs")
    public JSONArray orgs() {
        String org = "[\n" +
                "    {\n" +
                "        \"id\": 3380,\n" +
                "        \"createdDt\": \"2024-04-09 11:16:38\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-05-08 09:44:18\",\n" +
                "        \"updatedId\": \"42\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423391\",\n" +
                "        \"orgOldCode\": \"00760023006000010000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\硬件产品团队\\\\硬件组\",\n" +
                "        \"parentId\": 3379,\n" +
                "        \"parentIds\": \"/0/1/485/3379\",\n" +
                "        \"parentCode\": \"390423390\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"硬件组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"wangkaiyuan\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"zuoqinglin\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 100,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\硬件产品团队\\\\硬件组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3381,\n" +
                "        \"createdDt\": \"2024-04-09 11:16:42\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-05-08 09:45:13\",\n" +
                "        \"updatedId\": \"42\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423392\",\n" +
                "        \"orgOldCode\": \"00760023004200040000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\云视讯产品团队\\\\云视讯方案组\",\n" +
                "        \"parentId\": 2030,\n" +
                "        \"parentIds\": \"/0/1/485/2030\",\n" +
                "        \"parentCode\": \"390422033\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"云视讯方案组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"dengyi\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"wanjunzhi\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 200,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\云视讯产品团队\\\\云视讯方案组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3382,\n" +
                "        \"createdDt\": \"2024-04-09 11:16:48\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-05-08 09:46:42\",\n" +
                "        \"updatedId\": \"42\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423393\",\n" +
                "        \"orgOldCode\": \"00760023004300030000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\和对讲产品团队\\\\和对讲方案组\",\n" +
                "        \"parentId\": 2031,\n" +
                "        \"parentIds\": \"/0/1/485/2031\",\n" +
                "        \"parentCode\": \"390422034\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"和对讲方案组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"zuoqinglin\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"zuoqinglin\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 199,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\和对讲产品团队\\\\和对讲方案组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3383,\n" +
                "        \"createdDt\": \"2024-04-09 11:16:52\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-04-10 11:10:17\",\n" +
                "        \"updatedId\": \"42\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423394\",\n" +
                "        \"orgOldCode\": \"00760023006100000000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"0390405475\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\",\n" +
                "        \"parentId\": 485,\n" +
                "        \"parentIds\": \"/0/1/485\",\n" +
                "        \"parentCode\": \"0390101121\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"千里眼SaaS产品团队\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 3,\n" +
                "        \"orgManager\": \"liaochuan\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"liaochuan\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 4,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3384,\n" +
                "        \"createdDt\": \"2024-04-09 11:16:57\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-04-09 11:16:57\",\n" +
                "        \"updatedId\": \"61\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423395\",\n" +
                "        \"orgOldCode\": \"00760023006100010000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\\\\党政农商方案组\",\n" +
                "        \"parentId\": 3383,\n" +
                "        \"parentIds\": \"/0/1/485/3383\",\n" +
                "        \"parentCode\": \"390423394\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"党政农商方案组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"wangwei\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"liaochuan\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 100,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\\\\党政农商方案组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3385,\n" +
                "        \"createdDt\": \"2024-04-09 11:17:01\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-04-09 11:17:01\",\n" +
                "        \"updatedId\": \"61\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423396\",\n" +
                "        \"orgOldCode\": \"00760023006100020000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\\\\综治交通方案组\",\n" +
                "        \"parentId\": 3383,\n" +
                "        \"parentIds\": \"/0/1/485/3383\",\n" +
                "        \"parentCode\": \"390423394\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"综治交通方案组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"wangwei\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"liaochuan\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 200,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\\\\综治交通方案组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3386,\n" +
                "        \"createdDt\": \"2024-04-09 11:17:05\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-04-09 11:17:05\",\n" +
                "        \"updatedId\": \"61\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423397\",\n" +
                "        \"orgOldCode\": \"00760023006100030000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\\\\教育金融方案组\",\n" +
                "        \"parentId\": 3383,\n" +
                "        \"parentIds\": \"/0/1/485/3383\",\n" +
                "        \"parentCode\": \"390423394\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"教育金融方案组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"wangwei\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"liaochuan\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 300,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼SaaS产品团队\\\\教育金融方案组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3387,\n" +
                "        \"createdDt\": \"2024-04-09 11:17:09\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-04-09 11:17:09\",\n" +
                "        \"updatedId\": \"61\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423398\",\n" +
                "        \"orgOldCode\": \"00760023005500070000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼APaaS产品团队\\\\产品运营组\",\n" +
                "        \"parentId\": 2339,\n" +
                "        \"parentIds\": \"/0/1/485/2339\",\n" +
                "        \"parentCode\": \"390422354\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"产品运营组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"yujie\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"liaochuan\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 200,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\千里眼APaaS产品团队\\\\产品运营组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3388,\n" +
                "        \"createdDt\": \"2024-04-09 11:17:13\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-05-08 09:47:28\",\n" +
                "        \"updatedId\": \"42\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423399\",\n" +
                "        \"orgOldCode\": \"00760023005700030000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\视联网团队\\\\CV大模型组\",\n" +
                "        \"parentId\": 2341,\n" +
                "        \"parentIds\": \"/0/1/485/2341\",\n" +
                "        \"parentCode\": \"390422356\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"CV大模型组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"liuyuhan\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"xuliang\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 100,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\视联网团队\\\\CV大模型组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3389,\n" +
                "        \"createdDt\": \"2024-04-09 11:17:18\",\n" +
                "        \"createdId\": \"61\",\n" +
                "        \"updatedDt\": \"2024-05-08 09:48:35\",\n" +
                "        \"updatedId\": \"42\",\n" +
                "        \"status\": 0,\n" +
                "        \"orgCode\": \"390423400\",\n" +
                "        \"orgOldCode\": \"00760023005400040000\",\n" +
                "        \"ihrOrgCode\": \"\",\n" +
                "        \"parentIhrOrgCode\": \"\",\n" +
                "        \"fullName\": \"物联网公司\\\\视频物联网产品部\\\\品质售后团队\\\\品质组\",\n" +
                "        \"parentId\": 2338,\n" +
                "        \"parentIds\": \"/0/1/485/2338\",\n" +
                "        \"parentCode\": \"390422353\",\n" +
                "        \"erpCode\": \"\",\n" +
                "        \"shortName\": \"品质组\",\n" +
                "        \"orgType\": 3,\n" +
                "        \"description\": \"\",\n" +
                "        \"orgLevel\": 4,\n" +
                "        \"orgManager\": \"wangyu3\",\n" +
                "        \"viceManager\": \"\",\n" +
                "        \"supervisor\": \"\",\n" +
                "        \"managerOrgId\": \"\",\n" +
                "        \"address\": \"\",\n" +
                "        \"postCode\": \"\",\n" +
                "        \"area\": \"重庆\",\n" +
                "        \"leadership\": \"liaochuan\",\n" +
                "        \"phone\": \"\",\n" +
                "        \"faxNo\": \"\",\n" +
                "        \"displayOrder\": 200,\n" +
                "        \"operationResponsbity\": \"\",\n" +
                "        \"startTime\": \"\",\n" +
                "        \"endTime\": \"\",\n" +
                "        \"director\": \"\",\n" +
                "        \"expand1\": \"\",\n" +
                "        \"expand2\": \"\",\n" +
                "        \"expand3\": \"\",\n" +
                "        \"expand4\": \"\",\n" +
                "        \"expand5\": \"\",\n" +
                "        \"admin\": \"\",\n" +
                "        \"displayName\": \"物联网公司\\\\视频物联网产品部\\\\品质售后团队\\\\品质组\",\n" +
                "        \"operationMode\": \"\"\n" +
                "    }\n" +
                "]";
        return JSONUtil.parseArray(org);
    }

}

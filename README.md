<p align="center">
  <img width="128px" src=".github/logo.png" />
</p>
<h1 align="center">林语</h1>
<p align="center">该代码仓库为 林语 服务端相关代码</p>
<p align="center">客户端地址：https://github.com/DWHengr/linyu-client</p>

# 简绍

`林语`是基于`tauri`开发的桌面聊天软件，前端框架使用`react`，后端框架使用`springboot`进行开发
。其中使用http和websocket实现消息发送和推送，使用webrtc实现音视频聊天。

# 目前功能

好友相关、朋友圈、音视频聊天、语音消息、文本消息、文件消息、图片消息、截图、等。

# 项目相关

## java环境

开发使用的java版本为`1.8`，springboot版本为`2.6.7`。

## mysql安装

mysql使用的版本为`8.0.37`，执行项目下`linyu.sql`内容来初始化数据库。

## minio安装

minio使用的版本为`RELEASE.2024-05-10T01-41-38Z`。需要再minio中新建`file`和`linyu`这两个桶。其中需要把`linyu`
这个桶需设置为永久免密下载，可以`minio客户端`进行设置。\
`mc config host add minio [http://127.0.0.1:9000(替换成对应的minio)] [minio(替换成对应的Access Key)] [minio123(替换成对应的Secret Key)] --api S3v4`\
`mc policy set download  minio/linyu`\
`Access Key`可以在minio页面进行设置。

## redis安装

Redis使用的版本为`5.0.14.1`

## faster-whisper-server安装

#### 1.下载模型（whisper国内模型无法自动下载，需要手动下载）

https://huggingface.co/Systran/faster-whisper-small

#### 2.上传到服务（将模型上传到服务器，目录可以任意）

#### 3.使用docker部署（挂在刚才上传的模型目录）

docker run -d --publish 8000:8000 --volume /model:/model fedirz/faster-whisper-server:latest-cpu

#### 4.api调用示例

curl http://127.0.0.1:8000/v1/audio/transcriptions -F "file=@1.wav" -F"model=/model/faster-whisper-small/"

## 配置修改

修改`application.yml`内，mysql、minio、redis、faster-whisper-server相关地址。

# 项目截图

## 登录

![1](https://github.com/user-attachments/assets/0014be78-8270-4c2e-b3c0-df5318a8e454)

## 聊天

![2](https://github.com/user-attachments/assets/03115e1b-1090-4b73-a6d2-39abdf9cb03c)

## 好友

![3](https://github.com/user-attachments/assets/a4d475ce-a0e8-47d8-a07d-a3e038eb99c3)

## 朋友圈

![4](https://github.com/user-attachments/assets/981d7fad-897e-4973-b51f-31a8106d71cb)

## 通知

![5](https://github.com/user-attachments/assets/b29b4e27-a69a-44d5-a9cf-fe8c1eca69f3)

## 系统设置

![6](https://github.com/user-attachments/assets/aa132533-9b0c-4710-b646-e2a911b7eb25)

# 结语

![admire](https://github.com/user-attachments/assets/7e77ac87-a913-4f87-8783-a1d313297a05)

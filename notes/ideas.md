# Noisy - 叽喳

[toc]

## 简述

一个以聊天室为主的线上聊天网站。用户可以注册登入，查找聊天室，即时聊天。

## 功能

- 用户
  - 注册、登录、登出
  - 个人信息的修改
- 聊天室
  - 查找聊天室
  - 创建聊天室
  - 加入、退出聊天室
  - 聊天室信息

## 规范约定

见 `api.txt` 与 `model.txt`。

## 实现约定

- 用户登录的会话用 Cookie 实现: 登陆时添加 `(user_token, session_token)` 和 `(user_uuid, uuid)` 两个 Cookie. `session_token` 为生成的一个标识码(可以使用 uuid), 它用于标识一次有效的登录会话; `uuid` 为用户 UUID。通过检测这个 `session_token` 是否在数据库中且未到达销毁日期来判断登录是否有效
- 即时聊天实现使用 WebSocket, 其它用朴素的 HTTP 协议(RESTful API)
  - 用户登出发生在客户端, 不对应 RESTful API 端口
  - 用户在线状态以 WebSocket 连接状态来判定

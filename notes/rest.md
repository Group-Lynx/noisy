# RESTful APIs

[toc]

## 用户认证

### `POST /auth/login`

执行用户登录操作，登录成功返回 200，登录失败（不存在用户或密码错误）返回 401。

**Request**

```
{
    "name": string,
    "pswd": string,
}
```

**Response**

设置 Cookie `user_token: session_token` 和 `user_uuid: user_uuid`。

```
// 200 Ok
{
    "uuid": string,
    "name": string,
}
// 401 Unauthorized
{
    "err": string,
    "msg": string,
}
```

### `POST /auth/signup`

执行用户注册操作，注册成功返回 201。理论上而言不应当失败，但如果有客户端错误返回 4XX。

**Request**

```
{
    "name": string,
    "pswd": string,
}
```

**Response**

设置 Cookie `user_token: session_token` 和 `user_uuid: user_uuid`。

```
// 201 Created
{
    "uuid": string,
    "name": string,
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

## 用户管理

### `GET /user/[user_uuid]`

依照用户 UUID 获取用户信息，成功返回 200，失败返回 4XX（依据情况返回不同错误码）。

**Request**

**Response**

```
// 200 Ok
{
    "uuid": string,
    "name": string,
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `PATCH /user/[user_uuid]`

该请求应当检查 Cookie。更改 UUID 对应的用户的信息，更改成功返回 200，更新失败返回 4XX。

**Request**

```
{
    "new_name": string | null,  // `string | null` 代表该类型为 string 且可能为 null
    "new_pswd": string | null,
    "auth_pswd": string,
}
```

**Response**

```
// 200 Ok
{
    "uuid": string,
    "name": string,
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `DELETE /user/[user_uuid]`

该请求应当检查 Cookie。删除 UUID 对应的用户，删除成功返回 204，失败返回 4XX 。

**Request**

```
{
    "auth_pswd": string,
}
```

**Response**

```
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

## 聊天室管理

### `GET /chat`

该请求应当检查 Cookie。检索用户加入的所有聊天室，成功返回 200，失败返回 4XX。没加入聊天室的用户不应当视为操作失败，而应当返回空数组。

**Request**

**Response**

```
// 200 Ok
{
	"chatrooms": [  // 方括号表示数组
        "chat_detail": {
        	"chat": {
                "uuid": string,
                "name": string,
        	},
            "unread": number,
        },
        ...
    ],
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `GET /chat/[chat_uuid]`

检索 UUID 所对应的聊天室的信息，成功返回 200，失败返回 4XX。

**Request**

**Response**

```
// 200 Ok
{
    "name": string,
    "owner_name": string,
    "members": [
    	"user": {
    		"uuid": string,
    		"name": string,
    	},
    	...
    ],
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `PATCH /chat/[chat_id]`

该请求应当检查 Cookie。聊天室所有者更新 UUID 所对应的聊天室的信息，成功返回 200，失败返回 4XX。

**Request**

```
{
	"new_name": string | null,
}
```

**Response**

```
// 200 Ok
{
    "uuid": string,
	"name": string,
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `DELETE /chat/[chat_id]`

该请求应当检查 Cookie。聊天室所有者删除 UUID 所对应的聊天室，成功返回 204，失败返回 4XX。

**Request**

**Response**

```
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `POST /chat/[chat_id]/join`

该请求应当检查 Cookie。将发送请求的用户加入 UUID 所对应的聊天室，成功返回 204，失败返回 4XX。

**Request**

**Response**

```
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `POST /chat/[chat_id]/leave`

该请求应当检查 Cookie。让发送请求的用户离开 UUID 所对应的聊天室，成功返回 204，失败返回 4XX。

**Request**

**Response**

```
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `GET /chat/[chat_id]/msg`

该请求应当检查 Cookie。检索聊天室的所有消息，成功返回 200，失败返回 4XX。

**Request**

**Response**

```
// 200 Ok
{
    "messages": [
        "message": {
            "sender": string,   // 发送者的用户名
            "content": string,  // 消息的内容
            "sent_date": {      // 发送消息的时间
                "year": number,
                "month": number,
                "day": number,
                "hour": number,
                "minute": number,
            },
        },
        ...
    ],
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

## 搜索处理

### `GET /search/user?{query}`

查询用户，成功返回 200。理论上而言不应当失败，但如果有客户端错误返回 4XX。

- ` ` 不返回任何用户
- `name=[pattern]` 搜索所有用户名匹配 `[pattern]` 的用户

**Request**

**Response**

```
// 200 Ok
{
	"users": [
        "user": {
            "uuid": string,
            "name": string,
        },
        ...
    ],
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```

### `GET /search/chat?{query}`

查询聊天室，成功返回 200。理论上而言不应当失败，但如果有客户端错误返回 4XX。

- ` ` 返回所有聊天室
- `name=[pattern]` 搜索所有聊天室名匹配 `[pattern]` 的聊天室

```
// 200 Ok
{
	"chatrooms": [
        "chat": {
            "uuid": string,
            "name": string,
        },
        ...
    ],
}
// 4XX Client Error
{
    "err": string,
    "msg": string,
}
```


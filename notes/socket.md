# WebSocket

## `/socket`

服务器的 WebSocket 端点。具体实现中：
- 客户端向 `/listen/[chat_name]` 通道发送消息。
- 客户端从 `/broadcast/[chat_name]` 通道接收消息。
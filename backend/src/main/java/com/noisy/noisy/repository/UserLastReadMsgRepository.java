package com.noisy.noisy.repository;

import com.noisy.noisy.model.UserLastReadMsg;
import com.noisy.noisy.model.UserLastReadMsgKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLastReadMsgRepository extends JpaRepository<UserLastReadMsg, UserLastReadMsgKey> {
//    Integer findByUser_nameAndChatroom_name(String user_name,String chat_name);
//    //这个语句的作用是根据user——name和chat——name在lastreadmsg里查询id信息的最大值，最后返回这个最大值，所以你需要用到聚合函数
    @Query("SELECT MAX(u.last_read_msg_id) FROM UserLastReadMsg u WHERE u.user_name = :user_name AND u.chatroom_name = :chat_name")
    Integer findByUser_nameAndChatroom_name(@Param("user_name")String user_name, @Param("chat_name")String chat_name);

    // TODO: 初始化用户未读消息（用户进入一个聊天室时需要新建一个记录）
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_last_read_msg (user_name, chatroom_name, last_read_msg_id) VALUES (:user_name, :chat_name, 0)", nativeQuery = true)
    void initUserUnreadMsg(@Param("user_name") String user_name, @Param("chat_name") String chat_name);

    // TODO: 更改用户所读的最后一条消息
    @Modifying
    @Query("UPDATE UserLastReadMsg u SET u.last_read_msg_id = :last_read_msg_id WHERE u.user_name = :user_name AND u.chatroom_name = :chat_name")
    @Transactional
    void updateLastReadMsg(@Param("user_name") String user_name, @Param("chat_name") String chat_name, @Param("last_read_msg_id") Integer last_read_msg_id);
}

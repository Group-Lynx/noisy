package com.noisy.noisy.repository;

import com.noisy.noisy.Response.MessageResponse;
import com.noisy.noisy.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
//    List<Message> findByChat_name(String chat_name);
//    //这个语句的作用是根据聊天室名称返回该聊天室所有的信息
    @Query("SELECT new com.noisy.noisy.Response.MessageResponse(m) FROM Message m WHERE m.chat_name = :chat_name")
    List<MessageResponse> findByChat_name(@Param("chat_name")String chat_name);


//    int findByChat_nameAndUser_nameAndAndId(String chat_name,String user_name,Integer id);
//    //这个语句的作用是根据chat——name和user——name查询id值大于 传进去这个id参数的 行数
    @Query("SELECT COUNT(m) FROM Message m WHERE m.chat_name = :chat_name AND m.user_name = :user_name AND m.id > :id")
    int findByChat_nameAndUser_nameAndId(@Param("chat_name")String chat_name, @Param("user_name")String user_name, @Param("id")Integer id);
    //------------你这个方法名有两个连着的And,没给你改---------------
}

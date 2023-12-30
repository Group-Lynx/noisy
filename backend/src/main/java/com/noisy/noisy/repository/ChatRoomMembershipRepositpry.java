package com.noisy.noisy.repository;

import com.noisy.noisy.Response.ChatRoomMemberResponse;
import com.noisy.noisy.Response.UserResponse;
import com.noisy.noisy.model.ChatMembership;
import com.noisy.noisy.model.ChatMembershipkey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomMembershipRepositpry  extends JpaRepository<ChatMembership, ChatMembershipkey> {

//   void deleteByChat_nameAnUser_name(String chat_name,String user_name);
//   //这个语句的作用是根据给定的user——name和chat——name删除chatmembership表对应的数据
   @Transactional
   @Modifying
   @Query("DELETE FROM ChatMembership c WHERE c.chat_name = :chat_name AND c.user_name = :user_name")
   void deleteByChat_nameAnUser_name(@Param("chat_name")String chat_name, @Param("user_name")String user_name);


//   List<ChatRoomMemberResponse> findByUser_name(String user_name);
//   //这个语句的作用是查询姓名为user——name的用户加入的所有聊天室名称，最后返回一个链表（关于返回值你不需要担心，直接在前面标注就可以）
   @Query("SELECT new com.noisy.noisy.Response.ChatRoomMemberResponse(c.chat_name) FROM ChatMembership c WHERE c.user_name = :user_name")
   List<ChatRoomMemberResponse> findByUser_name(@Param("user_name")String user_name);


//   List<UserResponse> findByChat_name(String chat_name);
//   //这个语句的作用是查询名称为chat——name的聊天室所包含的所有用户名，最后返回一个链表
   @Query("SELECT new com.noisy.noisy.Response.UserResponse(c.user_name) FROM ChatMembership c WHERE c.chat_name = :chat_name")
   List<UserResponse> findByChat_name(@Param("chat_name")String chat_name);

}

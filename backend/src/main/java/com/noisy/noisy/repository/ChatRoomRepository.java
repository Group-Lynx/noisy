package com.noisy.noisy.repository;

import com.noisy.noisy.Response.ChatRoomResponse;
import com.noisy.noisy.model.ChatRoom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {

//    String findByName(String chat_name); //找聊天室名字
//    //这个语句要实现的是根据聊天室名称查找聊天室名称，最后返回一个String类型 （作用是为了查询是否存在这个聊天室）
    @Query("SELECT c.name FROM ChatRoom c WHERE c.name = :chat_name")
    String findByName(@Param("chat_name") String chat_name);


//    String findByOwner_name(String owner_name);//找用户名
//    //这个的定义有错误，查询的传参应该是chat——name
//    //这个语句要实现的是根据chat——name查询对应的owner——name最后返回
//------这个地方我直接方法名也改了，然后我看你在ChatRoomController第52行有一次使用这个方法，我直接将那个调用也改成findByChat_name了
    @Query("SELECT c.owner_name FROM ChatRoom c WHERE c.name = :chat_name")
    String findByChat_name(@Param("chat_name") String chat_name);


//    void updateByName(String old_name,String new_name);
//    //这个语句要实现的更新聊天室名称，old——name是救名字，new——name是新名字
    @Modifying
    @Query("UPDATE ChatRoom c SET c.name = :new_name WHERE c.name = :old_name")
    void updateByName(@Param("old_name")String old_name, @Param("new_name") String new_name);


//    void deleteChatRoomByName(String chat_name);
//    //这个语句要实现的是删除名称为chat——name的记录
    @Transactional
    @Modifying
    @Query("DELETE FROM ChatRoom c WHERE c.name = :chat_name")
    void deleteChatRoomByName(@Param("chat_name")String chat_name);


//    List<ChatRoomResponse> findByNameIsLike(String chat_name);
//    //这个语句要实现的是模糊查询，注意这个chat——name是已经适配好的，只需要写一个模糊查询语句就行
    @Query("SELECT new com.noisy.noisy.Response.ChatRoomResponse(c.name) FROM ChatRoom c WHERE c.name LIKE :chat_name")
    List<ChatRoomResponse> findByNameIsLike(@Param("chat_name")String chat_name);
}

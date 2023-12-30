package com.noisy.noisy.repository;

import com.noisy.noisy.Response.UserResponse;
import com.noisy.noisy.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    //String findByName(String user_name);
//    //这个查询语句要实现的事根据用户姓名在User那个表中查询用户姓名，最后返回一个Stirng类型的用户名（本质作用是为了确定该用户在不在用户列表中）
    @Query("SELECT u.name FROM User u WHERE u.name = :user_name")
    String findByName(@Param("user_name")String user_name);


//    String findByNameAndPasswd(String user_name,String pswd);
//    //这个查询语句要实现的是根据用户名和密码在用户列表中查询是否存在对应的用户，最后返回一个Stirng类型的用户名（本质作用是为了检验该用户的密码输入是否正确）
    @Query("SELECT u.name FROM User u WHERE u.name = :user_name AND u.pswd = :pswd")
    String findByNameAndPasswd(@Param("user_name")String user_name,@Param("pswd")String pswd);


//    void updateByName(String old_name,String new_name,String new_pswd);
//    //这个语句要实现的是根据将用户名为old——name的用户的用户名改为new——name 密码改为new——pswd
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = :new_name, u.pswd = :new_pswd WHERE u.name = :old_name")
    void updateByName(@Param("old_name")String old_name, @Param("new_name")String new_name, @Param("new_pswd")String new_pswd);


//    void deletebyname(String name);
//    //这个语句要实现的是根据name删除在user这个表中对应的记录
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.name = :name")
    void deletebyname(@Param("name")String name);


//    List<UserResponse> findByNameIsLike(String user_name);
//    //这个语句要实现的是模糊查询，注意这个chat——name是已经适配好的，只需要写一个模糊查询语句就行
//    @Query("SELECT u.name FROM User u WHERE u.name LIKE %:user_name%")
//    List<UserResponse> findByNameIsLike(@Param("user_name")String user_name);
    @Query("SELECT new com.noisy.noisy.Response.UserResponse(u.name) FROM User u WHERE u.name LIKE :user_name")
    List<UserResponse> findByNameIsLike(@Param("user_name") String user_name);

}

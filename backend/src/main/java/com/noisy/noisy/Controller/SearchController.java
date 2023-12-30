package com.noisy.noisy.Controller;

import com.noisy.noisy.Response.ChatRoomResponse;
import com.noisy.noisy.Response.ChatRoomResponseList;
import com.noisy.noisy.Response.UserResponse;
import com.noisy.noisy.Response.UserResponseList;
import com.noisy.noisy.repository.ChatRoomRepository;
import com.noisy.noisy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    UserRepository userRepository;
    ChatRoomRepository chatRoomRepository;
    @Autowired
    public SearchController(UserRepository userRepository ,ChatRoomRepository chatRoomRepository){
        this.chatRoomRepository=chatRoomRepository;
        this.userRepository=userRepository;
    }
    @GetMapping("/user")
    public ResponseEntity<?> searchuser(@RequestParam String name){
        List<UserResponse> users;
        if (name.isEmpty()) {
            users = new ArrayList<>();
        } else {
            users = userRepository.findByNameIsLike("%" + name + "%");
        }
        System.err.println(users);
        return ResponseEntity.ok(new UserResponseList(users));
    }

    @GetMapping("/chat")
    public ResponseEntity<?> searchchat(
            @RequestParam(required=false, defaultValue="") String name
    ){
        List<ChatRoomResponse> chatrooms=chatRoomRepository.findByNameIsLike("%"+name+"%");
        return ResponseEntity.ok(new ChatRoomResponseList(chatrooms));
    }

}

package com.noisy.noisy.Controller;

import com.noisy.noisy.Response.*;
import com.noisy.noisy.model.ChatMembership;
import com.noisy.noisy.model.ChatRoom;
import com.noisy.noisy.repository.*;
import com.noisy.noisy.request.ChatRoomPatchRequest;
import com.noisy.noisy.request.CreateChatRoomRequset;
import com.noisy.noisy.utils.ErrorResponse;
import com.noisy.noisy.utils.Variefy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    MessageRepository messageRepository;
    ChatRoomRepository chatRoomRepository;
    ChatRoomMembershipRepositpry chatRoomMembershipRepositpry;
    UserLastReadMsgRepository userLastReadMsgRepository;
    UserRepository userRepository;
    LoginRepository loginRepository;
    @Autowired
    public ChatRoomController(
            ChatRoomRepository chatRoomRepository,
            ChatRoomMembershipRepositpry chatRoomMembershipRepositpry,
            MessageRepository messageRepository,
            UserLastReadMsgRepository userLastReadMsgRepository,
            UserRepository userRepository,
            LoginRepository loginRepository
    ){
        this.chatRoomMembershipRepositpry=chatRoomMembershipRepositpry;
        this.chatRoomRepository=chatRoomRepository;
        this.messageRepository=messageRepository;
        this.userLastReadMsgRepository = userLastReadMsgRepository;
        this.userRepository=userRepository;
        this.loginRepository=loginRepository;
    }

    // LYN-REMOVE: @GetMapping("/")
    @GetMapping("")
    public ResponseEntity<?> getallmembership(HttpServletRequest request){
        System.err.println("@GetMapping(\"\")");
        Cookie[] cookies = request.getCookies();
        String user_name=null;
        // LYN-ADD-BEGIN
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "未登录用户",
                            "没有启用 Cookie 或并未登录"
                    ));
        }
        // LYN-ADD-END

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                user_name=cookie.getValue();
            }
        }
        List<ChatRoomMemberResponse> memberlist=chatRoomMembershipRepositpry.findByUser_name(user_name);
        List<Chat_detail> chatrooms=new ArrayList<Chat_detail>();
        for(ChatRoomMemberResponse chatRoomMemberResponse:memberlist){
            String chat_name=chatRoomMemberResponse.getName();
            Integer id= userLastReadMsgRepository.findByUser_nameAndChatroom_name(user_name,chat_name);
            if(id==null) id=0;
            Integer unread=messageRepository.findByChat_nameAndUser_nameAndId(chat_name,user_name,id);//Todo
            chatrooms.add(new Chat_detail(chatRoomMemberResponse,unread));
        }
        return ResponseEntity.ok(new ChatRooms(chatrooms));
    }

    @GetMapping("/{chat_name}")
    public ResponseEntity<?> getchatroomdetail(@PathVariable String chat_name){
        String owner_name=chatRoomRepository.findByChat_name(chat_name);
        if(owner_name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Not Found","ChatRoom Not Found"));
        }else{
            List<UserResponse> user_detail=chatRoomMembershipRepositpry.findByChat_name(chat_name);
            return ResponseEntity.ok(new ChatRoomDetailResponse(chat_name,owner_name,user_detail));
        }
    }

    @PatchMapping("/{chat_name}")
    public ResponseEntity<?> patchchatroombyname(@PathVariable String chat_name, @RequestBody ChatRoomPatchRequest chatRoomPatchRequest,HttpServletRequest request){
        String name=chatRoomRepository.findByName(chat_name);
        if(name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("聊天室未找到","该聊天室不存在"));
        }else{
            //Todo
            if(Variefy.variefy(request,userRepository,loginRepository)){
                chatRoomRepository.updateByName(chat_name,chatRoomPatchRequest.getChat_name());
                return ResponseEntity.ok(new ChatRoomPatchResponse(chatRoomPatchRequest.getChat_name()));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            }
        }
    }

    @DeleteMapping("/{chat_name}")
    public ResponseEntity<?> deletechatroom(@PathVariable String chat_name,HttpServletRequest request){
        String name=chatRoomRepository.findByName(chat_name);
        if(name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("聊天室未找到","该聊天室不存在"));
        }else{
            //Todo
            if(Variefy.variefy(request,userRepository,loginRepository)){
                chatRoomRepository.deleteChatRoomByName(name);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            }
        }

    }

    @PostMapping("/{chat_name}/join")
    public ResponseEntity<?> joinbychatname(@PathVariable String chat_name,HttpServletRequest request){
        String name=chatRoomRepository.findByName(chat_name);
        if(name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("聊天室未找到","该聊天室不存在"));
        }else{
            //Todo
            if(Variefy.variefy(request,userRepository,loginRepository)){
                Cookie[] cookies = request.getCookies();
                String user_name=null;
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("user")){
                        user_name=cookie.getValue();
                    }
                }

                ChatMembership chatMembership=new ChatMembership();
                chatMembership.setChat_name(chat_name);
                chatMembership.setUser_name(user_name);
                chatRoomMembershipRepositpry.save(chatMembership);
                // LYN-TODO: Runtime Error, database not found
                userLastReadMsgRepository.initUserUnreadMsg(user_name, chat_name);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            }
            //Todo 完善代码

        }
    }

    @PostMapping({"/{chat_name}/leave"})
    public ResponseEntity<?> deletebyname(@PathVariable String chat_name, HttpServletRequest request){
        String name=chatRoomRepository.findByName(chat_name);
        if(name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("聊天室未找到","该聊天室不存在"));
        }else{
            if(Variefy.variefy(request,userRepository,loginRepository)){
                //Todo
                Cookie[] cookies = request.getCookies();
                String user_name=null;
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("user")){
                        user_name=cookie.getValue();
                    }
                }
                chatRoomMembershipRepositpry.deleteByChat_nameAnUser_name(chat_name,user_name);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            }
        }
    }

    @GetMapping("/{chat_name}/msg") //可以加一个exception
    public ResponseEntity<?> getallmsg(@PathVariable String chat_name){
        String name=chatRoomRepository.findByName(chat_name);
        if(name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("聊天室未找到","该聊天室不存在"));
        }else{
            List<MessageResponse> messageResponses= messageRepository.findByChat_name(name);
            return ResponseEntity.ok(new MessageResponseList(messageResponses));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createchatroom(@RequestBody CreateChatRoomRequset createChatRoomRequset, HttpServletRequest request){
        String chat_name=chatRoomRepository.findByChat_name(createChatRoomRequset.getName());
        System.err.println("chat name: " + createChatRoomRequset.getName());
        if(chat_name==null){
            if(Variefy.variefy(request,userRepository,loginRepository)){
                Cookie[] cookies = request.getCookies();
                String user_name=null;
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("user")){
                        user_name=cookie.getValue();
                    }
                }
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setOwner_name(user_name);
                chatRoom.setName(createChatRoomRequset.getName());
                chatRoomRepository.save(chatRoom);

                ChatMembership chatMembership=new ChatMembership();
                chatMembership.setUser_name(user_name);
                chatMembership.setChat_name(createChatRoomRequset.getName());
                chatRoomMembershipRepositpry.save(chatMembership);

                userLastReadMsgRepository.initUserUnreadMsg(user_name, createChatRoomRequset.getName());
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("name conflict","chatroom already exists"));
        }
    }
}

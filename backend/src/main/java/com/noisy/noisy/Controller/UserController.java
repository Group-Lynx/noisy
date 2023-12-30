package com.noisy.noisy.Controller;

import com.noisy.noisy.Response.UserResponse;
import com.noisy.noisy.repository.LoginRepository;
import com.noisy.noisy.repository.UserRepository;
import com.noisy.noisy.request.DeleteRequest;
import com.noisy.noisy.request.PatchRequest;
import com.noisy.noisy.utils.ErrorResponse;
import com.noisy.noisy.utils.Variefy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    UserRepository userRepository;
    LoginRepository loginRepository;
    @Autowired
    public UserController(UserRepository userRepository,LoginRepository loginRepository){
        this.userRepository=userRepository;
        this.loginRepository=loginRepository;
    }
    @GetMapping("/{user_name}")
    public ResponseEntity<?> getuserbyname(@PathVariable String user_name){
        String name=userRepository.findByName(user_name);
        if(name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body( new ErrorResponse("User Not Found","Invalid user"));
        }else{
            return ResponseEntity.ok(new UserResponse(name));
        }
    }
    @PatchMapping("/{user_name}")
    public ResponseEntity<?> patchuser(@PathVariable String user_name, @RequestBody PatchRequest patchRequest,HttpServletRequest request){
        //Todo
        if(Variefy.variefy(request,userRepository,loginRepository)) {
            String name = userRepository.findByName(user_name);
            if (name == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            } else {
                userRepository.updateByName(name, patchRequest.getNew_name(), patchRequest.getNew_pswd());
                return ResponseEntity.ok(new UserResponse(name));
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User Not Found", "Invalid user"));
        }
    }
    @DeleteMapping("/{user_name}")
    public ResponseEntity<?> deleteuser(@PathVariable String user_name, @RequestBody DeleteRequest deleteRequest, HttpServletRequest request){
        //Todo
        if(Variefy.variefy(request,userRepository,loginRepository)) {
            String name = userRepository.findByName(user_name);
            if (name == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User Not Found", "Invalid user"));
            } else {
                name = userRepository.findByNameAndPasswd(name, deleteRequest.getAuth_pswd());
                if (name == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("Fail", "Wrong Password"));
                } else {
                    userRepository.deletebyname(name);
                    return ResponseEntity.ok().build();
                }
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User Not Found", "Invalid user"));
        }
    }
}

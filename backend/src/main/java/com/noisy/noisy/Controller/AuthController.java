package com.noisy.noisy.Controller;

import com.noisy.noisy.Response.AuthResp;
import com.noisy.noisy.Response.UserResponse;
import com.noisy.noisy.model.LoginSession;
import com.noisy.noisy.model.User;
import com.noisy.noisy.repository.LoginRepository;
import com.noisy.noisy.repository.UserRepository;
import com.noisy.noisy.request.LoginRequest;
import com.noisy.noisy.request.SignupRequest;
import com.noisy.noisy.utils.ErrorResponse;
import com.noisy.noisy.utils.GlobalVars;
import com.noisy.noisy.utils.Variefy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    @Autowired
    public AuthController(UserRepository userRepository,LoginRepository loginRepository){
        this.loginRepository=loginRepository;
        this.userRepository=userRepository;
    }

    // LYN-EDIT: @GetMapping("/")
    @GetMapping("")
    public ResponseEntity<?> index(HttpServletRequest request){
        System.err.println("@GetMapping(\"\")");
        try{
            //Todo
            if(Variefy.variefy(request,userRepository,loginRepository)){
                Cookie[] cookies = request.getCookies();
                String user_name=null;
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("user")){
                        user_name=cookie.getValue();
                    }
                }
                return ResponseEntity.ok(new UserResponse(user_name));
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse(
                                "Unauthorized",
                                "Invalid user"
                        ));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(
                            "Unhandled Server Error",
                            "Unhandled Server Error"
                    ));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        System.err.println("@PostMapping(\"/signup\")");

        //Todo 待完善
        String user_name=userRepository.findByName(signupRequest.getName());
        if(user_name==null){
            userRepository.save( new User(signupRequest.getName(),signupRequest.getPswd()));
            // LYN-EDIT: return ResponseEntity.ok().build();
            return ResponseEntity.ok(new UserResponse(signupRequest.getName()));
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(
                            "User Already Exists",
                            "User with name " + signupRequest.getName() + " already exists"
                    ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        System.err.println("@PostMapping(\"/login\")");
        //Todo 待完善
        String user_name=userRepository.findByName(loginRequest.getName());
        if(user_name==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "Invalid Credentials",
                            "User with name " + loginRequest.getName() + " does not exist"
                    ));
        }
        user_name=userRepository.findByNameAndPasswd(loginRequest.getName(), loginRequest.getPswd());
        if(user_name==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "Invalid Credentials",
                            "Incorrect password"
                    ));
        }
        LoginSession loginSession =new LoginSession();
        loginSession.setUser_name(loginRequest.getName());
        loginSession.setToken(UUID.randomUUID());
        loginSession.setValid_until(LocalDateTime.now().plusMinutes(GlobalVars.SESSION_VALID_MINUTE));
        loginRepository.save(loginSession);

        Cookie tokencookie = new Cookie("token",loginSession.getToken().toString());
        tokencookie.setPath("/");
        tokencookie.setMaxAge(GlobalVars.SESSION_VALID_MINUTE * 60);
        response.addCookie(tokencookie);

        Cookie userCookie = new Cookie("user", loginSession.getUser_name());
        userCookie.setPath("/");
        userCookie.setMaxAge(GlobalVars.SESSION_VALID_MINUTE * 60);
        response.addCookie(userCookie);

        // LYN-EDIT: return ResponseEntity.ok().build();
        return ResponseEntity.ok(new AuthResp(
                loginSession.getUser_name(),
                loginSession.getToken().toString()
        ));
    }
}

package com.noisy.noisy.utils;

import com.noisy.noisy.model.LoginSession;
import com.noisy.noisy.repository.LoginRepository;
import com.noisy.noisy.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class Variefy {
    public static boolean variefy(HttpServletRequest request, UserRepository userRepository, LoginRepository loginRepository){
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return false;
        }
        String token=null;
        String user_name=null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                token=cookie.getValue();
            }else if(cookie.getName().equals("user")){
                user_name=cookie.getValue();
            }
        }
        if(token==null||user_name==null){
            return false;
        }
        LoginSession loginSession = loginRepository.findByToken(UUID.fromString(token));
        if(loginSession==null){
            return  false;
        }
        if(!loginSession.getUser_name().equals(user_name)){
            return false;
        }
        if(loginSession.getValid_until().isBefore(LocalDateTime.now())){
            return false;
        }
        String user=userRepository.findByName(user_name);
        if(user==null){
            return false;
        }
        return true;
    }
}

package com.huya.community.controller;

import com.huya.community.mapper.UserMapper;
import com.huya.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niuting
 */

@Controller
public class IndexController {
    @Autowired
    UserMapper      userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies)
        {
            if("token".equals(cookie.getName()))
            {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null)
                {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        return "index" ;
    }
}

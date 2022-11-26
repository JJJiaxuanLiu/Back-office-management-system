package com.jiaxuan.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jiaxuan.entity.User;
import com.jiaxuan.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    @Resource
    private UserService userService;

    private static UserService staticUserService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    /**
     * 生成token
     * @param userId
     * @param username
     * @return
     */
    public static String generateToken(String userId, String username){
        return JWT.create().withAudience(userId) //将userid保存到token里，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后token过期
                .sign(Algorithm.HMAC256(username)); //将username作为token的密钥
    }


    /**
     * 根据当前登录信息（token）获取用户信息
     * @return User
     */
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StringUtils.isNotEmpty(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(userId);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}

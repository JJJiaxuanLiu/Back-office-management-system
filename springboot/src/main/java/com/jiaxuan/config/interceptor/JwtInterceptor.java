package com.jiaxuan.config.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jiaxuan.commom.Constants;
import com.jiaxuan.entity.User;
import com.jiaxuan.exception.ServiceException;
import com.jiaxuan.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if(StringUtils.isEmpty(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //获取token中的userid
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }
        //通过token中的userid查询数据库
        User user = userService.getById(userId);
        if(user == null){
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }
        //用户username加密验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUsername())).build();
        try {
            jwtVerifier.verify(token); //验证token
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }
        return true;
    }

}

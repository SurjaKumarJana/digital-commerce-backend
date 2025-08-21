package org.surja.digital_commerce_backend.service;


import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public String generateToken(String email, Integer time){

        String token = UUID.randomUUID().toString();
        String key =  "Token : " +token;

        redisTemplate.opsForValue().set(key,email,time, TimeUnit.MINUTES);
        return token;
    }

    public String validateToken(String token){

        String key = "Token : " +token;
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }

    public void invalidateToken(String token) {
        String key = "Token : " + token;
        redisTemplate.delete(key);
    }
}

// 阶段 A 的实现：内存登记本。
// 第 7 步之后改用 JwtService 验签，本类已停用，保留供对照学习。

package com.example.jwt_lab;

import java.util.HashMap;
import java.util.Map;

public class TokenStore {

    private static final Map<String, Entry> TOKENS = new HashMap<>();
    private static final long TTL = 5*60*1000;
    
    public static class Entry{
        public String username;
        public long issueAt;
    }

    public static void save(String token, String username){
        Entry e = new Entry();
        e.username = username;
        e.issueAt = System.currentTimeMillis();
        TOKENS.put(token, e);
    }
    
    public static String getUsername(String token){
        Entry e = TOKENS.get(token);
        if(e == null){
            return null;
        }
        return e.username;
    }

    public static boolean isExpired(String token){
        Entry e = TOKENS.get(token);
        if(e == null) {return true;}
        return System.currentTimeMillis() - e.issueAt > TTL;
    }

}



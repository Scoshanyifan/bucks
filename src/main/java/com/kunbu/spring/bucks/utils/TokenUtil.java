package com.kunbu.spring.bucks.utils;

import com.kunbu.spring.bucks.constant.other.TokenTypeEnum;

public class TokenUtil {

    public static boolean checkAdmin(String token) {
        if (token != null && token.length() > 0) {
            if (token.indexOf(TokenTypeEnum.ADMIN.name()) > 0) {
                return true;
            }
        }
        return false;
    }

}

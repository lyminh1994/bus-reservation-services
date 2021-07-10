package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CryptoUtils {

    public String encrypt(String password) {
        return password;
    }

    public boolean check(String checkPassword, String realPassword) {
        return checkPassword.equals(realPassword);
    }

}

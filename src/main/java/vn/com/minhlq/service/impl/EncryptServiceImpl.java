package vn.com.minhlq.service.impl;

import org.springframework.stereotype.Service;

import vn.com.minhlq.service.EncryptService;

@Service
public class EncryptServiceImpl implements EncryptService {

    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public boolean check(String checkPassword, String realPassword) {
        return checkPassword.equals(realPassword);
    }

}

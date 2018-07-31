package com.todaysoft.ghealth.service.impl;

public interface PasswordEncoder
{
    String encode(CharSequence rawPassword);
    
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
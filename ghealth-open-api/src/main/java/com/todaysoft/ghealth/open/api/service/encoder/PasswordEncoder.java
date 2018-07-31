package com.todaysoft.ghealth.open.api.service.encoder;

public interface PasswordEncoder
{
    String encode(CharSequence rawPassword);
    
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
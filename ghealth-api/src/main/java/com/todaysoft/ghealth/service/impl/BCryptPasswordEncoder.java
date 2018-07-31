package com.todaysoft.ghealth.service.impl;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder
{
    private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    
    private final Log logger = LogFactory.getLog(getClass());
    
    private final int strength;
    
    private final SecureRandom random;
    
    public BCryptPasswordEncoder()
    {
        this(-1);
    }
    
    public BCryptPasswordEncoder(int strength)
    {
        this(strength, null);
    }
    
    public BCryptPasswordEncoder(int strength, SecureRandom random)
    {
        this.strength = strength;
        this.random = random;
    }
    
    @Override
    public String encode(CharSequence rawPassword)
    {
        String salt;
        if (strength > 0)
        {
            if (random != null)
            {
                salt = BCrypt.gensalt(strength, random);
            }
            else
            {
                salt = BCrypt.gensalt(strength);
            }
        }
        else
        {
            salt = BCrypt.gensalt();
        }
        return BCrypt.hashpw(rawPassword.toString(), salt);
    }
    
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword)
    {
        if (encodedPassword == null || encodedPassword.length() == 0)
        {
            logger.warn("Empty encoded password");
            return false;
        }
        
        if (!BCRYPT_PATTERN.matcher(encodedPassword).matches())
        {
            logger.warn("Encoded password does not look like BCrypt");
            return false;
        }
        
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
}

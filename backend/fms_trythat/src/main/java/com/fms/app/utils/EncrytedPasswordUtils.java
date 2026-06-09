package com.fms.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncrytedPasswordUtils {
	
	private static final Logger logger = LogManager.getLogger(EncrytedPasswordUtils.class);

	// Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        try {
        	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(password);
        }catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EncrytedPasswordUtils.encrytePassword: "+stacktrace,e);
			return null;
		}
    }

	public static boolean isPasswordMatches(String oldPwd, String newPwd) {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.matches(newPwd, oldPwd);
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EncrytedPasswordUtils.encrytePassword: "+stacktrace,e);
			return false;
		}
	}


}

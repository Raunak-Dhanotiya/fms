package com.fms.app.payload;

import javax.validation.constraints.*;

public class SignupRequest {

	 @NotBlank
	    @Size(min = 3, max = 20)
	    private String username;
	 
	    
	    @NotBlank
	    @Size(min = 6, max = 40)
	    private String password;
	  
	    public String getUsername() {
	        return username;
	    }
	 
	    public void setUsername(String username) {
	        this.username = username;
	    }
	 
}

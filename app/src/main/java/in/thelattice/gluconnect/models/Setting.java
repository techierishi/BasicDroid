package in.thelattice.gluconnect.models;

import java.io.Serializable;

public class Setting implements Serializable{
	
	String username=null;
	String password=null;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

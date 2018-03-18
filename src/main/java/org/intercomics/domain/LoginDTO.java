package org.intercomics.domain;

import org.intercomics.user.ProfileVO;

public class LoginDTO {

	private String username;
	private String password;
	private String client_id;
	//삭제 해도 무방 
	private String client_secret;
	private ProfileVO profile;

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

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public ProfileVO getProfile() {
		return profile;
	}

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "LoginDTO [username=" + username + ", password=" + password + ", client_id=" + client_id
				+ ", client_secret=" + client_secret + ", profile=" + profile + "]";
	}

}

package org.intercomics.domain;

import org.springframework.stereotype.Component;

@Component("OauthAccessTokenVO")
public class OauthAccessTokenVO {

	private String token_id;
	private String access_token;
	private String userId;
	private String client_id;
	private String authentication;
	private String refresh_token;

	public String getToken_id() {
		return token_id;
	}

	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	@Override
	public String toString() {
		return "OauthAccessTokenVO [token_id=" + token_id + ", access_token=" + access_token + ", userId=" + userId
				+ ", client_id=" + client_id + ", authentication=" + authentication + ", refresh_token=" + refresh_token
				+ "]";
	}

}

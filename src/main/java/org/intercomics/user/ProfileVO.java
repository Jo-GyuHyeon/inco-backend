package org.intercomics.user;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component("ProfileVO")
public class ProfileVO {

	private String id;
	private String name;
	private String mac_token;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String access_token;
	private String email;
	private String picture_url;
	private Boolean isPush;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac_token() {
		return mac_token;
	}

	public void setMac_token(String mac_token) {
		this.mac_token = mac_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public Boolean getIsPush() {
		return isPush;
	}

	public void setIsPush(Boolean isPush) {
		this.isPush = isPush;
	}

	@Override
	public String toString() {
		return "ProfileVO [id=" + id + ", name=" + name + ", mac_token=" + mac_token + ", access_token=" + access_token
				+ ", email=" + email + ", picture_url=" + picture_url + ", isPush=" + isPush + "]";
	}

}

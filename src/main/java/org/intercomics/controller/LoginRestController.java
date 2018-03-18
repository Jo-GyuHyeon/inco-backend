package org.intercomics.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.intercomics.domain.LoginDTO;
import org.intercomics.domain.OauthAccessTokenVO;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.WebtoonVO;
import org.intercomics.mapper.ProfileRepository;
import org.intercomics.mapper.TokenRepository;
import org.intercomics.mapper.UserRepository;
import org.intercomics.service.UserService;
import org.intercomics.user.UserVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Resource(name = "OauthAccessTokenVO")
	private OauthAccessTokenVO tokenVO;
	
	private Date date = new Date();

	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@RequestMapping(value = "/jwt/token", method = RequestMethod.POST)
	public ResponseEntity<String> takeToekn(@RequestBody LoginDTO dto) {
		
		System.out.println("/jwt/token -POST  : " + sdf.format(date));

		ResponseEntity<String> entity = null;

		try {
			UserVO vo = userService.getUser(dto.getUsername());
			if (vo == null) {
				userService.create(dto.getUsername(), dto.getPassword());
				tokenVO.setClient_id(dto.getClient_id());
				tokenVO.setAccess_token(dto.getProfile().getAccess_token());
				tokenVO.setUserId(dto.getUsername());
				System.out.println(tokenVO.toString());
				tokenRepository.newToken(tokenVO);
				System.out.println(dto.getProfile().toString());
				profileRepository.newProfile(dto.getProfile());
			}

			// String client_id = "facebook_id";

			CloseableHttpClient client = HttpClients.createDefault();

			String url = "http://facebook_id:facebook_secret@127.0.0.1:8080/oauth/token";
			HttpPost post = new HttpPost(url);
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("grant_type", "password"));
			params.add(new BasicNameValuePair("client_id", "facebook_id"));
			params.add(new BasicNameValuePair("scope", "read"));
			params.add(new BasicNameValuePair("username", dto.getUsername()));
			params.add(new BasicNameValuePair("password", dto.getPassword()));
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(post);

			entity = new ResponseEntity<String>(EntityUtils.toString(response.getEntity()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 회원삭제
	@RequestMapping(value = "/jwt/token", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(HttpServletRequest request) {

		System.out.println("/jwt/token -DELETE  : " + sdf.format(date));

		ResponseEntity<String> entity = null;
		try {
			userService.delete((getUserId(request.getHeader("x-token"))));

			entity = new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 로그아웃
	@RequestMapping(value = "/jwt/token", method = RequestMethod.PATCH)
	public ResponseEntity<String> logout(HttpServletRequest request) {

		System.out.println("/jwt/token -PATCH  : " + sdf.format(date));

		ResponseEntity<String> entity = null;
		try {
			profileRepository.logout((getUserId(request.getHeader("x-token"))));

			entity = new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	/*
	 * @RequestMapping(value = "/jwt/token2", method = RequestMethod.POST) public
	 * ResponseEntity<String> takeToekn2(@RequestBody LoginDTO dto) {
	 * 
	 * ResponseEntity<String> entity = null;
	 * 
	 * try { UserVO vo = userService.getUser(dto.getUsername()); if(vo==null) {
	 * userService.create(dto.getUsername(), dto.getPassword()); } // String
	 * username = "hhw"; // String password = "1234"; String client_id =
	 * "facebook_id"; // String client_secret = "facebook_secret"; String url =
	 * "http://facebook_id:facebook_secret@127.0.0.1:8080/oauth/token"; String[]
	 * command = { "curl", url, "-d", "grant_type=password", "-d", "client_id=" +
	 * client_id, "-d", "scope=read", "-d", "username=" + dto.getUsername(), "-d",
	 * "password=" + dto.getPassword() }; ProcessBuilder process = new
	 * ProcessBuilder(command); Process p; p = process.start(); BufferedReader
	 * reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	 * StringBuilder builder = new StringBuilder(); String line = null; while ((line
	 * = reader.readLine()) != null) { builder.append(line);
	 * builder.append(System.getProperty("line.separator")); } entity = new
	 * ResponseEntity<String>(builder.toString(), HttpStatus.OK); } catch (Exception
	 * e) { e.printStackTrace(); entity = new
	 * ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST); } return
	 * entity; }
	 */

	private String getUserId(String token) {
		Jwt jwt = JwtHelper.decode(token);
		JSONObject obj = new JSONObject(jwt.getClaims());
		System.out.println("token : " + token);
		System.out.println("user_name : " + obj.get("user_name").toString());
		return obj.get("user_name").toString();
	}
}

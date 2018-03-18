package org.intercomics.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.intercomics.domain.LoginDTO;
import org.intercomics.domain.NotificationDTO;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {

	@RequestMapping(value = "/getHeader", method = RequestMethod.GET)
	public ResponseEntity<String> testHeader2(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<String> entity = null;
		try {
			System.out.println("getHeader : " + request.getHeader("x-token"));

			Jwt jwt = JwtHelper.decode(request.getHeader("x-token"));

			JSONObject obj = new JSONObject(jwt.getClaims());
			// System.out.println(jwt.getClaims());
			System.out.println(obj.get("user_name"));

			entity = new ResponseEntity<>(request.getHeader("x-token"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@RequestMapping(value = "/testLogin", method = RequestMethod.POST)
	public void test2(@RequestBody LoginDTO dto) {

		System.out.println(dto.toString());

	}

	@RequestMapping(value = "/testNotification", method = RequestMethod.POST)
	public ResponseEntity<String> takeToekn() {

		System.out.println("/notification -POST");

		ResponseEntity<String> entity = null;

		try {

			CloseableHttpClient client = HttpClients.createDefault();

			String url = "https://exp.host/--/api/v2/push/send";
			HttpPost post = new HttpPost(url);
			Header[] headers = { new BasicHeader("accept", "application/json"),
					new BasicHeader("accept-encoding", "gzip,deflate"),
					new BasicHeader("Content-type", "application/json") };

			post.setHeaders(headers);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("to", "ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]"));
			params.add(new BasicNameValuePair("title", "Hi dayea"));
			params.add(new BasicNameValuePair("body", "I LOVE YOU. YOU ARE MY daoughter"));
			// params.add(new BasicNameValuePair("ttl", Integer.toString(500)));
			// params.add(new BasicNameValuePair("expiration", Integer.toString(1000)));
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(post);
			entity = new ResponseEntity<String>(response.toString(), HttpStatus.OK);
			// entity = new
			// ResponseEntity<String>(EntityUtils.toString(response.getEntity()),
			// HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

}

package org.intercomics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.intercomics.domain.NotificationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationTest {

	@Test
	public void contextLoads() {
	}

//	@Test
	public void testSubmit() throws Exception {

		String url = "https://exp.host/--/api/v2/push/send";

		URI uri = URI.create(url);

		NotificationDTO dto = new NotificationDTO();
		dto.setTo("ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]");
		dto.setTitle("test");
		dto.setBody("sd");
		RestTemplate restTemplate = new RestTemplate();
		String responseString = restTemplate.postForObject(uri, dto, String.class);
		System.out.println(responseString);

	}

	// @Test
	public void takeToekn() {

		String url = "https://exp.host/--/api/v2/push/send";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("to", "ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]");
		map.add("title", "Hi dayea ");
		map.add("body", "I LOVE YOU. YOU ARE MY daoughter");
		// map.add("ttl", Integer.toString(500));
		// map.add("expiration", Integer.toString(1000));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		System.out.println(response.toString());

	}

	// @Test
	public void test() throws IOException {

		URL url = new URL("https://exp.host/--/api/v2/push/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST"); // 보내는 타입
		conn.setRequestProperty("content-type", "application/json");

		// 데이터
		String param = "{\"to\": \"ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]\", \"title\" : \"title\", \"body\" : \"ddd\"}";
		// 전송
		OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
		try {
			osw.write(param);
			osw.flush();
			// 응답
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			// 닫기
			osw.close();
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void whenPostJsonUsingHttpClient_thenCorrect() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("https://exp.host/--/api/v2/push/send");

		String json = "{\"to\": \"ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]\", \"title\" : \"title\", \"body\" : \"ddd\"}";

		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode());
		client.close();
	}

	// @Test
	public void test3() throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();

		String url = "https://exp.host/--/api/v2/push/send";
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-type", "application/json");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("to", "ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]"));
		params.add(new BasicNameValuePair("title", "Hi dayea"));
		params.add(new BasicNameValuePair("body", "I LOVE YOU. YOU ARE MY daoughter"));
		// params.add(new BasicNameValuePair("ttl", Integer.toString(500)));
		// params.add(new BasicNameValuePair("expiration", Integer.toString(1000)));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = client.execute(post);
		System.out.println(response.toString());
	}

	// @Test
	public void test2() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();

		String url = "https://exp.host/--/api/v2/push/send\"";
		HttpPost post = new HttpPost(url);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		StringEntity data = new StringEntity(
				"to=ExponentPushToken[KMPgfLAOcOPYIloek9_l8G]&title=Hi dayea&body=I LOVE YOU. YOU ARE MY daoughter");
		post.setEntity(data);

		HttpResponse response = client.execute(post);

		System.out.println(response.toString());
	}

}

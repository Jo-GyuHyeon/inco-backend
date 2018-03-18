package org.intercomics.controller;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.intercomics.domain.BoardVO;
import org.intercomics.domain.NotificationDTO;
import org.intercomics.mapper.BoardRepository;
import org.intercomics.mapper.ProfileRepository;
import org.intercomics.mapper.SubscribeRepository;
import org.intercomics.mapper.WebtoonRepository;
import org.intercomics.user.ProfileVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NotificationController {

	private static final String TITLE = "Intercomics";
	private static final String URL = "https://exp.host/--/api/v2/push/send";

	private Date date = new Date();
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private SubscribeRepository subscribeRepository;

	@Autowired
	private WebtoonRepository webtoonRepository;

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	@Resource(name = "NotificationDTO")
	private NotificationDTO notificationDTO;

	@Resource(name = "ProfileVO")
	private ProfileVO profileVO;

	@Value("${notification.key-value}")
	private String token;

	//push 전송 
	@RequestMapping(value = "/notification", method = RequestMethod.POST)
	public ResponseEntity<String> notification(@RequestParam("webtoonId") String webtoonId,
			HttpServletRequest request) {

		System.out.println("/notification -POST  : " + sdf.format(date));
		ResponseEntity<String> entity = null;
		try {

			if (!token.equals(request.getHeader("token"))) {
				throw new Exception();
			}

			URI uri = URI.create(URL);
			String webtoonName = webtoonRepository.getWebtoonName(webtoonId);

			List<String> macList = subscribeRepository.pushList(webtoonId);
			List<NotificationDTO> pushList = new ArrayList<NotificationDTO>();

			for (int i = 0; i < macList.size(); i++) {
				NotificationDTO notificationDTO = new NotificationDTO(macList.get(i), TITLE, webtoonName);
				pushList.add(notificationDTO);
			}
			String response = restTemplate.postForObject(uri, pushList, String.class);

			entity = new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	//push 수신 설정 
	@RequestMapping(value = "/notification/{isPush}", method = RequestMethod.PATCH)
	public ResponseEntity<String> isNotification(HttpServletRequest request, @PathVariable("isPush") boolean isPush) {

		System.out.println("/notification -PATCH  : " + sdf.format(date));

		ResponseEntity<String> entity = null;
		String userId = getUserId(request.getHeader("x-token"));

		try {
			profileVO.setId(userId);
			profileVO.setIsPush(isPush);
			profileRepository.isPush(profileVO);

			entity = new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 공지사항 최신순으로 요청
	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public ResponseEntity<List<BoardVO>> boardScroll() {

		System.out.println("/notification -GET : " + sdf.format(date));

		ResponseEntity<List<BoardVO>> entity = null;
		try {
			entity = new ResponseEntity<List<BoardVO>>(boardRepository.getList(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	private String getUserId(String token) {
		Jwt jwt = JwtHelper.decode(token);
		JSONObject obj = new JSONObject(jwt.getClaims());
		System.out.println("token : " + token);
		System.out.println("user_name : " + obj.get("user_name").toString());
		return obj.get("user_name").toString();
	}

}

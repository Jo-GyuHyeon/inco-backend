package org.intercomics.mapper;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.MywebtoonVO;
import org.intercomics.domain.SearchCriteria;
import org.intercomics.domain.WebtoonVO;

public interface SubscribeRepository {

	@Insert("insert into mywebtoon (userId,webtoonId) values(#{userId},#{webtoonId})")
	public void addSubscribe(MywebtoonVO vo) throws Exception;

	@Delete("delete from mywebtoon where userId = #{userId} and webtoonId = #{webtoonId}")
	public void removeSubscribe(MywebtoonVO vo) throws Exception;

	@Select("select * from mywebtoon where userId = #{userId} and webtoonId = #{webtoonId}")
	public WebtoonVO isSubscibe(MywebtoonVO vo) throws Exception;

	public List<WebtoonVO> listSubscibe(SearchCriteria cri) throws Exception;

	public int subscibeLastNum(SearchCriteria cri) throws Exception;

	public Timestamp timestampSubscibe(SearchCriteria cri) throws Exception;

	@Select("select mac_token from mywebtoon inner join profile on mywebtoon.userId = profile.id where isPush = true and webtoonId = #{webtoonId}")
	public List<String> pushList(@Param("webtoonId") String webtoonId) throws Exception;

}

package org.intercomics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.intercomics.domain.LoginDTO;
import org.intercomics.user.ProfileVO;

public interface ProfileRepository {

	@Insert("insert into profile (id,name, mac_token, picture_url, email) values (#{id},#{name}, #{mac_token}, #{picture_url},#{email})")
	public void newProfile(ProfileVO vo) throws Exception;

	@Select("select * from profile where id = #{id}")
	public LoginDTO findByUserId(String userId) throws Exception;

	@Delete("delete from profile where id = #{id}")
	public void delete(String userId) throws Exception;

	@Select("select * from profile ")
	public List<LoginDTO> findAll() throws Exception;
	
	@Update("update profile set mac_token = null where id = #{id}")
	public void logout(@Param("id") String id) throws Exception;
	
	@Update("update profile set isPush = #{isPush} where id = #{id}")
	public void isPush(ProfileVO vo) throws Exception;

}

package org.intercomics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.RankVO;

public interface RankRepository {

	@Select("select * from rank natural join webtoon natural join day natural join genre natural join platform natural join writer where type = #{type} order by currentRank asc ")
	public List<RankVO> getList(@Param("type") String type) throws Exception;

}

package org.intercomics.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.intercomics.domain.BoardVO;

public interface BoardRepository {

	@Select("select * from board order by regdate desc")
	public List<BoardVO> getList() throws Exception;

}

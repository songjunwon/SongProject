package com.finalproj.missingitnow.admin.notice.model.dao;

import java.util.List;

import com.finalproj.missingitnow.admin.notice.model.dto.NoticeDTO;
import com.finalproj.missingitnow.common.search.MoreSearchDTO;

public interface NoticeMapper {

	int deleteNTC(String no);

	int updateNTC(NoticeDTO ntcDTO);

	NoticeDTO selectDetail(String no);

	int increamentBoardCount(String no);

	int insertNTC(NoticeDTO ntcDTO);

	List<NoticeDTO> selectList(MoreSearchDTO search);

	int selectTotalCount();

}

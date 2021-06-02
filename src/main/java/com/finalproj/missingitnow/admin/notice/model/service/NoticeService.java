package com.finalproj.missingitnow.admin.notice.model.service;

import java.util.List;

import com.finalproj.missingitnow.admin.notice.model.dto.NoticeDTO;
import com.finalproj.missingitnow.common.search.MoreSearchDTO;

public interface NoticeService {

	int deleteNTC(int no);

	NoticeDTO updateNTC(NoticeDTO ntcDTO);

	NoticeDTO selectDetail(int no);

	boolean insertNTC(NoticeDTO ntcDTO);

	List<NoticeDTO> selectList(MoreSearchDTO search);

	int selectTotalCount();

}

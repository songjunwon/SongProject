package com.finalproj.missingitnow.admin.qna.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproj.missingitnow.admin.qna.model.dto.QNADTO;
import com.finalproj.missingitnow.admin.qna.model.service.QNAService;
import com.finalproj.missingitnow.common.page.PageInfoDTO;
import com.finalproj.missingitnow.common.page.Pagenation;
import com.finalproj.missingitnow.common.search.DetailSearchDTO;
import com.finalproj.missingitnow.common.search.SearchDTO;

@Controller
@RequestMapping("/admin/qna")
public class AdminQNAController {
	
	private final QNAService qnaService;
	
	@Autowired
	public AdminQNAController(QNAService qnaService) {
		
		this.qnaService = qnaService;
		
	}

	@GetMapping("/list")
	public String adminQNAListPage(HttpServletRequest request, Model model) {
		
		String condition = "writer";
		String value = "";
		
		String currentPage = request.getParameter("currentPage");
		
		int pageNo = 1;
		System.out.println(currentPage);
		if (currentPage != null && !"".equals(currentPage)) {

			pageNo = Integer.valueOf(currentPage);

			if (pageNo < 1) {

				pageNo = 1;

			}
		}

		int totalCount = qnaService.selectTotalCount();
		
		System.out.println(totalCount);
		
		int limit = 10;

		int buttonAmount = 10;
		PageInfoDTO pageInfo = Pagenation.getPageInfo(pageNo, totalCount, limit, buttonAmount);
		
		SearchDTO search = new SearchDTO(pageInfo, condition, value);

		List<QNADTO> boardList = qnaService.selectList(search);
		
		System.out.println(boardList);
		System.out.println(search);
		
		model.addAttribute("qnaList", boardList);
		model.addAttribute("search", search);
		
		return "admin/qna/adminQNAList";
		
	}
	
	@GetMapping("/regist")
	public String adminQNARegistPage() {
		
		return "admin/qna/adminQNARegist";
		
	}
	
	@PostMapping("/regist")
	public String adminQNARegist(@ModelAttribute QNADTO qnaDTO, HttpServletRequest request, RedirectAttributes rttr) {
		
		System.out.println(qnaDTO);
		HttpSession session = request.getSession();
		
		if(qnaService.insertQNA(qnaDTO)) {
			
			rttr.addFlashAttribute("message", "qna작성에 성공하셨습니다!");
			
		} else {
				
			rttr.addFlashAttribute("message", "qna작성에 실패하셨습니다!");
			
		}
		
		return "redirect:list";
		
	}
	
	@GetMapping("/manage")
	public String adminQNAManagePage(HttpServletRequest request, Model model) {
		
		java.sql.Date searchWriteDateStart = null;
		java.sql.Date searchWriteDateEnd = null;
		String largeSearchCondition = request.getParameter("largeSearchCondition");
		String smallSearchCondition = request.getParameter("smallSearchCondition");
		String searchValue = request.getParameter("searchValue");
		String currentPage = request.getParameter("currentPage");
		if(request.getParameter("searchWriteDateStart") != null && !"".equals(request.getParameter("searchWriteDateStart"))) {
			searchWriteDateStart = java.sql.Date.valueOf(request.getParameter("searchWriteDateStart"));
			System.out.println(searchWriteDateStart);
		}
		if(request.getParameter("searchWriteDateEnd") != null && !"".equals(request.getParameter("searchWriteDateEnd"))) {
			searchWriteDateEnd = java.sql.Date.valueOf(request.getParameter("searchWriteDateEnd"));
			System.out.println(searchWriteDateEnd);
		}
		if(searchValue == null || "".equals(searchValue)) {
			searchValue = "";
		}
		if(smallSearchCondition == null || "".equals(smallSearchCondition)) {
			smallSearchCondition = "id";
			System.out.println(smallSearchCondition);
		}
		if(largeSearchCondition == null || "".equals(largeSearchCondition)) {
			largeSearchCondition = "all";
			System.out.println(smallSearchCondition);
		}
		int pageNo = 1;
		if (currentPage != null && !"".equals(currentPage)) {

			pageNo = Integer.valueOf(currentPage);
			
			System.out.println("pageNo : " + pageNo);

			if (pageNo < 1) {

				pageNo = 1;

			}
		}
		DetailSearchDTO search = new DetailSearchDTO(null, searchWriteDateStart, searchWriteDateEnd, largeSearchCondition, smallSearchCondition, searchValue);

		int totalCount = qnaService.selectSearchTotalCount(search);
		
		System.out.println("totalCount : " + totalCount);
		
		int limit = 10;

		int buttonAmount = 10;
		PageInfoDTO pageInfo = Pagenation.getPageInfo(pageNo, totalCount, limit, buttonAmount);
		
		search.setPageInfo(pageInfo);
		
		List<QNADTO> boardList = qnaService.selectAllList(search);
		
		System.out.println(boardList);
		System.out.println(search);
		
		model.addAttribute("qnaList", boardList);
		model.addAttribute("search", search);
		
		return "admin/qna/adminQNAManage";
		
	}
	
	@GetMapping("/response")
	public String adminQNAResponsePage(@ModelAttribute QNADTO qnaDTO, Model model) {
		
		QNADTO qna = qnaService.selectDetail(Integer.valueOf(qnaDTO.getNo()));
		
		model.addAttribute("qna", qna);
		
		return "admin/qna/adminQNAResponse";
		
	}
	
	@PostMapping("/response")
	public String adminQNAResponse(@ModelAttribute QNADTO qnaDTO, RedirectAttributes rttr) {
		
		String message = null;
		
		System.out.println(qnaDTO);
		
		if(qnaService.updateResponse(qnaDTO) == 2) {
			
			message = "답변 작성에 성공하셨습니다.";
			
		} else {
			
			message = "답변 작성에 실패하셨습니다.";
			
		}
		
		rttr.addFlashAttribute("message", message);
		
		return "redirect:manage";
		
	}
	
	@GetMapping("/detail")
	public String adminQNADetail(HttpServletRequest request, Model model) {
		
		System.out.println(request.getParameter("qnaNo"));
		
		int no = Integer.valueOf(request.getParameter("qnaNo"));
		
		QNADTO qna = qnaService.selectDetail(no);
		
		model.addAttribute("qna", qna);
		
		return "admin/qna/adminQNADetail";
		
	}
	
	@GetMapping("/update")
	public String adminQNAUpdatePage(Model model, @ModelAttribute QNADTO qnaDTO) {
		
		model.addAttribute("qna", qnaDTO);
		
		return "admin/qna/adminQNAUpdateForm";
		
	}
	
	@PostMapping("/update")
	public String adminQNAUpdate(@ModelAttribute QNADTO qnaDTO, RedirectAttributes rttr) {
		QNADTO qna = null;
		qna = qnaService.updateQNA(qnaDTO);
		
		if(qna != null) {
		
			rttr.addAttribute("qnaNo", qna.getNo());
			rttr.addFlashAttribute("message", "Q&A 수정에 성공하셨습니다.");
		
		} else {
			
			rttr.addAttribute("qnaNo", qnaDTO.getNo());
			rttr.addFlashAttribute("message", "Q&A 수정에 실패하셨습니다.");
			
		}
		return "redirect:detail";
		
	}
	
	@GetMapping("/delete")
	public String adminQNADelete(@ModelAttribute QNADTO qnaDTO, RedirectAttributes rttr) {
		
		int no = Integer.valueOf(qnaDTO.getNo());
		
		if(qnaService.deleteQNA(no) == 1) {
			
			rttr.addFlashAttribute("message", "Q&A 삭제에 성공하셨습니다");
			
		} else {
			
			rttr.addFlashAttribute("message", "Q&A 삭제에 실패하셨습니다.");
			
		}
		
		return "redirect:list";
		
	}
	
	@PostMapping("/fileUpload")
	public void maltiFileUploadAjax(
			@ModelAttribute List<MultipartFile> uploadFiles,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(uploadFiles);

		/* 경로 설정 */
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		System.out.println(root);
		
		String path = root + "\\uploadFiles";
		
		File mkdir = new File(path);
		
		/* 폴더 생성 */
		if(!mkdir.exists()) {
			mkdir.mkdirs();
		}
		
		List<Map<String, String>> files = new ArrayList<>();
		for(int i = 0 ; i < uploadFiles.size() ; i++) {
			/* 파일명 변경 처리 */
			String originName = uploadFiles.get(i).getOriginalFilename();
			String ext = originName.substring(originName.lastIndexOf("."));
			String rename = UUID.randomUUID().toString().replace("-", "") + ext;
			
			/* 파일에 관한 정보 추출 후 보관 */
			Map<String, String> file = new HashMap<>();
			file.put("originName", originName);
			file.put("rename", rename);
			file.put("path", path);
			
			files.add(file);
		} 
		
		List<Map<String, String>> urlList = new ArrayList<>();
		
		try {
			
			/* 파일을 저장한다 */
				
			for(int i  = 0; i < uploadFiles.size() ; i++) {
				Map<String, String> file = files.get(i);
				Map<String, String> urlMap = new HashMap<>();
				String url = path + "\\" + file.get("rename");
				urlMap.put("url", url);
				urlList.add(urlMap);
				uploadFiles.get(i).transferTo(new File(path + "\\" + file.get("rename")));
			}
			
		} catch (IllegalStateException | IOException e) {
			
			e.printStackTrace();
			
			/* 실패시 파일 삭제 */
			for(int i = 0 ; i < uploadFiles.size() ; i++) {
				
				Map<String, String> file = files.get(i);
				
				new File(path + "\\" + file.get("rename")).delete();
				
			}
			
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		PrintWriter out = response.getWriter();
		out.print(mapper.writeValueAsString(urlList));
		
		out.flush();
		out.close();
		
	}
	
}
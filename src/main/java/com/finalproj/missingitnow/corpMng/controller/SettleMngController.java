package com.finalproj.missingitnow.corpMng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.finalproj.missingitnow.corpMng.model.dto.CorpUserDTO;
import com.finalproj.missingitnow.corpMng.model.dto.SettleMngDepositDTO;
import com.finalproj.missingitnow.corpMng.model.dto.SettleMngPaymentDTO;
import com.finalproj.missingitnow.corpMng.model.dto.SettleMngSettlementDTO;
import com.finalproj.missingitnow.corpMng.model.service.SettleMngService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/settleMng")
public class SettleMngController {
	
	private final SettleMngService settleMngService;
	
	@Autowired
	public SettleMngController(SettleMngService settleMngservice) {
		this.settleMngService = settleMngservice;
	}
	
	// 전체 정산 내역 조회
	@GetMapping("/selectSettlementList")
	public String selectSettlementList(Model model) {
		
		List<SettleMngSettlementDTO> settlementList = settleMngService.selectSettlementList();
		
//		for(SettleMngSettlementDTO A : settlementList) {
//			System.out.println(A);
//		}
		
		model.addAttribute("settlementList", settlementList);
		
		return "/corpMng/settleMng-settlementNList";
	}
	
	// 정산 요청하기
	@PostMapping("/insertSettlement")
	public String insertSettlement(Model model, @ModelAttribute SettleMngSettlementDTO settleMngSettlement) {
		
		int insertSettlement = settleMngService.insertSettlement(settleMngSettlement);
		
		if(insertSettlement > 0) {
			model.addAttribute("successCode", "insertSettlement");
			return "/common/success";
		} else {
			model.addAttribute("failedCode", "insertSettlement");
			return "/common/failed";
		}
	}
	
	// 마지막 정산 날짜 확인 (ajax)
	@PostMapping(value="/chkLastEndDate", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String chkLastEndDate() {
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		SettleMngSettlementDTO chkLastEndDate = settleMngService.chkLastEndDate();
//		System.out.println(chkLastEndDate);
		
		return gson.toJson(chkLastEndDate);
	}
	
	// 기업회원별 예치금 조회
	@GetMapping("/selectDepositList")
	public String selectDepositList(Model model) {
		
		List<SettleMngDepositDTO> depositList = settleMngService.selectDepositList();
		
//		for(SettleMngDepositDTO a : depositList) {
//			System.out.println(a);
//		}
		
		model.addAttribute("depositList", depositList);
		
		return "/corpMng/settleMng-depositMng";
	}
	
	// 결제를 위한 기업정보 조회 (ajax)
	@PostMapping(value="/selectCorpUserForPay", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String selectCorpUserForPay() {
		
		Gson gson = new Gson();
		
		CorpUserDTO corpUserInfo = settleMngService.selectCorpUserForPay();
		System.out.println(corpUserInfo);
		
		return gson.toJson(corpUserInfo);
	}
	
	// 예치금 충전 결제내역 insert (ajax)
	@PostMapping(value="/insertPayment", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String insertPayment(@RequestParam(required = false) String imp_uid,
								@RequestParam(required = false) String merchant_uid,
								@RequestParam(required = false) String pay_method,	
								@RequestParam(required = false) String name,	
								@RequestParam(required = false) int paid_amount,	
								@RequestParam(required = false) String buyer_name,	
								@RequestParam(required = false) String buyer_email,	
								@RequestParam(required = false) String buyer_tel) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("imp_uid", imp_uid);
		params.put("pay_method", pay_method);
		params.put("merchant_uid", merchant_uid);
		params.put("name", name);
		params.put("paid_amount", paid_amount);
		params.put("buyer_name", buyer_name);
		params.put("buyer_email", buyer_email);
		params.put("buyer_tel", buyer_tel);
		
		Gson gson = new Gson();
		
		int insertPayment = settleMngService.insertPayment(params);
		
		// 결제내역 insert하면서 예치금 테이블에도 같이 '충전'내용 insert
		int insertDepositCharge = settleMngService.insertDepositCharge(params);
		
		return gson.toJson(insertPayment);
	}
	
	// 결제내역 조회
	@GetMapping("/paymentList")
	public String selectPaymentList(Model model) {
		
		List<SettleMngPaymentDTO> paymentList = settleMngService.selectPaymentList();
//		for(SettleMngPaymentDTO pay : paymentList) {
//			System.out.println("pay : " + pay);
//		}
		
		model.addAttribute("paymentList", paymentList);
		
		return "/corpMng/settleMng-paymentList";
	}
	
	
}



















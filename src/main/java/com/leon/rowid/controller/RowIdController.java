package com.leon.rowid.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leon.core.util.DateUtil;
import com.leon.rowid.service.RowIdService;


@Controller
@RequestMapping("/id-service")
public class RowIdController {

	private static final Logger logger = LogManager.getLogger(RowIdController.class);
	
	@Autowired
	RowIdService rowIdService;
	
	@RequestMapping(value = "/getrowid/{idType}" ,method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String getRowId(@PathVariable String idType, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String rowId = "";
		try {
			System.out.println("生成id："+DateUtil.getHHMMSS_SSS());
			rowId = rowIdService.getRowId(idType);
			System.out.println("生成id结束："+DateUtil.getHHMMSS_SSS());
			System.out.println("————————————————————————————————————————————");
		} catch (Exception e) {
			logger.error("获取row_id出现异常！");
			e.printStackTrace();
			return null;
		}
		return rowId;
	}
	
}

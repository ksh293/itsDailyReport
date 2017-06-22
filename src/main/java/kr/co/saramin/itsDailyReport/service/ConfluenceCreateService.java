package kr.co.saramin.itsDailyReport.service;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import kr.co.saramin.itsDailyReport.module.RestApiUrl;
import kr.co.saramin.itsDailyReport.module.RestHttpClient;

/** 
 * <pre>
 *  파 일 명 : ConfluenceCreateService.java
 *  설    명 : WIKI 페이지 생성 API를 호출하기 위한 Service
 *  작 성 자 : 김소현
 *  작 성 일 : 2017.06.22
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 */

public class ConfluenceCreateService {

	private RestHttpClient client = null;
	
	private final static String CONFLUENCE = "confluence";
	
	//pageId 의 하위공간에 페이지 생성 (16615297 : 기술연구소 회의자료(가로형) > 2017년 > 개발팀 일일 작업기록)
	String pageId = "16615297";
	
	/**
	 * 서비스 객체 생성 시 Confluence 호스트 인증 정보를 셋팅한 HTTP Client 객체 생성
	 * @throws Exception
	 */
	public ConfluenceCreateService() throws Exception{
		client = new RestHttpClient(CONFLUENCE);
	}
	 
	/**
	 * WIKI 페이지 생성 API를 호출할때 넘길 JSONObject 및 Client resource 세팅
	 * Http Client 호출
	 * @param htmlTemplate
	 * @param devTeam
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void postCreateNewPage(String htmlTemplate, String devTeam) throws UnsupportedEncodingException {
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("type", "page");
		
		jsonObject.put("title", this.getDevReportPageTitle(devTeam));
		
		JSONObject ancestorsOb = new JSONObject();
		ancestorsOb.put("id", this.pageId);
		JSONArray ancestorsArr = new JSONArray();
		ancestorsArr.add(ancestorsOb);
		jsonObject.put("ancestors", ancestorsArr);
		
		JSONObject spaceOb = new JSONObject();
		spaceOb.put("key", "TECHSTR");
		jsonObject.put("space", spaceOb);
		
		JSONObject storageOb = new JSONObject();
		//storageOb.put("value", StringEscapeUtils.escapeHtml(htmlTemplate));	
		storageOb.put("value", htmlTemplate);	
		storageOb.put("representation", "storage");
		JSONObject bodyOb = new JSONObject();
		bodyOb.put("storage", storageOb);
		jsonObject.put("body", bodyOb);
		
		
		String jsonOb = jsonObject.toString();
		
		URLEncoder.encode(jsonOb, "UTF-8");
		

		RestApiUrl restApiUrl = new RestApiUrl();
		String resourceUrl = restApiUrl.getConfluenceCreatePageUrl();
		client.setResourceName(resourceUrl);
		
		client.post(jsonOb);
		
	}
	/**
	 * 생성할 페이지 제목 설정
	 * @param devTeam
	 * @return
	 */
	public String getDevReportPageTitle(String devTeam){
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		
		SimpleDateFormat today = new SimpleDateFormat("yyyy/MM/dd");
		
		String title = devTeam + " 일일 작업기록 (" + today.format(cal.getTime()) +")" ;
		return title;
	}
}

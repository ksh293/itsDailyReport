package kr.co.saramin.itsDailyReport.module;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

/** 
 * <pre>
 *  파 일 명 : RestHttpClient.java
 *  설    명 : Rest API를 호출하기 위한 HttpClient 모듈 (Jersey Client)
 *  작 성 자 : 김소현
 *  작 성 일 : 2017.06.22
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 */

public class RestHttpClient {

	private ClientConfig clientConfig;
	
	private Client client;
	
	private WebResource webResource;
	
	private Properties properties;
	
	private final static String CONFIG_FILE = "-rest-client.properties";
	
	
	/**
	 * Client 객체 생성 시 호스트 인증 정보 세팅을 위해 파라미터를 넘겨받는다
	 * apiRequest : jira, confluence
	 * 인자값에 따라 JIRA / Confluence 설정
	 * @param apiRequest
	 * @throws Exception
	 */
	public RestHttpClient(String apiRequest) throws Exception {
		
		properties = new Properties();
		
		InputStream fis = getClass().getClassLoader().getResourceAsStream(apiRequest+CONFIG_FILE);
		
		properties.load(new BufferedInputStream(fis));
		
		clientConfig = new DefaultClientConfig();
		
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.FALSE);
		
		client = Client.create(clientConfig);
		
		client.addFilter(new LoggingFilter());
		
		HTTPBasicAuthFilter auth = new HTTPBasicAuthFilter(properties.getProperty("user.id"), properties.getProperty("user.pwd"));
		
		client.addFilter(auth);
	}
	
	/**
	 * 호출할 URL을 Client에 세팅한다.
	 * @param resourceName
	 */
	public void setResourceName(String resourceName) {
		
		webResource = client.resource(properties.getProperty("server.url") + resourceName);
	
	}
	
	/**
	 * GET 메소드 호출
	 * Type : application/json
	 * @return
	 */
	public ClientResponse get() {
		
		ClientResponse response = webResource.accept("application/json").type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		return checkStatus(response);
	}
	
	/**
	 * POST 메소드 호출
	 * Type : application/json
	 * json 형식으로 작성된 데이터를 인자로 받아 설정된 URL로 POST 메소드를 호출한다.
	 * @param json
	 * @return
	 */
	public ClientResponse post(String json) {
		
		
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		
		return checkStatus(response);
	}
	
	private ClientResponse checkStatus(ClientResponse response) {
		
		if(response.getStatus() != Status.OK.getStatusCode() && response.getStatus() != Status.CREATED.getStatusCode()) {
		
			throw new ClientHandlerException("Failed : HTTP error code : " + response.getStatus());
			 
		}
		
		return response;
	}
	
}

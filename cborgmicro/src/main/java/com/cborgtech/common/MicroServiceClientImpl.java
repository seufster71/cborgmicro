package com.cborgtech.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.serviceCrawler.MicroServiceClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Component("MicroServiceClient")
public class MicroServiceClientImpl implements MicroServiceClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	protected EurekaClient client;
	
	@Override
	public void process(RestRequest request, RestResponse response) {
		// use remote service
		RestTemplate restTemplate = restTemplateBuilder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka((String) request.getParams().get(GlobalConstant.MICROSERVICENAME), false);
		String baseUrl = instanceInfo.getHomePageUrl();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RestRequest> entity = new HttpEntity<>(request,headers);
		StringBuilder url = new StringBuilder();
		url.append(baseUrl);
		url.append((String) request.getParams().get(GlobalConstant.MICROSERVICEPATH));
		logger.info("call url " + url.toString());
		ResponseEntity<RestResponse> result = restTemplate.exchange(url.toString(), HttpMethod.POST, entity, RestResponse.class);
		
		response.setParams(((RestResponse) result.getBody()).getParams());
	}

}

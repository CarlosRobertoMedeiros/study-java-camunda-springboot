package br.com.roberto.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestDepositoReembolsoDelegate implements JavaDelegate {

	@Autowired
	private final RestTemplate restTemplate;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Response response;

        String uri = execution.getVariable("httpUri").toString();
        String httpMethodValue = execution.getVariable("httpMethod").toString().toUpperCase();
        String payload = execution.getVariable("payload").toString();

        HttpMethod httpMethod = HttpMethod.valueOf(httpMethodValue);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        response = exchange(uri, httpMethod, new HttpEntity<>(payload, headers));

        execution.setVariable("httpStatusCode", response.getHttpStatusCode());
        execution.setVariable("httpResponse", response.getHttpResponse());
	}

	private Response exchange(String uri, HttpMethod httpMethod, HttpEntity httpEntity) {
		String httpStatuscode;
        String httpResponse;
        Response response = new Response();
        try {
            //http://localhost:8525/api/demo
        	uri = "http://localhost:8525/api/demo";
        	ResponseEntity<String> exchange = restTemplate.exchange(uri, httpMethod, httpEntity, String.class);
            httpStatuscode = Integer.toString(exchange.getStatusCode().value());
            httpResponse = exchange.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Error occurred", ex);
            httpStatuscode = Integer.toString(ex.getStatusCode().value());
            httpResponse = ex.getResponseBodyAsString();
        }
        response.setHttpStatusCode(httpStatuscode);
        response.setHttpResponse(httpResponse);
        System.out.println("Deu Certinho "); 
        return response;
	}

}

@Data
class Response {

    private String httpStatusCode;
    private String httpResponse;

}

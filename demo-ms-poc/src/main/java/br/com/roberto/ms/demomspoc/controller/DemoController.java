package br.com.roberto.ms.demomspoc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
	
	@GetMapping
	public Response getAll() {
		return new Response("200","Deu Certinho MS Demonstrativo");
	}  

}


@Data
@AllArgsConstructor
class Response {

    private String httpStatusCode;
    private String httpResponse;

}

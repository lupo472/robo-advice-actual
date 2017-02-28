package it.uiip.digitalgarage.roboadvice.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ExampleController.class);
	
	public class GenericResponse{
		private String message;
		private boolean result;
		
		public String getMessage() {
			return message;
		}
		public boolean isResult() {
			return result;
		}
		public GenericResponse(boolean result, String message){
			this.message = message;
			this.result = result;
		}
	}
	
	@RequestMapping(value="/rest")
	public GenericResponse rest(){
		logger.info("rest");
		return new GenericResponse(true, "ciao Rest");
	}
	
	@RequestMapping(value="/access/rest")
	public GenericResponse access(){
		logger.info("rest");
		return new GenericResponse(true, "ciao Rest");
	}
}
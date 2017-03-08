package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class DefaultStrategyController extends AbstractController {
	
	@RequestMapping("/getDefaultStrategySet")
	@ResponseBody
	public GenericResponse<?> getDefaultStrategySet() {
		List<DefaultStrategyDTO> assets = this.defaultStrategyOp.getDefaultStrategySet();
		return new GenericResponse<List<DefaultStrategyDTO>>(1, assets);
	}

}

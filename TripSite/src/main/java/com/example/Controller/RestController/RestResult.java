package com.example.Controller.RestController;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResult {
	
	private int result;
	private Map<String,String> errors;

}

package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.PanelRequest;
import com.example.cms.responsedto.PanelResponse;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PanelController {

	private PanelService panelService;
	
	@PostMapping("/panel/addPanel")
	public ResponseEntity<ResponseStructure<PanelResponse>> createPanel(@RequestBody PanelRequest panelRequest ){
		return panelService.createPanel(panelRequest);
	}
}

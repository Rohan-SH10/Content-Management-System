package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.PanelRequest;
import com.example.cms.responsedto.PanelResponse;
import com.example.cms.utility.ResponseStructure;

public interface PanelService {

	ResponseEntity<ResponseStructure<PanelResponse>> createPanel(PanelRequest panelRequest);

}

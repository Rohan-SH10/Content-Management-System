package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.requestdto.PanelRequest;
import com.example.cms.responsedto.PanelResponse;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PanelServiceImpl implements PanelService{

	private ContributionPanelRepository panelRepository;
	private ResponseStructure<PanelResponse> responseStructure;
	
	@Override
	public ResponseEntity<ResponseStructure<PanelResponse>> createPanel(PanelRequest panelRequest) {
		ContributionPanel panel = mapToPanelEntity(panelRequest,new ContributionPanel());
		panelRepository.save(panel);
		return ResponseEntity.ok(responseStructure
				.setMessage("Panel created")
				.setStatusCode(HttpStatus.OK.value())
				.setData(mapToPanelResponse(panel)));
	}

	private PanelResponse mapToPanelResponse(ContributionPanel panel) {
		
		return PanelResponse.builder().panelId(panel.getPanelId()).contributors(panel.getContributors()).build();
	}

	private ContributionPanel mapToPanelEntity(PanelRequest panelRequest, ContributionPanel contributionPanel) {
		contributionPanel.setContributors(panelRequest.getContributors());
		
		return contributionPanel;
		
	}

}

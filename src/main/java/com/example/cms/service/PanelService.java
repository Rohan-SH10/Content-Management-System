package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.utility.ResponseStructure;

public interface PanelService {

	ResponseEntity<ResponseStructure<ContributionPanel>> insertUser(int userId,int  panelId);

	ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(int userId, int panelId);

}

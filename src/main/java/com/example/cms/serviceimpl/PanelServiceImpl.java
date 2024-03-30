package com.example.cms.serviceimpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.exceptions.ContributionPanelNotFoundByIdException;
import com.example.cms.exceptions.UnAuthorizedException;
import com.example.cms.exceptions.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PanelServiceImpl implements PanelService {

	private ContributionPanelRepository panelRepository;
	private ResponseStructure<ContributionPanel> responseStructure;
	private UserRepository userRepo;
	private BlogRepository blogRepo;

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> insertUser(int userId, int panelId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepo.findByEmail(username).map(owner -> {
			return panelRepository.findById(panelId).map(panel -> {
				if (!blogRepo.existsByUserAndPanel(owner, panel))
					throw new UnAuthorizedException("illegal accept request");
				return userRepo.findById(userId).map(contributor -> {
					if(!panelRepository.existsByContributors(contributor))
						panel.getContributors().add(contributor);
					panelRepository.save(panel);
					return ResponseEntity.ok(responseStructure.setData(panel).setMessage("user inserted success")
							.setStatusCode(HttpStatus.OK.value()));
				}).orElseThrow(() -> new UserNotFoundByIdException("cant insert contributor"));
			}).orElseThrow(() -> new ContributionPanelNotFoundByIdException("Cant insert contributor"));
		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(int userId, int panelId) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepo.findByEmail(username).map(owner -> {
			return panelRepository.findById(panelId).map(panel -> {
				if (!blogRepo.existsByUserAndPanel(owner, panel))
					throw new UnAuthorizedException("illegal accept request");
				return userRepo.findById(userId).map(contributor -> {
					if(panel.getContributors().contains(contributor))
						panel.getContributors().remove(contributor);
					panelRepository.save(panel);
					return ResponseEntity.ok(responseStructure.setData(panel).setMessage("user Deleted success")
							.setStatusCode(HttpStatus.OK.value()));
				}).orElseThrow(() -> new UserNotFoundByIdException("cant delete contributor"));
			}).orElseThrow(() -> new ContributionPanelNotFoundByIdException("Cant delete contributor"));
		}).get();
	}

}

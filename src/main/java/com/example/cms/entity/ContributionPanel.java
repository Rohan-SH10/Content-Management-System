package com.example.cms.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContributionPanel {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int panelId;
	
	@ManyToMany
	private List<User> contributors=new ArrayList<>();
	

	
}

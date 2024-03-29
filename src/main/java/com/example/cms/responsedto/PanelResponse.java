package com.example.cms.responsedto;

import java.util.List;

import com.example.cms.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PanelResponse {

	private int panelId;
	private List<User> contributors;
}

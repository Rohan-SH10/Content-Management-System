package com.example.cms.requestdto;

import java.util.List;

import com.example.cms.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanelRequest {

	private List<User> contributors;
}

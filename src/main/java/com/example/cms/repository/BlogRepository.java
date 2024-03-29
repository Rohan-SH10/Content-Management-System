package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

	boolean existsByTitle(String title);


	boolean existsByUserAndPanel(User user, ContributionPanel panel);


}

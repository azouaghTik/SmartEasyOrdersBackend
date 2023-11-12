package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Category;
import com.bezkoder.spring.datajpa.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByPublished(boolean published);
	//List<Category> findByTitleContaining(Long user_id);
	List<Category> findByUserId(Long user_id);

	@Query("SELECT c FROM Category c WHERE c.user.id = :user_id")
	List<Category> findCategoryByUser(Long user_id);


}

package com.bezkoder.spring.datajpa.repository;

import java.util.List;

import com.bezkoder.spring.datajpa.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, Long> {

	Long findFoodById(long id);
	List<Food> findByUserId(Long user_id);
	List<Food> findByPublished(boolean published);
	List<Food> findByTitleContaining(String title);

	List<Food> findByTitleContainingAndUserId(String title, Long userId);

	List<Food> findByUser_IdAndPublished(Long userId, boolean published);

	List<Food> findByUser_IdAndPublishedAndTitle(Long userId, boolean published, String title);


}

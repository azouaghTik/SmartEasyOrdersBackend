package com.bezkoder.spring.datajpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bezkoder.spring.datajpa.repository.*;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/client")
public class FoodClientController {
	@Autowired
	FoodRepository foodRepository;

	@Autowired
	FoodService foodService;

	@GetMapping("/foods/user/{user_id}")
	public ResponseEntity<List<Food>> getAllFoods(@PathVariable Long user_id, @RequestParam(required = false) String title) {
		try {
			List<Food> foods = new ArrayList<Food>();

			if (title == null)
				foodService.findFoodByUserAndPublished(user_id, true).forEach(foods::add);
			else
				foodRepository.findByUser_IdAndPublishedAndTitle(user_id, true,title).forEach(foods::add);

			if (foods.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(foods, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*@GetMapping("/foods/user/{user_username}")
	public List<Food> getFoodByUser_Username(@PathVariable("user_username") String user_username) {
		List<Food> foodData = foodService.getAllFoodsByUser_Username(user_username);
		return foodData;
	}*/


	/*@GetMapping("/foods/user/{user_id}")
	public List<Food> getFoodByUserAndPublished(
			@PathVariable Long user_id,
			@RequestParam(required = false) String title
	) {
		return foodService.findFoodByUserAndPublished(user_id, true);
	}*/



	@GetMapping("/foods/{id}")
	public ResponseEntity<Food> getFoodById(@PathVariable("id") long id) {
		Optional<Food> foodData = foodRepository.findById(id);

		if (foodData.isPresent()) {
			return new ResponseEntity<>(foodData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*@PostMapping("/foods")
	public ResponseEntity<Food> createFood(@RequestBody Food food) {
		try {
			Food _food = foodRepository
					.save(new Food(
							food.getTitle(),
							food.getPrice(),
							food.getImage(),
							food.getDescription(),
							food.isPublished()
					));
			return new ResponseEntity<>(_food, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/

	@PutMapping("/foods/{id}")
	public ResponseEntity<Food> updateFood(@PathVariable("id") long id, @RequestBody Food food) {
		Optional<Food> foodData = foodRepository.findById(id);

		if (foodData.isPresent()) {
			Food _food = foodData.get();
			_food.setTitle(food.getTitle());
			_food.setPrice(food.getPrice());
			_food.setImage(food.getImage());
			_food.setDescription(food.getDescription());
			_food.setPublished(food.isPublished());
			return new ResponseEntity<>(foodRepository.save(_food), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/foods/{id}")
	public ResponseEntity<HttpStatus> deleteFood(@PathVariable("id") long id) {
		try {
			foodRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/foods")
	public ResponseEntity<HttpStatus> deleteAllFoods() {
		try {
			foodRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/foods/published")
	public ResponseEntity<List<Food>> findByPublished() {
		try {
			List<Food> foods = foodRepository.findByPublished(true);
			//foodRepository.findByPublished(true).forEach(foods::add);
			//System.out.println(foods);
			if (foods.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(foods, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Category;
import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.repository.CategoryRepository;
import com.bezkoder.spring.datajpa.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/client")
public class CategoryClientController {
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryService categoryService;


	/*@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(@PathVariable Long user_id, @RequestParam(required = false) String title) {
		try {
			List<Category> categories = new ArrayList<Category>();

			if (title == null)
				categoryRepository.findCategoryByUser(user_id).forEach(categories::add);
			else
				categoryRepository.findByTitleContaining(user_id, title).forEach(categories::add);

			if (categories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/

	@GetMapping("/categories/user/{user_id}")
	public List<Category> getCategoryByUser(@PathVariable Long user_id) {
		return categoryService.findCategoryByUser(user_id);
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
		Optional<Category> categoryData = categoryRepository.findById(id);

		if (categoryData.isPresent()) {
			return new ResponseEntity<>(categoryData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/categories")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		try {
			Category _category = categoryRepository
					.save(new Category(
							category.getTitle(),
							category.getImage(),
							category.isPublished()
					));
			return new ResponseEntity<>(_category, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
		Optional<Category> categoryData = categoryRepository.findById(id);

		if (categoryData.isPresent()) {
			Category _category = categoryData.get();
			_category.setTitle(category.getTitle());
			_category.setImage(category.getImage());
			_category.setPublished(category.isPublished());
			return new ResponseEntity<>(categoryRepository.save(_category), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
		try {
			categoryRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/categories")
	public ResponseEntity<HttpStatus> deleteAllCategorys() {
		try {
			categoryRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/categories/published")
	public ResponseEntity<List<Category>> findByPublished() {
		try {
			List<Category> categories = categoryRepository.findByPublished(true);
			//categoryRepository.findByPublished(true).forEach(categories::add);
			//System.out.println(categories);
			if (categories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

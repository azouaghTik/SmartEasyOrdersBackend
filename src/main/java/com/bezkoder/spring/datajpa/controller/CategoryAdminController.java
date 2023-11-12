package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Category;
import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.repository.CategoryRepository;
import com.bezkoder.spring.datajpa.security.services.UserDetailsImpl;
import com.bezkoder.spring.datajpa.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")

@RestController
@RequestMapping("/api/admin")
public class CategoryAdminController {
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		// Obtenir l'objet Authentication de l'utilisateur actuellement authentifié
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Vérifier si l'utilisateur est authentifié
		if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			// Obtenir l'ID de l'utilisateur authentifié à partir de UserDetailsImpl
			Long user_id = userDetails.getId();

				// Sinon, obtenez tous les aliments de l'utilisateur
				List<Category> catogories = categoryService.getAllCategoriesByUserId(user_id);
				return catogories;

		} else {
			return null;
		}
	}


	@DeleteMapping("/categories/{id}")
	public ResponseEntity<HttpStatus> deleteFoodById(@PathVariable("id") long id) {
		try {
			Category category = categoryRepository.findById(id).orElse(null);
			if (category != null) {
				category.setUser(null); // Supprimez l'association avec l'utilisateur
				categoryRepository.save(category); // Mettez à jour l'enregistrement category
				categoryRepository.deleteById(id); // Supprimez l'enregistrement category
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/categories")
	public ResponseEntity<?> addFood(@RequestBody Category category) {
		// Récupérez l'utilisateur authentifié
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			// Obtenir l'ID de l'utilisateur authentifié à partir de UserDetailsImpl
			Long userid = userDetails.getId();
			categoryService.addCategory(category, userid);

			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			// Gérez le cas où l'utilisateur n'est pas authentifié ou où les informations de l'utilisateur ne sont pas disponibles
			// Vous pouvez renvoyer une erreur ou rediriger vers une page de connexion
			return null;
		}
	}


	/*@PostMapping("/categories")
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
	}*/

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

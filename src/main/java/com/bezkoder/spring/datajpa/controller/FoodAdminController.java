package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.FoodRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.bezkoder.spring.datajpa.security.services.UserDetailsImpl;
import com.bezkoder.spring.datajpa.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/admin")
public class FoodAdminController {
    @Autowired
    FoodRepository foodRepository;

    @Autowired
   FoodService foodService;

    @GetMapping("/foods")
    public List<Food> getAllUserFoodsForCurrentUser(@RequestParam(required = false) String title) {
        // Obtenir l'objet Authentication de l'utilisateur actuellement authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Vérifier si l'utilisateur est authentifié
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // Obtenir l'ID de l'utilisateur authentifié à partir de UserDetailsImpl
            Long user_id = userDetails.getId();

            if (title != null) {
                // Si le paramètre "title" est spécifié, recherchez par titre
                List<Food> foodsByTitle = foodService.getAllFoodsByTitleAndUserId(title, user_id);
                return foodsByTitle;
            } else {
                // Sinon, obtenez tous les aliments de l'utilisateur
                List<Food> foods = foodService.getAllFoodsByUserId(user_id);
                return foods;
            }
        } else {
            return null;
        }
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable("id") long id) {
        Optional<Food> foodData = foodRepository.findById(id);

        if (foodData.isPresent()) {
            return new ResponseEntity<>(foodData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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

    @PostMapping("/foods/{userId}")
    public ResponseEntity<?> addFood(@PathVariable("userId") long userId,@RequestBody Food food) {
            foodService.addFood(food, userId);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*@PostMapping("/foods")
    public ResponseEntity<?> addFood(@RequestBody Food food) {

        // Récupérez l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Obtenir l'ID de l'utilisateur authentifié à partir de UserDetailsImpl
            Long userid = userDetails.getId();
            foodService.addFood(food, userid);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // Gérez le cas où l'utilisateur n'est pas authentifié ou où les informations de l'utilisateur ne sont pas disponibles
            // Vous pouvez renvoyer une erreur ou rediriger vers une page de connexion
            return null;
        }
    }*/


    @DeleteMapping("/foods/{id}")
    public ResponseEntity<HttpStatus> deleteFoodById(@PathVariable("id") long id) {
        try {
            Food food = foodRepository.findById(id).orElse(null);
            if (food != null) {
                food.setUser(null); // Supprimez l'association avec l'utilisateur
                foodRepository.save(food); // Mettez à jour l'enregistrement Food
                foodRepository.deleteById(id); // Supprimez l'enregistrement Food
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


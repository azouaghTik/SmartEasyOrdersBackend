package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.SubFood;
import com.bezkoder.spring.datajpa.repository.SubFoodRepository;
import com.bezkoder.spring.datajpa.service.SubFoodService;
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
@RequestMapping("/api/admin")
public class SubFoodAdminController {

    @Autowired
    SubFoodRepository subFoodRepository;


    private final SubFoodService subFoodService;

    @Autowired
    public SubFoodAdminController(SubFoodService subFoodService) {
        this.subFoodService = subFoodService;
    }



    @GetMapping("/subfoods")
    public ResponseEntity<List<SubFood>> getAllFoods(@RequestParam(required = false) String title) {

        try {
            List<SubFood> subFoods = new ArrayList<SubFood>();

            if (title == null)
                subFoodRepository.findAll().forEach(subFoods::add);
            else
                subFoodRepository.findByTitleContaining(title).forEach(subFoods::add);

            if (subFoods.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(subFoods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/subfoods/list")
    public ResponseEntity<List<SubFood>> getSubFoods(@RequestParam(required = false) long food) {
        try {
            List<SubFood> subFoods = new ArrayList<SubFood>();

            subFoodRepository.findByFoodContaining(food).forEach(subFoods::add);

            if (subFoods.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(subFoods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subfoods/published")
    public ResponseEntity<List<SubFood>> findByPublished() {
        try {
            List<SubFood> foods = subFoodRepository.findByPublished(true);
            //subFoodRepository.findByPublished(true).forEach(foods::add);

            if (foods.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subfoods/{id}")
    public ResponseEntity<SubFood> getFoodById(@PathVariable("id") long id) {
        Optional<SubFood> foodData = subFoodRepository.findById(id);

        if (foodData.isPresent()) {
            return new ResponseEntity<>(foodData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subfoods/all/{foodId}")
    public ResponseEntity<List<SubFood>> getAllSubfoodsForFood(@PathVariable Long foodId) {
        // Votre logique pour récupérer tous les subfoods liés à ce foodId
        List<SubFood> subfoods = subFoodService.getAllSubfoodsForFood(foodId);
        return ResponseEntity.ok(subfoods);
    }

    @GetMapping("/subfoods/foods/{foodId}/subfoods/{subfoodId}")
    public ResponseEntity<SubFood> getSubfoodForFood(
            @PathVariable Long foodId,
            @PathVariable Long subfoodId) {
        // Votre logique pour récupérer le subfood en fonction des IDs
        SubFood subfood = subFoodService.getSubfoodForFood(foodId, subfoodId);
        return ResponseEntity.ok(subfood);
    }



    @PostMapping("/subfoods/{foodId}")
    public ResponseEntity<SubFood> addSubfoodToFood(
            @PathVariable Long foodId,
            @RequestBody SubFood subfood) {
        // Votre logique pour ajouter un subfood lié au foodId
        // Assurez-vous de gérer la liaison entre food et subfood
        // Enregistrez le subfood dans la base de données
        subFoodService.addSubfoodToFood(foodId, subfood);
        //return ResponseEntity.ok(savedSubfood);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/subfoods/{id}")
    public ResponseEntity<SubFood> updateFood(@PathVariable("id") long id, @RequestBody SubFood food) {
        Optional<SubFood> foodData = subFoodRepository.findById(id);

        if (foodData.isPresent()) {
            SubFood _food = foodData.get();
            _food.setTitle(food.getTitle());
            _food.setPrice(food.getPrice());
            _food.setPic(food.getPic());
            _food.setDescription(food.getDescription());
            _food.setPublished(food.isPublished());
            return new ResponseEntity<>(subFoodRepository.save(_food), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/subfoods/{id}")
    public ResponseEntity<HttpStatus> deleteFoodById(@PathVariable("id") long id) {
        try {
            SubFood subFood = subFoodRepository.findById(id).orElse(null);
            if (subFood != null) {
                subFood.setFood(null); // Supprimez l'association avec table Food
                subFoodRepository.save(subFood); // Mettez à jour l'enregistrement SubFood
                subFoodRepository.deleteById(id); // Supprimez l'enregistrement SubFood
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/subfoods")
    public ResponseEntity<HttpStatus> deleteAllFoods() {
        try {
            subFoodRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/subfoods/deleteAllByParents/{foodValue}")
    public void deleteDuplicatesByFoodValue(@PathVariable long foodValue) {
        subFoodService.deleteDuplicatesByFoodValue(foodValue);
    }


    @GetMapping("/subfoods/max-price/{foodValue}")
    public Double getMaxPrice(@PathVariable long foodValue) {
        return subFoodService.getMaxPrice(foodValue);
    }

    @GetMapping("/subfoods/min-price/{foodValue}")
    public Double getMinPrice(@PathVariable long foodValue) {
        return subFoodService.getMinPrice(foodValue);
    }

}

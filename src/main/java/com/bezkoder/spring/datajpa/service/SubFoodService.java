package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.SubFood;
import com.bezkoder.spring.datajpa.repository.FoodRepository;
import com.bezkoder.spring.datajpa.repository.SubFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bezkoder.spring.datajpa.exceptions.ResourceNotFoundException; // Assurez-vous d'utiliser le bon package pour votre classe

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubFoodService {

    @Autowired
    SubFoodRepository subFoodRepository;

    @Autowired
    FoodRepository foodRepository;

    public void deleteDuplicatesByFoodValue(long foodValue) {
        subFoodRepository.deleteDuplicatesByFood(foodValue);
    }

    public Double getMaxPrice(long foodValue) {
        return subFoodRepository.findMaxPrice(foodValue);
    }
    public Double getMinPrice(long foodValue) {
        return subFoodRepository.findMinPrice(foodValue);
    }

   //post subFood
    public SubFood addSubfoodToFood(Long foodId, SubFood subfood) {
        // Récupérez le food correspondant à l'ID
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + foodId));
        // Associez le subfood au food
        subfood.setFood(food);
        // Enregistrez le subfood dans la base de données
        return subFoodRepository.save(subfood);
    }

    //get subFood
    public List<SubFood> getAllSubfoodsForFood(Long foodId) {
        // Récupérez le food correspondant à l'ID
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + foodId));

        // Récupérez tous les subfoods liés à ce food
        return subFoodRepository.findByFood(food);
    }
    //get SubFood by id
    public SubFood getSubfoodForFood(Long foodId, Long subfoodId) {
        // Récupérez le food correspondant à l'ID
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + foodId));

        // Récupérez le subfood correspondant à l'ID et au food
        SubFood subfood = subFoodRepository.findByIdAndFood(subfoodId, food)
                .orElseThrow(() -> new ResourceNotFoundException("Subfood not found with id " + subfoodId));

        return subfood;
    }
}

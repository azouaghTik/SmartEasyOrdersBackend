package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.FoodRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository, UserRepository userRepository) {
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }


    public List<Food> getAllFoodsByUserId(Long user_id) {
        // Vous pouvez implémenter la logique pour récupérer les aliments associés à un utilisateur spécifique ici.
        // Par exemple, vous pouvez utiliser le repository pour effectuer une requête personnalisée.
        // Assumez que vous avez une méthode findByUser_Username dans votre repository pour récupérer les aliments par nom d'utilisateur.

        return foodRepository.findByUserId(user_id);

}

    public List<Food> getAllFoodsByTitleAndUserId(String title, Long userId) {
        return foodRepository.findByTitleContainingAndUserId(title, userId);
    }


    public List<Food> findFoodByUserAndPublishedAndTitle(Long userId, boolean published, String title) {
        return foodRepository.findByUser_IdAndPublishedAndTitle(userId, published,title);
    }

    public List<Food> findFoodByUserAndPublished(Long userId, boolean published) {
        return foodRepository.findByUser_IdAndPublished(userId, published);
    }

    public void deleteFood(Food food) {
        foodRepository.delete(food);
    }
    public Food getFoodById(long id) {
        Optional<Food> foodOptional = foodRepository.findById(id);
        return foodOptional.orElse(null);
    }

    public void addFood(Food food, Long userid) {
        // Récupérez l'utilisateur à partir de la base de données en fonction du nom d'utilisateur
        User user = userRepository.getUserById(userid);

        if (user != null) {
            // Associez l'utilisateur à l'aliment
            food.setUser(user);
            foodRepository.save(food);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé : " + userid);
        }
    }



}

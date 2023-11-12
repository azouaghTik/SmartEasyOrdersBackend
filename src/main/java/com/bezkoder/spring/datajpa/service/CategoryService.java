package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Category;
import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.CategoryRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;


    public List<Category> getAllCategoriesByUserId(Long user_id) {
        return categoryRepository.findByUserId(user_id);

    }

    public void addCategory(Category category, Long userid) {
        // Récupérez l'utilisateur à partir de la base de données en fonction du nom d'utilisateur
        User user = userRepository.getUserById(userid);

        if (user != null) {
            // Associez l'utilisateur à l'aliment
            category.setUser(user);
            categoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé : " + userid);
        }
    }

    public List<Category> findCategoryByUser(Long userId) {
        return categoryRepository.findCategoryByUser(userId);
    }


}

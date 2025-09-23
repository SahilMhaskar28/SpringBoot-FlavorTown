package com.example.test.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.test.model.Food;
import com.example.test.serviceImpl.FoodServiceImpl;

@Controller
public class FoodController {

    @Autowired
    private FoodServiceImpl fdimpl;
    Food food;
    boolean flag;
    
    @GetMapping("/")
    public String home() {
        return "home";   
    }

    @GetMapping("/food/add")
    public String addFood(Model model) {
        model.addAttribute("food", new Food());  
        return "addFood";
    }

    @PostMapping("/food/save")
    public String saveFood( Food food) {
        flag = fdimpl.addFood(food);
        if (flag) {
            return "redirect:/food/list";
        } else {
            return "redirect:/food/add";
        }
    }

    @GetMapping("/food/list")
    public String getFoodList(Model model) {
        List<Food> li = fdimpl.getAllFood();
        if (li != null && !li.isEmpty()) {
            model.addAttribute("foodList", li);
            return "getFood";  
        } else {
            return "redirect:/";  
        }
    }
    
    @GetMapping("/food/update/{foodId}")
    public String updateFood(Model model,@PathVariable int foodId) {
    	food=fdimpl.searchFood(foodId);
    	if(food != null) {
    		model.addAttribute("food", food);
    		return "updateFood";
    	}
    	else {
    		return "redirect:/";
    	}
    }
    
    @GetMapping("/food/delete/{foodId}")
    public String deleteFood(@PathVariable int foodId) {
    	flag=fdimpl.deleteFood(foodId);
    	if(flag) {
    		return "redirect:/food/list";
    	}
    	else {
    		return "redirect:/food/list";
    	}
    }
    
    @GetMapping("/food/searchByName")
    public String searchFood(@RequestParam String keyword, Model model) {
        List<Food> results = fdimpl.searchFoodByName(keyword);     
        model.addAttribute("foodList", results);
        model.addAttribute("keyword", keyword);
        return "getFood";
    }
    
    @GetMapping("/about")
    public String aboutPage() {
		return "AboutUs";
    }
}
package com.webshop.admin.controller;

import lnu.webshop.library.model.Category;
import lnu.webshop.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal) {
        if (principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("size",categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("newCategory",new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("newCategory") Category category, RedirectAttributes attributes){
        try {
            Category save = categoryService.save(category);
            System.out.println(save.toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById",method = {RequestMethod.PUT,RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes) {
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success","updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Category with this name already exist");
        }
        catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Something went wrong");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id,RedirectAttributes attributes) {
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success","deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Something went wrong");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/activate-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String activate(Long id,RedirectAttributes attributes) {
        try {
            categoryService.enableById(id);
            attributes.addFlashAttribute("success","activated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Something went wrong");
        }
        return "redirect:/categories";
    }
}

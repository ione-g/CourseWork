package lnu.webshop.library.service.impl;

import lnu.webshop.library.dto.CategoryDto;
import lnu.webshop.library.model.Category;
import lnu.webshop.library.repository.CategoryRepository;
import lnu.webshop.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        try {
            Category categorySave = new Category(category.getName());
            return repo.save(categorySave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category updateCategory = null;
        try {
            updateCategory = repo.findById(category.getId()).get();
            updateCategory.setName(category.getName());
            category.set_activated(category.is_activated());
            category.set_deleted(category.is_activated());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repo.save(updateCategory);
    }

    @Override
    public void deleteById(Long id) {
        Category category = repo.findById(id).get();
        category.set_deleted(true);
        category.set_activated(false);
        repo.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = repo.findById(id).get();
        category.set_deleted(false);
        category.set_activated(true);
        repo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repo.findAllByActivated();
    }

    @Override
    public List<CategoryDto> findNumberProductsInCategory() {
        return repo.findNumberProductsInCategory();
    }
}
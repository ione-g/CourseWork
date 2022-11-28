package lnu.webshop.library.service;

import lnu.webshop.library.dto.CategoryDto;
import lnu.webshop.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);

    List<Category> findAllByActivated();

    List<CategoryDto> findNumberProductsInCategory();
}

package lnu.webshop.customer.controller;

import lnu.webshop.library.dto.CategoryDto;
import lnu.webshop.library.model.Category;
import lnu.webshop.library.model.Product;
import lnu.webshop.library.service.CategoryService;
import lnu.webshop.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @GetMapping("products")
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        List<CategoryDto> categories = categoryService.findNumberProductsInCategory();
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id")Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        List<Product> relatedProducts = productService.getRelatedProducts(product.getCategory().getId());
        model.addAttribute("product",product);
        model.addAttribute("products",relatedProducts);
        return "product-detail";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id")Long categoryId,Model model) {
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categories = categoryService.findNumberProductsInCategory();
        List<Product> products = productService.getProductsInCategory(categoryId);
        model.addAttribute("category",category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "products-in-category";
    }
}

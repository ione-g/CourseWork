package com.webshop.admin.controller;

import lnu.webshop.library.dto.ProductDto;
import lnu.webshop.library.model.Category;
import lnu.webshop.library.model.Product;
import lnu.webshop.library.service.CategoryService;
import lnu.webshop.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/products")
    public String products(Model model,
                           Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("titel", "Manage Products");
        model.addAttribute("products",productDtoList);
        model.addAttribute("size",productDtoList.size());
        return "products";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "add-product";
    }
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes) {
        try{
            productService.save(imageProduct,productDto);
            attributes.addFlashAttribute("success","Added successfully");
        }catch (Exception e) {
            attributes.addFlashAttribute("failed","Something went wrong");

            e.printStackTrace();

        }
        return "redirect:/products/0";
    }
    @GetMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                Model model,
                                Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title","update product");

        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);

        ProductDto productDto =  productService.findById(id);
        model.addAttribute("productDto",productDto);

        return "update-product";
    }
    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @RequestParam("imageProduct")MultipartFile imageProduct,
                                @ModelAttribute("productDto") ProductDto productDto,
                                RedirectAttributes attributes) {
        try {
            productService.update(imageProduct,productDto);
            attributes.addFlashAttribute("success","Update successfully");
        }catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Something went wrong");
        }
        return "redirect:/products/0";
    }
    @RequestMapping(value = "/delete-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id,int pageNo,RedirectAttributes attributes) {
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success","deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Something went wrong");
        }
        return "redirect:/products/"+pageNo;
    }
    @RequestMapping(value = "/activate-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String activate(Long id,int pageNo,RedirectAttributes attributes) {
        try {
            productService.enableById(id);
            attributes.addFlashAttribute("success","activated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Something went wrong");
        }
        return "redirect:/products/"+pageNo;
    }
    @GetMapping("products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model,Principal principal) {
        if (principal == null) { return "redirect:/login"; }
        Page<Product> products = productService.productPage(pageNo);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("products",products);
        return "products";
    }
    @GetMapping ("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword")String keyword,
                                 Model model,
                                 Principal principal) {
        if (principal == null){
            return "redirect:/login";
        }
        Page<Product> products = productService.searchProducts(pageNo,keyword);
        model.addAttribute("title","Search Result");
        model.addAttribute("products",products);
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return "products";
    }
}


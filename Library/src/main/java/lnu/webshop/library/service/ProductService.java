package lnu.webshop.library.service;

import lnu.webshop.library.dto.ProductDto;
import lnu.webshop.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    //Адмін сервіс
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(MultipartFile imageProduct, ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
    ProductDto findById(Long id);

    Page<Product> productPage(int pageNo);
    Page<Product> searchProducts(int pageNo,String keyword);


    // Клієнтська частина

    List<Product> getAllProducts();

    Product getProductById(Long id) ;

    List<Product> getRelatedProducts(Long categoryId);
    List<Product> getProductsInCategory(Long categoryId);
    List<Product> filterHighPrice();
    List<Product> filterLowPrice();

}

package lnu.webshop.library.service;

import lnu.webshop.library.model.Customer;
import lnu.webshop.library.model.Product;
import lnu.webshop.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);

    ShoppingCart deleteItemFromCart(Product product, Customer customer);

}

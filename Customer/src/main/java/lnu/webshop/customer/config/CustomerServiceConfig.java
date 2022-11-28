package lnu.webshop.customer.config;

import lnu.webshop.library.model.Customer;
import lnu.webshop.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class CustomerServiceConfig implements UserDetailsService {

    @Autowired
    private CustomerRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repo.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Could not find exception");
        }
        return new User(customer.getUsername(),
                        customer.getPassword(),
                        customer.getRoles()
                        .stream()
                                .map(role->new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())) ;
    }
}

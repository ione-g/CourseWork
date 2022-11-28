package lnu.webshop.library.service;

import lnu.webshop.library.dto.AdminDto;
import lnu.webshop.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);

}

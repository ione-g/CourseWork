package lnu.webshop.library.util;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {

    private final String UPLOAD_FOLDER = "I:\\LNU\\COURSE\\WebShopCourseWork\\Admin\\src\\main\\resources\\static\\image-product";

    public boolean uploadImage(MultipartFile imageProduct) {
        boolean isUpload = false;
        try {
            Files.copy(imageProduct.getInputStream(),
                    Paths.get(UPLOAD_FOLDER+ File.separator, imageProduct.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile multipartFile) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER+"\\"+multipartFile.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }
}

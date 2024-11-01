package com.learning.E_commers.E_commers.Service;

import com.learning.E_commers.E_commers.Modal.Product;
import com.learning.E_commers.E_commers.Repositary.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProduct(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repository.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
            return repository.save(product);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {

       List<Product> product =repository.searchProduct(keyword);
       return product;
    }
}

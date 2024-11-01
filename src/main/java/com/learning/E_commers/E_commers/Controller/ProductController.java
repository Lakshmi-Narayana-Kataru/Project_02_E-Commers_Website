package com.learning.E_commers.E_commers.Controller;

import com.learning.E_commers.E_commers.Modal.Product;
import com.learning.E_commers.E_commers.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private ProductService service;
    public ProductController(ProductService service){
        this.service =service;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){

        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){

        Product product = service.getProduct(id);
        if(product != null)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) throws IOException {
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity(product1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id){
        Product product = service.getProduct(id);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id,
                                                 @RequestPart Product product,
                                                 @RequestPart MultipartFile imageFile) throws IOException {

        Product product1 = service.updateProduct(id,product,imageFile);
        return new ResponseEntity(product1,HttpStatus.OK);
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){

        Product prodcuct = service.getProduct(id);
        if(prodcuct !=null){
            service.deleteProduct(id);
            return new ResponseEntity("Product deleted successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity("Product not found",HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> product = service.searchProduct(keyword);
        return new ResponseEntity(product,HttpStatus.OK);
    }
}

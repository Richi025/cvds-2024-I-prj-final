package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Product;
import co.edu.eci.cvds.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String toList(Model model) {
        List<Product> products=productService.getAllProducts();
        model.addAttribute("products", products);
        for (Product produc : products) {
            System.out.println(produc.toString());
        }
        return "listProducts";
    }


    @GetMapping("/update/{productId}")
    public String updateProduct(@PathVariable String productId, Model model) {
        Product product = productService.getProduct(productId);
        if (product != null) {
            model.addAttribute("product", product);
            return "updateProducts";
        } else {
            // Manejo de error si no se encuentra el empleado
            return "error"; // Puedes redirigir a una página de error
        }
    }

    @PostMapping("/update/{productId}")
    public String update(@PathVariable String productId, @ModelAttribute("product") Product productUpdate) {
        Optional<Product> optionalProduct = productService.getProductM(productId);
        Product product = optionalProduct.get();
        if (!product.equals(null)) {
            product.setName(productUpdate.getName());
            product.setDescription(productUpdate.getDescription());
            product.setTechnicalDescription(productUpdate.getTechnicalDescription());
            product.setCoin(productUpdate.getCoin());
            product.setTechnicalDescription(productUpdate.getTechnicalDescription());
            product.setPrice(productUpdate.getPrice());
            product.setDiscount(productUpdate.getDiscount());
            product.setTax(productUpdate.getTax());
            productService.updateProduct(product);
            return "redirect:/api/products";
        } else {
            return "error";
        }
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return "redirect:/api/products";
    }


/**
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product productDetails) {
        productDetails.setProductId(productId);
        Product updatedProduct = productService.updateProduct(productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Otros métodos según sea necesario
     */

}

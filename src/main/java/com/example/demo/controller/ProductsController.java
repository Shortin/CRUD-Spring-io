package com.example.demo.controller;

import com.example.demo.model.Products;
import com.example.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping({"/"})
    public String viewProductsLis( Model model){
        model.addAttribute("products", productsService.getAllProducts());
        model.addAttribute("title", "Просмотр списока продуктов");

        return "ViewProductsList";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("title", "Добавить продукты");

        return "AddProduct";
    }

    @PostMapping("/addProduct")
    public String postAddProduct(@RequestParam String title, @RequestParam Float price, Model model){
        Products product = new Products(title, price);
        productsService.saveOrUpdateProduct(product);
        return "redirect:/";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        productsService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        Products product = productsService.getProductById(id);
        model.addAttribute("product", product);
        return  "editProduct";
    }

    @PostMapping("/editProduct/{id}")
    public  String postEditProduct(@PathVariable(value = "id") long id,
                                   @RequestParam String title, @RequestParam Float price){
        Products product = productsService.getProductById(id);
        product.setTitle(title);
        product.setPrice(price);

        if (productsService.saveOrUpdateProduct(product)) {
            return "redirect:/";
        }
        return "redirect:/editProduct/" + product.getId();
    }

}
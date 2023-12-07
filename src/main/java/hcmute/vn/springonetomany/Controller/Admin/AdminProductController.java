package hcmute.vn.springonetomany.Controller.Admin;

import hcmute.vn.springonetomany.Entities.Category;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Service.CategoryService;
import hcmute.vn.springonetomany.Service.ProductService;
import hcmute.vn.springonetomany.Ultis.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String showProductsPage(Model model) {
        List<Product> listProduct = productService.findAll();
        model.addAttribute("listProduct", listProduct);
        return "product/admin_products";
    }

    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        List<Category> listCategory = categoryService.findAll();
        model.addAttribute("product", new Product());
        model.addAttribute("listCategory", listCategory);
        return "product/product_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") int id, Model model) {
        try {
            Product product = productService.findById(id);
            List<Category> listCategory = categoryService.findAll();
            model.addAttribute("listCategory", listCategory);
            model.addAttribute("product", product);
            return "product/product_form";
        } catch (Exception e) {
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/save")
    private String saveProduct(Product product, @RequestParam(value = "image") MultipartFile multipartFile, @RequestParam(value = "id", required = false) Integer id) throws Exception {
        String fileName = id == null || (multipartFile != null && !multipartFile.isEmpty())
                ? StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()))
                : productService.findById(id).getPhotos();

        product.setPhotos(fileName);
        Product savedProduct = productService.getNewProduct(product);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String uploadDir = "product_photos/" + savedProduct.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/admin/products";
    }
}

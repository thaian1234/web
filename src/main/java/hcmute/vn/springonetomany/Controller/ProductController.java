package hcmute.vn.springonetomany.Controller;

import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Entities.User;
import hcmute.vn.springonetomany.Repository.IUserRepository;
import hcmute.vn.springonetomany.Entities.Rating;
import hcmute.vn.springonetomany.Service.CategoryService;
import hcmute.vn.springonetomany.Service.ProductService;
import hcmute.vn.springonetomany.Service.RatingService;
import hcmute.vn.springonetomany.Ultis.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private RatingService ratingService;

    @GetMapping("")
    public String getOnePage(Model model,
                             @RequestParam(required = false, defaultValue = "1") int page,
                             HttpSession session) {
        Page<Product> productPage = productService.findPage(page);
        List<Product> productList = productPage.getContent();
        int totalPages = productPage.getTotalPages();
        long totalItems = productPage.getTotalElements();

        model.addAttribute("productList", productList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        return "product/products";
    }

    @GetMapping(path = {"/search"})
    public String searchProducts(Model model, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam int page) {
        List<Product> productList = null;

        if (keyword != null) {
            Page<Product> productPage = productService.searchProducts(keyword, page);
            productList = productPage.getContent();
            int totalPages = productPage.getTotalPages();
            long totalItems = productPage.getTotalElements();

            model.addAttribute("productList", productList);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", page);
        } else {
            productService.findAll();
        }
        model.addAttribute("productList", productList);
        return "product/products";
    }

    @GetMapping({"/category/{id}"})
    public String showProductsByCategoryId(@PathVariable("id") int id, @RequestParam int page, Model model) throws Exception {
        Page<Product> productPage = productService.getProductsByCategory_Id(id, page);
        List<Product> productList = productPage.getContent();
        int totalPages = productPage.getTotalPages();
        long totalItems = productPage.getTotalElements();

        model.addAttribute("productList", productList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("currentPage", page);
        return "product/products";
    }

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") int id, @RequestParam(name = "reviewPage", defaultValue = "1") int reviewPage, Model model) {
        try {
            Product product = productService.findById(id);
            Page<Rating> ratingPage = ratingService.getRatingsByProduct_Id(id, reviewPage);
            List<Rating> listRating = ratingPage.getContent();
            int totalPages = ratingPage.getTotalPages();
            long totalItems = ratingPage.getTotalElements();
            Rating rating = new Rating();
            int numberOfRating = 0;
            int ratingPoint = 0;
            if (!product.getRatings().isEmpty()) {
            	numberOfRating = product.getRatings().size();
                ratingPoint = product.getRatingPoint();
            }
            model.addAttribute("listRating", listRating);
            model.addAttribute("ratingPoint", ratingPoint);
            model.addAttribute("numberOfRating", numberOfRating);
            model.addAttribute("product", product);
            model.addAttribute("rating", rating);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", reviewPage);
            
        } catch (Exception e) {
            return "redirect:/products";
        }
        return "detail";
    }
}

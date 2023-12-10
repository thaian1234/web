package hcmute.vn.springonetomany.Controller;

import hcmute.vn.springonetomany.Entities.Category;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Entities.ProductImages;
import hcmute.vn.springonetomany.Entities.User;
import hcmute.vn.springonetomany.Repository.IUserRepository;
import hcmute.vn.springonetomany.Service.CategoryService;
import hcmute.vn.springonetomany.Service.ProductService;
import hcmute.vn.springonetomany.Ultis.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String getOnePage(Model model, @RequestParam(required = false, defaultValue = "1") int page,
			HttpSession session) {
		Page<Product> productPage = productService.findPage(page);
		List<Product> productList = productPage.getContent();
		int totalPages = productPage.getTotalPages();
		long totalItems = productPage.getTotalElements();

		model.addAttribute("productList", productList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);

		if (session.getAttribute("user") != null) {
			// Cập nhật user trong session
			User user = (User) session.getAttribute("user");
			user = userRepository.findById(user.getId()).orElse(null);
			session.setAttribute("user", user);
		}

		return "product/products";
	}

	@GetMapping(path = { "/search" })
	public String searchProducts(Model model, @RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam int page) {
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

	@GetMapping({ "/category/{id}" })
	public String showProductsByCategoryId(@PathVariable("id") int id, @RequestParam int page, Model model)
			throws Exception {
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
	public String showProductDetail(@PathVariable("id") int id, Model model) {
		try {
			Product product = productService.findById(id);
			model.addAttribute("product", product);
			Optional<Category> optionalCategory  = categoryService.getCategory(product.getCategory().getId());
			 if (optionalCategory.isPresent()) {
				 Category currentCategory = optionalCategory.get();
				 Set<Product> categoryProducts = currentCategory.getProducts();
				 List<Product> categoryProductList = new ArrayList<>(categoryProducts);
				 categoryProductList.remove(product);
				 int maxProductsCount = Math.min(6, categoryProductList.size());
				 List<Product> relevantProducts = categoryProductList.subList(0, maxProductsCount);
				 model.addAttribute("relevantProducts", relevantProducts);
			 }
			 else {
				 return "redirect:/products";
			 }
			/*
			 * if (!relevantCategories.isEmpty()) {
			 * relevantCategories.remove(product.getCategory()); }
			 */
//			relevantCategories.remove(product.getCategory());

			/*
			 * Random random = new Random(); Category randomCategory =
			 * relevantCategories.get(random.nextInt(relevantCategories.size()));
			 * Set<Product> categoryProducts=randomCategory.getProducts(); List<Product>
			 * categoryProductList = new ArrayList<>(categoryProducts);
			 * categoryProductList.remove(product);
			 * //Collections.shuffle(categoryProductList);
			 * 
			 * 
			 * List<Product> relevantProducts = new ArrayList<>(); List<Product>
			 * categoryProductList = new ArrayList<>(categoryProducts); int
			 * randomProductsCount = Math.min(5, categoryProducts.size()); for (int i = 0; i
			 * < randomProductsCount; i++) {
			 * relevantProducts.add(categoryProductList.get(random.nextInt(categoryProducts.
			 * size()))); }
			 * 
			 * int randomProductsCount = Math.min(5, categoryProductList.size());
			 * List<Product> relevantProducts = categoryProductList.subList(0,
			 * randomProductsCount);
			 */
		} catch (Exception e) {
			return "redirect:/products";
		}
		return "detail";
	}

}

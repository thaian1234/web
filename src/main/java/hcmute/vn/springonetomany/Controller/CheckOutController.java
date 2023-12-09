package hcmute.vn.springonetomany.Controller;

import hcmute.vn.springonetomany.Custom.CustomUser;
import hcmute.vn.springonetomany.Custom.CustomUserDetails;
import hcmute.vn.springonetomany.Custom.Oauth2.CustomOAuth2User;
import hcmute.vn.springonetomany.Entities.Cart;
import hcmute.vn.springonetomany.Entities.CartItem;
import hcmute.vn.springonetomany.Entities.Category;
import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Entities.User;
import hcmute.vn.springonetomany.Repository.IOrderRepository;
import hcmute.vn.springonetomany.Repository.IProductRepository;
import hcmute.vn.springonetomany.Repository.IUserRepository;
import hcmute.vn.springonetomany.Service.CartItemService;
import hcmute.vn.springonetomany.Service.CartService;
import hcmute.vn.springonetomany.Service.CategoryService;
import hcmute.vn.springonetomany.Service.OrderService;
import hcmute.vn.springonetomany.Service.ProductService;
import hcmute.vn.springonetomany.Service.UserService;
import hcmute.vn.springonetomany.Ultis.FileUploadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class CheckOutController {
	 @Autowired
	    CartService cartService;
	 @Autowired
	    IUserRepository userRepository;
	 @Autowired
	    CartItemService cartItemService;
	 @Autowired
	    IProductRepository productRepository;
	    @Autowired
	    private IOrderRepository iorderServicIOrderRepository;
	    @Autowired 
	    private OrderService orderService;
    @GetMapping("/checkout")
    public String viewHomePage(Model model, @AuthenticationPrincipal CustomUser loggedUser, HttpSession session) {
    	 User user = (User) session.getAttribute("user");
         Cart cart = cartService.getCartByUserId(user.getId());
//         Set<CartItem> cartItemList = cart.getCartItems();
         List<CartItem> cartItemList = cartItemService.listCartItemByCartId(cart.getId());
         cartService.recalculateCartTotal(cart.getId());
         
         model.addAttribute("cartItemList", cartItemList);
         model.addAttribute("quantity",cartItemList.size());
         model.addAttribute("total", cart.getPriceFormatted());
         // Cập nhật user trong session
         user = userRepository.findById(user.getId()).orElse(null);
         session.setAttribute("user", user);

        return "checkout";
    }
    @PostMapping("/save")
    private String saveOrder(@Valid Order order,
                               BindingResult result,
                               @RequestParam(value = "image") MultipartFile multipartFile,
                               @RequestParam(value = "cart_id", required = false) Integer id) throws Exception {
        if (result.hasErrors()) {
            return "checkout";
        }
        Cart cart = cartService.findCartById(id);
        Order order1 =  new Order();
//        OderSer
//
//        String fileName = id == null || (multipartFile != null && !multipartFile.isEmpty())
//                ? StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()))
//                : orderService.findById(id).getPhotos();
//
//        product.setPhotos(fileName);
     // Lưu đối tượng Order mới
//        Order savedOrder = orderService.saveOrder(order);
        return "redirect:/checkout";
    }
//    @PostMapping("/save")
//    private String saveProduct(@Valid Product product,
//                               BindingResult result,
//                               @RequestParam(value = "image") MultipartFile multipartFile,
//                               @RequestParam(value = "id", required = false) Integer id) throws Exception {
//        if (result.hasErrors()) {
//            return "product/product_form";
//        }
//
//        String fileName = id == null || (multipartFile != null && !multipartFile.isEmpty())
//                ? StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()))
//                : productService.findById(id).getPhotos();
//
//        product.setPhotos(fileName);
//        Product savedProduct = productService.getNewProduct(product);
//
//        if (multipartFile != null && !multipartFile.isEmpty()) {
//            String uploadDir = "product_photos/" + savedProduct.getId();
//            FileUploadUtil.deleteAllFiles(uploadDir);
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//        }
//        return "redirect:/admin/products";
//    }
    
}

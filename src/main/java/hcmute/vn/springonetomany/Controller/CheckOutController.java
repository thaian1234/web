package hcmute.vn.springonetomany.Controller;

import hcmute.vn.springonetomany.Custom.CustomUser;
import hcmute.vn.springonetomany.Custom.CustomUserDetails;
import hcmute.vn.springonetomany.Custom.Oauth2.CustomOAuth2User;
import hcmute.vn.springonetomany.Entities.Cart;
import hcmute.vn.springonetomany.Entities.CartItem;
import hcmute.vn.springonetomany.Entities.Category;
import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.OrderLines;
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
    
    
    
    @GetMapping("")
    public String showOrder(Model model) {
        //List<Order> orders  = orderService.listAll();
        model.addAttribute("orders", new Order());
        //model.addAttribute("OrderLines", new OrderLines());
        return "checkout/order";
    }
    //sử lí post save
    @PostMapping("/save")
    public String saveOrder(@RequestParam("id") int id,
                              @RequestParam("price") double price,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("productId") int productId,
                              @RequestParam("orderId") int orderId,
                              Model model) {
        // Xử lý dữ liệu và lưu vào cơ sở dữ liệu 
    	   OrderLines orderLines = new OrderLines();
    	   Order order = new Order();
    	    orderLines.setId(id);
    	    orderLines.setPrice(price);
    	    orderLines.setQuantity(quantity);
    	    orderLines.getProductId().setId(productId);
    	    orderLines.getOrderId().setId(orderId);

    	   orderService.getNewOrder(order);
        // Truyền dữ liệu vào Model để hiển thị trên trang kết quả
        model.addAttribute("price", price);
        model.addAttribute("quantity", quantity);

        return "redirect:/checkout";
    }

//    @PostMapping("/save")
//    private String saveOrder(@Valid Order order,
//                               BindingResult result,                              
//                               @RequestParam(value = "cart_id", required = false) Integer id) throws Exception {
//        if (result.hasErrors()) {
//            return "checkout";
//        }
//        Cart cart = cartService.findCartById(id);
//        Order order1 =  new Order();
//       Order saveOrder = orderService.getNewOrder(order);
//        return "redirect:/checkout";
//    }

    
}

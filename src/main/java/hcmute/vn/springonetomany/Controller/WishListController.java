package hcmute.vn.springonetomany.Controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Entities.WishList;
import hcmute.vn.springonetomany.Entities.WishListItem;
import hcmute.vn.springonetomany.Service.WishListService;

@Controller
public class WishListController {
	@Autowired
	WishListService wishListService;
	
	
	 @GetMapping("/wishlist") 
	 public String showWishlist(Model model,HttpServletRequest request) 
	 { 
		String sessionToken = (String) request.getSession(true).getAttribute("sessiontTokenWishList");
		List<WishListItem> wishlistItems = wishListService.getAllWishlists(sessionToken);
	    model.addAttribute("wishlistItem", wishlistItems); // Note the plural form "wishlistItems"

	    return "wishlist";
	 }
	 
	@GetMapping("/addtowishlist/{id}")
	public String addToWishList(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
		String sessionToken = (String) request.getSession(true).getAttribute("sessiontTokenWishList");
	    if (sessionToken == null) {
	        sessionToken = generateAndSetSessionToken(request);
	        wishListService.addToWishFirstTime(id, sessionToken);
	    } else {
	        wishListService.addToExistingWishList(id, sessionToken);
	    }

	    return "redirect:/home";
	}

   
	@GetMapping("/removewishlistitem/{id}")
	public String removeItem(@PathVariable("id") Integer id, HttpServletRequest request) {
	    String sessionToken = getSessionTokenFromRequest(request);
	    wishListService.removeItemWishList(id, sessionToken);
	    return "redirect:/wishlist";
	}

	@GetMapping("/clearwishlist")
	public String clearWishList(HttpServletRequest request) {
	    String sessionToken = getSessionTokenFromRequest(request);
	    clearSessionToken(request);
	    wishListService.clearWishList(sessionToken);
	    return "redirect:/wishlist";
	}

	// Helper methods
	private String getSessionTokenFromRequest(HttpServletRequest request) {
	    return (String) request.getSession(false).getAttribute("sessiontTokenWishList");
	}

	private String generateAndSetSessionToken(HttpServletRequest request) {
	    String sessionToken = UUID.randomUUID().toString();
	    request.getSession().setAttribute("sessiontTokenWishList", sessionToken);
	    return sessionToken;
	}

	private void clearSessionToken(HttpServletRequest request) {
	    request.getSession(false).removeAttribute("sessiontTokenWishList");
	}

	
}

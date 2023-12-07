package hcmute.vn.springonetomany.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import hcmute.vn.springonetomany.Entities.WishList;
import hcmute.vn.springonetomany.Service.WishListService;
@ControllerAdvice
public class GeneralController {
	@Autowired
	private WishListService wishlistService;
	
	@ModelAttribute
	public void populateModel(Model model, HttpServletRequest request) {
	  String sessionTokenwishList = (String) request.getSession(true).getAttribute("sessiontTokenWishList");
		
		
		if(sessionTokenwishList == null) {
			model.addAttribute("wishList", new WishList());
			
		}
		else {
			model.addAttribute("wishList", wishlistService.getWishListBySessionToken(sessionTokenwishList));
		}
		
	}
	

}

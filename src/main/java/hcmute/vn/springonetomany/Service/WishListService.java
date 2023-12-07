package hcmute.vn.springonetomany.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Entities.WishList;
import hcmute.vn.springonetomany.Entities.WishListItem;
import hcmute.vn.springonetomany.Repository.IWishListItemRepository;
import hcmute.vn.springonetomany.Repository.IWishListRepository;



@Service
public class WishListService {
	
	@Autowired
	private IWishListRepository wishListRepository;
	@Autowired
	private IWishListItemRepository wishListItemRepository;
	@Autowired
	private ProductService productService;
	
	public WishList addToWishFirstTime(Integer id, String sessionToken) throws Exception {
		WishList wishlist = new WishList();
		WishListItem item = new WishListItem();
		
		item.setDate(new Date());
		item.setProduct(productService.findById(id));
		wishlist.getItems().add(item);
		wishlist.setSessionToken(sessionToken);
		wishlist.setDate(new Date());
		return wishListRepository.save(wishlist);

	}

	public WishList addToExistingWishList(Integer id, String sessionToken) throws Exception {

		WishList wishList = wishListRepository.findBySessionToken(sessionToken);
		Product p = productService.findById(id);
		Boolean productDoesExistInTheCart = false;
		if (wishList != null) {
		    Set<WishListItem> items = wishList.getItems();
			for (WishListItem item : items) {
				if (item.getProduct().equals(p)) {
					productDoesExistInTheCart = true;
					break;  
				}
				
			}
		}
		if(!productDoesExistInTheCart && (wishList != null))
		{
			WishListItem item1 = new WishListItem();
			item1.setDate(new Date());
			item1.setProduct(p);
			wishList.getItems().add(item1);
			return wishListRepository.saveAndFlush(wishList);
		}
		
		return null;

	}

	public WishList getWishListBySessionToken(String sessionToken) {
		
		return  wishListRepository.findBySessionToken(sessionToken);
	}

	
	public WishList removeItemWishList(Integer id, String sessionToken) {
		WishList WishList = wishListRepository.findBySessionToken(sessionToken);
		Set<WishListItem> items = WishList.getItems();
		WishListItem item = null;
		for(WishListItem item1 : items) {
			if(item1.getId()==(long)id) {
				item = item1;
			}
		}
		items.remove(item);
		wishListItemRepository.delete(item);
	    WishList.setItems(items);
	    return wishListRepository.save(WishList);
	}

	public void clearWishList(String sessionToken) {
		WishList sh = wishListRepository.findBySessionToken(sessionToken);
		wishListRepository.delete(sh);
		
	}

	public List<WishListItem> getAllWishlists(String sessionToken) {
	    WishList wishList = wishListRepository.findBySessionToken(sessionToken);

	    if (wishList != null) {
	        return new ArrayList<>(wishList.getItems());
	    } else {
	        return Collections.emptyList(); // Return an empty list if the wishlist is not found.
	    }
	}

	public long getWishlistItemCount() {
        return wishListItemRepository.count();
        }


}

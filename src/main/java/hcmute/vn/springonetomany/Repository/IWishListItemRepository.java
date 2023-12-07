package hcmute.vn.springonetomany.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.vn.springonetomany.Entities.WishListItem;

@Repository
public interface IWishListItemRepository extends JpaRepository<WishListItem, Long>{
	
}

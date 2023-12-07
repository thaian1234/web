package hcmute.vn.springonetomany.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.vn.springonetomany.Entities.WishList;

@Repository
public interface IWishListRepository extends JpaRepository<WishList,Long>{
	WishList findBySessionToken(String sessionToken);
	
}

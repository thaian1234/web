package hcmute.vn.springonetomany.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.Product;

@Repository

// import org.springframework.stereotype.Repository; va @Repository


public interface IOrderRepository extends JpaRepository<Order, Integer> {

	
//	@Query("select p from Product p where p.category.id = ?1")
//    Page<Order> getOrderF(int category_id, Pageable pageable)
}

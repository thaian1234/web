package hcmute.vn.springonetomany.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.vn.springonetomany.Entities.Cart;
import hcmute.vn.springonetomany.Entities.CartItem;
import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.OrderLines;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Repository.IOrderRepository;

@Service
public class OrderService {
//	public List<Product> findAll() {
//
//        return productRepository.findAll();
//    }
	@Autowired
    private IOrderRepository orderRepository;
	
    public void save(Order order) {
    	orderRepository.save(order);
    }
    public Order getNewOrder(Order order) {
    	 return orderRepository.save(order);
    }
    public Order getOrderByCart(Cart cart) {
    	Order order = new Order();
    	OrderLines oderLines = new OrderLines();
    	for ( CartItem cartItem:cart.getCartItems()) {
    		
    		   //OrderLines.productId.setProductId() = cartItem.getTotal();
    		
    	}
    	return null;
    }
//    public Product getNewProduct(Product product) {
//        return productRepository.save(product);
//    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
} 

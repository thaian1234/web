package hcmute.vn.springonetomany.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.vn.springonetomany.Entities.Cart;
import hcmute.vn.springonetomany.Entities.CartItem;
import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.OrderLines;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Repository.IOrderLinesRepository;
import hcmute.vn.springonetomany.Repository.IOrderRepository;
//import hcmute.vn.springonetomany.Repository.OrderLinesRepository;

@Service
public class OrderService {
	
	
//	@Autowired
//    private IOrderRepository orderRepository;
//	
//    public void save(Order order) {
//    	orderRepository.save(order);
//    }
//    public Order getNewOrder(Order order) {
//    	 return orderRepository.save(order);
//    }
//    public Order getOrderByCart(Cart cart) {
//    	Order order = new Order();
//    	OrderLines oderLines = new OrderLines();
//    	for ( CartItem cartItem:cart.getCartItems()) {
//    		
//    		   //OrderLines.productId.setProductId() = cartItem.getTotal();
//    		
//    	}
//    	return null;
//    }
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }
//    
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderLinesRepository orderLinesRepository;

    public void saveOrder(Order order, List<CartItem> cartItems) {
        // Lưu thông tin đơn hàng
        Order savedOrder = orderRepository.save(order);
        System.out.println("Saved Order ID: " + savedOrder.getId());
        // Lưu thông tin các sản phẩm trong đơn hàng vào bảng order_lines
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            OrderLines orderLines = new OrderLines();
            orderLines.setOrder(savedOrder);
            orderLines.setProduct(product);
            orderLines.setPrice(product.getPrice());
            orderLines.setQuantity(cartItem.getQuantity());

            orderLinesRepository.save(orderLines);
            System.out.println("Saved OrderLine ID: " + orderLines.getId());
        }
//        public List<Order> getAllOrders() {
////          return orderRepository.findAll();
//        }
    }

	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}   
}

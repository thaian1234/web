package hcmute.vn.springonetomany.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.vn.springonetomany.Entities.Cart;
import hcmute.vn.springonetomany.Entities.CartItem;
import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.OrderLines;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Entities.User;
import hcmute.vn.springonetomany.Repository.ICartItemRepository;
import hcmute.vn.springonetomany.Repository.ICartRepository;
import hcmute.vn.springonetomany.Repository.IOrderLinesRepository;
import hcmute.vn.springonetomany.Repository.IOrderRepository;
import hcmute.vn.springonetomany.Repository.IProductRepository;
import hcmute.vn.springonetomany.Repository.IUserRepository;

@Service
public class OrderService {
//	public List<Product> findAll() {
//
//        return productRepository.findAll();
//    }
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	ICartRepository cartRepository;
	@Autowired
	IUserRepository userRepository;
	@Autowired
	IProductRepository productRepository;
	@Autowired
	ICartItemRepository cartItemRepository;
	@Autowired
	CartItemService cartItemService;
	@Autowired
	IOrderLinesRepository OderLinesRepository;

//    @Autowired 
//    Orderlines
//	
	public void save(Order order) {
		orderRepository.save(order);
	}

	public Order getNewOrder(Order order) {
		return orderRepository.save(order);}
	
	public Order createOrderFromCart(User user, Cart cart) {
	    Order order = new Order();
	    order.setUser(user);

	    List<OrderLines> orderLines = new ArrayList<>();
	    for (CartItem item : cart.getCartItems()) {
	        OrderLines line = new OrderLines();
	        line.setOrder(order);
//	        line.setItem(item.getItem());
	        line.setItem(item.getItem());
	        line.setQuantity(item.getQuantity());
	        orderLines.add(line);
	    }

	    order.setOrderLines(orderLines);
	    return order;
	}

//	public OrderLines getNewOrderlines(OrderLines orderLines) {
//		return OderLinesRepository.save(orderLines);}
//		public Order getOrderByCart(Cart cart) {
//		    Order order = new Order();
//		    List<OrderLines> orderLinesList = new ArrayList<>();
//
//		    for (CartItem cartItem : cart.getCartItems()) {
//		        OrderLines orderLines = new OrderLines();
//		        orderLines.setProductId(cartItem.getProduct());
//		        orderLines.setPrice(cartItem.getProduct().getPrice());
//		        orderLines.setQuantity(cartItem.getQuantity());
//		        orderLines.setOrderId(order);
//		        orderLinesList.add(orderLines);
//		    }
//		    order.setOrderLines(orderLinesList);
//		    return order;
//		}		

////	public Order getOrderByCart(Cart cart) {
//
////		Order order = new Order();
////    	OrderLines orderLines = new OrderLines();
//		List<OrderLines> orderLinesList = new ArrayList<>();
//		for (CartItem cartItem : cart.getCartItems()) {
//			OrderLines orderLines = new OrderLines();
//			orderLines.setProductId(cartItem.getProduct());
//			orderLines.setPrice(cartItem.getProduct().getPrice());
//			orderLines.setQuantity(cartItem.getQuantity());
//
//			orderLinesList.add(orderLines);
////    		   OrderLines.productId.setProductId() = cartItem.getTotal(); code mẫu
////    	    OrderLines orderLines = new OrderLines();
////    	    orderLines.setQuantity(this.getQuantity());
////    	    orderLines.setPrice(this.product.getPrice());
////    	    orderLines.setProductId(this.getProduct());
////    	    orderLines.setOrderId(this.getCart().getOrder());
//		}
//		order.setOrderLines(orderLinesList);
//		return order;
//	}
//
////    public Product getNewProduct(Product product) {
////        return productRepository.save(product);
////    }
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
}

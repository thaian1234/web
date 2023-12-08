package hcmute.vn.springonetomany.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.vn.springonetomany.Entities.Order;
import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Repository.IOrderRepository;

@Service
public class OrderService {
//	public List<Product> findAll() {
//
//        return productRepository.findAll();
//    }
	@Autowired
    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
} 

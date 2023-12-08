package hcmute.vn.springonetomany.Controller.Admin;

import hcmute.vn.springonetomany.Service.OrderService; // Thêm import này

//import hcmute.vn.springonetomany.Repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	  @Autowired
	   // private IOrderRepository iorderServicIOrderRepository;  // gốc
	    private OrderService orderService; // Sửa tên biến nàys
		  @GetMapping("")
		    public String showInvoices(Model model) {
		    //   model.addAttribute("order", orderService.getAllOrders());

		        return "order/admin_order";
		    }
}

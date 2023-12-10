package hcmute.vn.springonetomany.Entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User userId;

	public void setOrderLines(List<OrderLines> orderLinesList) {
		
		
	}
	
	
//	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	    private List<OrderLines> orderLines;
//	 
//	    public void setOrderLines(List<OrderLines> orderLines) {
//	        for (OrderLines orderLine : orderLines) {
//	            orderLine.setOrder(this);
//	        }
//	        this.orderLines = orderLines;
//	    }
	    
//	public void setOrderLines(List<OrderLines> orderLines) {
//	    for (OrderLines orderLine : orderLines) {
//	        orderLines.setOrderId(this);
//	    }
//	    this.OrderLines = orderLines;
//	}
}

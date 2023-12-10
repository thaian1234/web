package hcmute.vn.springonetomany.Entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import hcmute.vn.springonetomany.Enum.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_order")
public class AddressOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order orderId;

    @Nationalized
    @Column(name = "street_number")
    private String streetNumber;

    @Nationalized
    @Column(name = "ward")
    private String ward;

    @Nationalized
    @Column(name = "district")
    private String district; 

    @Nationalized
    @Column(name = "city")
    private String city;

    @Nationalized
    @Column(name = "country")
    private String country;
}

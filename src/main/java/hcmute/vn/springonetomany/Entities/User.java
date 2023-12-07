package hcmute.vn.springonetomany.Entities;

import hcmute.vn.springonetomany.Enum.AuthProvider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email(message = "Vui lòng nhập đúng định dạng Email")
	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = true, length = 64)
	private String password;
	
	@NotNull(message = "Không được bỏ trống")
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	@NotNull(message = "Không dược bỏ trống")
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(mappedBy = "users")
    private Set<Voucher> vouchers = new HashSet<>();
	
    @Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Nationalized
    @Column(name = "country", length = 50)
    private String country;

    @Nationalized
    @Column(name = "gender", length = 5)
    private String gender;

	@Size(min = 4, max = 12, message = "Số điện thoại phải từ 4 đến 12 kí tự")
    @Column(name = "phone", length = 15)
    private String phone;

    @Nationalized
    @Column(name = "photos", length = 100)
    private String photos;

	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthProvider authProvider;

	@CreationTimestamp
	@Transient
    private Date createdAt;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Transient
	public String getPhotosImagePath() {
		if (photos == null || id == null) return null;

		return "/user_photos/" + id + "/" + photos;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
}

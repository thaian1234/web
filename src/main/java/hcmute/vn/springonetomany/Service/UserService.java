package hcmute.vn.springonetomany.Service;

import hcmute.vn.springonetomany.Entities.Role;
import hcmute.vn.springonetomany.Entities.User;
import hcmute.vn.springonetomany.Enum.AuthProvider;
import hcmute.vn.springonetomany.Repository.IRoleRepository;
import hcmute.vn.springonetomany.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepo;

    @Autowired
    IRoleRepository roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    int PAGE_SIZE = 2;

    public void registerDefaultUser(User user) {
        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);
        encodePassword(user);
        userRepo.save(user);
    }

    public List<User> listAll() {
        return userRepo.findAll();
    }

    public Page<User> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return userRepo.findAll(pageable);
    }

    public User getUserById(Long id) {
        Optional<User> existedUser = userRepo.findById(id);
        if (existedUser.isPresent()) {
            return existedUser.get();
        }
        throw new UsernameNotFoundException("Không tồn tại người dùng");
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    public void save(User user) {
        encodePassword(user);
        userRepo.save(user);
    }

    public void saveOauth2(User user) {
        userRepo.save(user);
    }

    public User getNewUser(User user) {
        encodePassword(user);
        return userRepo.save(user);
    }

    public User updateUser(User user) {
        User existingUser = userRepo.findById(user.getId()).orElse(null);

        if (existingUser != null) {
            // Check if the password field is empty
            if (user.getPassword().isEmpty()) {
                // If empty, keep the old password
                user.setPassword(existingUser.getPassword());
            } else {
                // If not empty, encode the new password
                encodePassword(user);
            }
        }

        return userRepo.save(user);
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public void deleteUserById(Long id) {
        if (userRepo.findById(id).isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng");
        }
        userRepo.deleteById(id);
    }

    public boolean existsUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        return user != null;
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean checkPassword(String email, String password) {
        User user = userRepo.findByEmail(email);
        return  user.getPassword().equals(password);
    }

    public void updateAuthenticationType(String username, String oauth2ClientName) {
        AuthProvider authType = AuthProvider.valueOf(oauth2ClientName.toUpperCase());
        userRepo.updateAuthenticationProvider(username, authType);
    }
}

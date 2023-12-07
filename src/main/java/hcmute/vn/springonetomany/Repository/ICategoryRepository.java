package hcmute.vn.springonetomany.Repository;

import hcmute.vn.springonetomany.Entities.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
	Category findByName(String name);
}

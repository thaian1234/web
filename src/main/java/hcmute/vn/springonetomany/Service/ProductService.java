package hcmute.vn.springonetomany.Service;

import hcmute.vn.springonetomany.Entities.Product;
import hcmute.vn.springonetomany.Repository.ICartItemRepository;
import hcmute.vn.springonetomany.Repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    int PAGE_SIZE = 2;

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;

    public List<Product> findAll() {

        return productRepository.findAll();
    }

    public Page<Product> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return productRepository.findAll(pageable);
    }

    public Page<Product> getProductsByCategory_Id(int category_id, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return productRepository.getProductsByCategory_Id(category_id, pageable);
    }

    public Page<Product> searchProducts(String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return productRepository.getProductsByKeyword(keyword, pageable);
    }

    public void save(Product product) {
        productRepository.save(product);
    }
    @Transactional
    public void deleteById(int id) {
        cartItemRepository.deleteAllByProductId(id);
        productRepository.deleteById(id);
    }

    public Product getNewProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findById(int id) throws Exception {
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new Exception("Could not find product");
    }

	public Page<Product> listAll() {

		Pageable pageable = PageRequest.of(0, 10);
        return productRepository.findAll(pageable);
	}

	/*
	 * public Page<Product> findByPriceOrderByPriceAsc(int page) { Pageable pageable
	 * = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("price").ascending()); return
	 * productRepository.findAll(pageable); }
	 * 
	 * public Page<Product> findByNameOrderByNameAsc(int page) { Pageable pageable =
	 * PageRequest.of(page - 1, PAGE_SIZE, Sort.by("name").ascending()); return
	 * productRepository.findAll(pageable); }
	 * 
	 * public Page<Product> findByRatingOrderByRatingDesc(int page) { Pageable
	 * pageable = PageRequest.of(page - 1, PAGE_SIZE,
	 * Sort.by("rating").descending()); return productRepository.findAll(pageable);
	 * }
	 */
    	
}

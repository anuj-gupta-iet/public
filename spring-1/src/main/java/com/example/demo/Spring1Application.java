package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@SpringBootApplication
@RestController
public class Spring1Application {

	@Autowired
	private MyService myService;

	@Autowired
	Inventory inventory;

	@GetMapping("/groupByBrand")
	public Map<Brand, List<Product>> groupByBrand(HttpServletRequest request) {
		System.out.println(request.getHeader("User-Agent")); // to chk request
																// is coming
																// from browser
																// or mobile
		return myService.groupByBrand();
	}

	@GetMapping("/groupByColor")
	public Map<Color, List<Product>> groupByColor() {
		return myService.groupByColor();
	}

	@GetMapping("/getProductBySKU/{id}")
	public Product getProductBySKU(@PathVariable String id) {
		return myService.getProductBySKU(id);
	}

	@GetMapping("/getAvailableProductsBySeller/{sellerId}")
	public List<Product> getAvailableProductsBySeller(
			@PathVariable String sellerId) {
		return myService.getAvailableProductsBySeller(sellerId);
	}

	@GetMapping("/purchaseProduct/{productId}")
	public String purchaseProduct(@PathVariable String productId) {
		return myService.purchaseProduct(productId);
	}

	public static void main(String[] args) {
		SpringApplication.run(Spring1Application.class, args);
	}
}

@Component
class MyService {
	@Autowired
	private Inventory inventory;

	public Map<Brand, List<Product>> groupByBrand() {
		return inventory.productsMap.values().stream()
				.collect(Collectors.groupingBy(p -> p.getBrand()));
	}

	public String purchaseProduct(String productId) {
		Product product = inventory.productsMap.get(productId);
		if (product.getIsSold()) {
			return "Product already sold: " + product;
		}
		return inventory.purchaseProduct(product);
	}

	public List<Product> getAvailableProductsBySeller(String sellerId) {
		return inventory.productsMap.values().stream()
				.filter(p -> sellerId.equalsIgnoreCase(p.getId().split("-")[0]))
				.filter(p -> !p.getIsSold()).collect(Collectors.toList());
	}

	public Product getProductBySKU(String id) {
		return inventory.productsMap.get(id);
	}

	public Map<Color, List<Product>> groupByColor() {
		return inventory.productsMap.values().stream()
				.collect(Collectors.groupingBy(p -> p.getColor()));
	}
}

@Component
class Inventory {
	@Autowired
	List<Seller> sellers;

	Map<String, Product> productsMap = new ConcurrentHashMap<>();

	@PostConstruct
	private void init() {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Updating Inventory");
				updateInventory();
				productsMap.values().stream().forEach(System.out::println);
			}
		};
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 15000);
	}

	public void updateInventory() {
		for (Seller seller : sellers) {
			List<Product> products = seller.getUpdatedProducts();
			Map<String, Product> map = products.stream().map(p -> {
				p.setId(seller.getSellerId() + "-" + p.getId());
				p.setSellerName(seller.getSellerName());
				return p;
			}).collect(Collectors.toMap(p -> p.getId(), Function.identity()));
			productsMap.putAll(map);
		}
	}

	public String purchaseProduct(Product product) {
		Optional<Seller> seller = sellers.stream()
				.filter(s -> s.getSellerId()
						.equalsIgnoreCase(product.getId().split("-")[0]))
				.findFirst();
		if (!seller.isPresent()) {
			return "Seller not present for product: " + product;
		}
		product.setIsSold(true);
		seller.get().purchaseProduct(product);
		return "Product purchased Successfully: " + product;
	}

}

interface Seller {
	String getSellerId();
	String getSellerName();
	List<Product> getUpdatedProducts();
	void purchaseProduct(Product product);
}

@Component
class ABCSeller implements Seller {
	String id = "abc";
	String sellerName = "ABC Seller";

	@Override
	public List<Product> getUpdatedProducts() {
		Random r = new Random();

		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < 3; i++) {
			int index = r.nextInt(3);
			Brand brand = Brand.values()[index];
			Category category = Category.values()[index];
			Color color = Color.values()[index];
			Size size = Size.values()[index];
			Product product = Product.builder().brand(brand).category(category)
					.color(color).size(size).id(r.nextInt(100) + "")
					.isSold(r.nextBoolean()).build();
			products.add(product);
		}
		System.out.println(sellerName + "-->" + products);
		return products;
	}

	@Override
	public String getSellerId() {
		return this.id;
	}

	@Override
	public String getSellerName() {
		return this.sellerName;
	}

	@Override
	public void purchaseProduct(Product product) {
		System.out.println(this.sellerName + " updated with purchased product: "
				+ product);
	}
}

@Component
class XYZSeller implements Seller {
	String id = "xyz";
	String sellerName = "XYZ Seller";

	@Override
	public List<Product> getUpdatedProducts() {
		Random r = new Random();

		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < 5; i++) {
			int index = r.nextInt(3);
			Brand brand = Brand.values()[index];
			Category category = Category.values()[index];
			Color color = Color.values()[index];
			Size size = Size.values()[index];
			Product product = Product.builder().brand(brand).category(category)
					.color(color).size(size).id(r.nextInt(100) + "")
					.isSold(r.nextBoolean()).build();
			products.add(product);
		}
		System.out.println(sellerName + "-->" + products);
		return products;
	}

	@Override
	public String getSellerId() {
		return this.id;
	}

	@Override
	public String getSellerName() {
		return this.sellerName;
	}

	@Override
	public void purchaseProduct(Product product) {
		System.out.println(this.sellerName + " updated with purchased product: "
				+ product);
	}
}

@AllArgsConstructor
@Data
@Builder
class Product {
	private String id;
	private Category category;
	private Brand brand;
	private Color color;
	// private Integer price;
	private Size size;
	private Boolean isSold;
	private String sellerName;
}

enum Brand {
	Nike, Reebok, Adidas
}

enum Category {
	Pant, Shirt, Cap;
}

enum Color {
	Black, Blue, Red;
}

enum Size {
	S, M, L;
}
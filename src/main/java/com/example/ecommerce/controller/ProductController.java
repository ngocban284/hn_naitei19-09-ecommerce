package com.example.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.ecommerce.config.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;

@Controller("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("categories/{categoryId}/products")
//<<<<<<< HEAD
//	public String index(@PathVariable(name = "categoryId") Integer categoryId, Model model, HttpSession session,
//			HttpServletResponse response) throws IOException {
//
//=======
	public String index(@PathVariable(name = "categoryId") Integer categoryId,
			@RequestParam(value = "page", defaultValue = "1") Integer page, Model model, HttpServletResponse response, HttpSession session)
			throws IOException {
		if (categoryId == null) {
			response.sendRedirect("/404-page");
			String errorMessage = "Không tìm thấy sản phẩm";
			model.addAttribute("errorMessage", errorMessage);
			return "error";
		}
		int pageSize = 8;
		Integer currentPage;
		if (page != null) {
			currentPage = page;
		} else {
			currentPage = 1;
		}
		List<Product> products;
		int startIndex = (currentPage - 1) * pageSize;
		UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");
		model.addAttribute("user", currentUser);
		products = this.productService.getProductsByCategory(categoryId);
		int endIndex = Math.min(startIndex + pageSize, products.size());
		List<Product> productsOnPage = products.subList(startIndex, endIndex);
		model.addAttribute("products", productsOnPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", (int) Math.ceil((double) products.size() / pageSize));
		return "products/index";

	}

	@GetMapping(value = "categories/{categoryId}/products", params = { "page", "sortBy" })
	public String sort(@PathVariable(name = "categoryId") Integer categoryId,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "sortBy") String sortBy, 
			@RequestParam(value = "ord", required = false) String order,
			Model model, HttpServletResponse response) throws IOException {

		int pageSize = 8;
		Integer currentPage;
		if (page != null) {
			currentPage = page;
		} else {
			currentPage = 1;
		}
		List<Product> products = null;
		int startIndex = (currentPage - 1) * pageSize;
		if (sortBy.equals("sales")) {
			products = this.productService.sortByBuyCount(categoryId);
			int endIndex = Math.min(startIndex + pageSize, products.size());
			List<Product> productsOnPage = products.subList(startIndex, endIndex);
			model.addAttribute("products", productsOnPage);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalPages", (int) Math.ceil((double) products.size() / pageSize));
			return "products/sort/sales";
		} else if (sortBy.equals("price")) {
			if (order != null && !order.isEmpty()) {
				if (order.equals("ASC")) {
					products = this.productService.sortByPrice(categoryId, "ASC");
				} else if (order.equals("DESC")) {
					products = this.productService.sortByPrice(categoryId, "DESC");
				}
				int endIndex = Math.min(startIndex + pageSize, products.size());
				List<Product> productsOnPage = products.subList(startIndex, endIndex);
				model.addAttribute("products", productsOnPage);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("totalPages", (int) Math.ceil((double) products.size() / pageSize));
				model.addAttribute("order", order);
				return "products/sort/price";
			}
		}
		return order;
	}

	@GetMapping("/{product_id}")
	public String show(@PathVariable(name = "product_id") Integer id, Model m, HttpServletResponse response)
			throws IOException {
		Product product = this.productService.getReferenceById(id);
		if (product == null) {
			response.sendRedirect("/404-page");
			String errorMessage = "Không tìm thấy sản phẩm";
			m.addAttribute("errorMessage", errorMessage);
			return "error";
		}

		m.addAttribute("product", product);
		return "products/show";
	}

	@GetMapping("/search")
	public String search(@RequestParam(value = "category", required = true) Integer categoryId,
			@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			Model model) {

		int pageSize = 8;
		Integer currentPage;
		if (page != null) {
			currentPage = page;
		} else {
			currentPage = 1;
		}
		List<Product> products = null;
		int startIndex = (currentPage - 1) * pageSize;
		String id = String.valueOf(categoryId);
		if (id.equals("-1")) {
			products = this.productService.searchAllCategories(keyword);
		} else {
			Category selectedCategory = this.categoryService.getReferenceById(categoryId);
			if (selectedCategory == null) {
				return "error";
			}
			products = this.productService.searchByCategory(categoryId, keyword);
		}

		int endIndex = Math.min(startIndex + pageSize, products.size());
		List<Product> productsOnPage = products.subList(startIndex, endIndex);
		model.addAttribute("products", productsOnPage);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", (int) Math.ceil((double) products.size() / pageSize));
		return "products/search/search";
	}

	@GetMapping(value = "/search", params = { "category", "keyword", "page", "sortBy" })
	public String sort(@RequestParam(value = "category", required = true) Integer categoryId,
			@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "sortBy") String sortBy,
			@RequestParam(value = "ord", required = false) String order, Model model) {
		int pageSize = 8;
		Integer currentPage;
		if (page != null) {
			currentPage = page;
		} else {
			currentPage = 1;
		}
		List<Product> products = null;
		int startIndex = (currentPage - 1) * pageSize;
		if (sortBy != null && !sortBy.isEmpty()) {
			if (sortBy.equals("sales")) {
				String id = String.valueOf(categoryId);
				if (id.equals("-1")) {
					products = this.productService.sortResultsByBuyCount(keyword);
				} else {
					products = this.productService.sortResultsByBuyCount(categoryId, keyword);
				}
				int endIndex = Math.min(startIndex + pageSize, products.size());
				List<Product> productsOnPage = products.subList(startIndex, endIndex);
				model.addAttribute("products", productsOnPage);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("categoryId", categoryId);
				model.addAttribute("totalPages", (int) Math.ceil((double) products.size() / pageSize));
				return "products/search/sortBySales";

			} else if (sortBy.equals("price")) {
				if (order != null && !order.isEmpty()) {
					String id = String.valueOf(categoryId);
					if (id.equals("-1")) {
						if (order.equals("ASC")) {
							products = this.productService.sortResultsByPrice(keyword, "ASC");
						} else if (order.equals("DESC")) {
							products = this.productService.sortResultsByPrice(keyword, "DESC");
						}

					} else {
						if (order.equals("ASC")) {
							products = this.productService.sortResultsByPrice(categoryId, keyword, "ASC");
						} else if (order.equals("DESC")) {
							products = this.productService.sortResultsByPrice(categoryId, keyword, "DESC");
						}
					}
				}
				int endIndex = Math.min(startIndex + pageSize, products.size());
				List<Product> productsOnPage = products.subList(startIndex, endIndex);
				model.addAttribute("products", productsOnPage);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("categoryId", categoryId);
				model.addAttribute("totalPages", (int) Math.ceil((double) products.size() / pageSize));
				model.addAttribute("order", order);
				return "products/search/sortByPrice";
			}
		}
		return order;

	}

}

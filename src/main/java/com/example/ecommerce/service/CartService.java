package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dao.CartDetailRepository;
import com.example.ecommerce.dao.CartRepository;
import com.example.ecommerce.dao.ProductRepository;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartDetailRepository cartDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	public Cart findByUserId(Long userID) {
//		System.out.println("userID: " + userID);
//		System.out.println("cartRepository.findByUserId(userID): " + cartRepository.findByUserId(userID));
		return cartRepository.findByUserId(userID);
	}

	public Cart addItemToCart(User user, Product product, int amount) {
		// Kiểm tra xem người dùng đã có giỏ hàng hay chưa
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);

			// Lưu giỏ hàng
			cart = cartRepository.save(cart);
		}

		// Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
		boolean productExistsInCart = cartDetailRepository.existsByProductAndCart(product, cart);

		if (productExistsInCart) {
			// Sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
			CartDetail cartDetail = cartDetailRepository.findByProductAndCart(product, cart);
			cartDetail.setAmount(cartDetail.getAmount() + amount);
		} else {
			// Sản phẩm chưa tồn tại trong giỏ hàng, tạo mới chi tiết giỏ hàng
			CartDetail cartDetail = new CartDetail();
			cartDetail.setCart(cart);
			cartDetail.setProduct(product);
			cartDetail.setAmount(amount);
			cartDetailRepository.save(cartDetail);
		}

		// Lưu giỏ hàng
		return cartRepository.save(cart);
	}

	public Cart addItemToCartByProductId(User user, int Id, int amount) {

		Product product = productRepository.findById(Id).orElse(null);
		System.out.println("product: " + product);

		// Kiểm tra tồn tại sản phẩm trong product hay không
		if (product == null) {
			return null;
		}

		// Kiểm tra xem người dùng đã có giỏ hàng hay chưa
		Cart cart = cartRepository.findByUser(user);
//		System.out.println("cart: " + cart);

		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);

			// Lưu giỏ hàng
			cart = cartRepository.save(cart);
		}

		Long CartId = cart.getId();

		// Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
		boolean productExistsInCart = cartDetailRepository.existsByProductIdAndCartId(product.getId(), CartId);

		if (productExistsInCart) {
			// Sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
			CartDetail cartDetail = cartDetailRepository.findByProductIdAndCartId(product.getId(), CartId);
			cartDetail.setAmount(cartDetail.getAmount() + amount);

			// Price = price * amount
			cartDetail.setPrice(product.getPrice() * cartDetail.getAmount());

		} else {
			// Sản phẩm chưa tồn tại trong giỏ hàng, tạo mới chi tiết giỏ hàng
			CartDetail cartDetail = new CartDetail();
			cartDetail.setCart(cart);
			cartDetail.setAmount(amount);
			cartDetail.setProduct(product);
			cartDetail.setPrice(product.getPrice());
			cartDetailRepository.save(cartDetail);
		}

		// Lưu giỏ hàng
		return cartRepository.save(cart);
	}

	public Cart updateCartItem(CartDetail cartDetail, int amount) {
		// Cập nhật số lượng sản phẩm trong chi tiết giỏ hàng
		cartDetail.setAmount(amount);

		// Lưu chi tiết giỏ hàng
		cartDetailRepository.save(cartDetail);

		// Lấy lại giỏ hàng để cập nhật tổng số tiền, nếu cần
		return cartRepository.findById(cartDetail.getCart().getId()).orElse(null);
	}

	public void removeItemFromCart(CartDetail cartDetail) {
		// Xóa sản phẩm khỏi giỏ hàng
		cartDetailRepository.delete(cartDetail);
	}

	// remove product in cart by product id
	public void removeProductInCartByProductId(User user, Integer productId) {

		Product product = productRepository.findById(productId).orElse(null);

		if (product == null) {
			return;
		}

		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			return;
		}
		Long cartId = cart.getId();
		// Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
		boolean productExistsInCart = cartDetailRepository.existsByProductIdAndCartId(product.getId(), cartId);

		if (productExistsInCart) {
			// Sản phẩm đã tồn tại trong giỏ hàng và số lượng > 1
			CartDetail cartDetail = cartDetailRepository.findByProductIdAndCartId(productId, cartId);
			if (cartDetail.getAmount() > 1) {
				cartDetail.setAmount(cartDetail.getAmount() - 1);
				cartDetail.setPrice(cartDetail.getPrice() - cartDetail.getProduct().getPrice());

				cartDetailRepository.save(cartDetail);

			} else {
				// Sản phẩm đã tồn tại trong giỏ hàng và số lượng = 1 , xóa productInCart
				cartDetailRepository.delete(cartDetail);
			}

		}
	}
}

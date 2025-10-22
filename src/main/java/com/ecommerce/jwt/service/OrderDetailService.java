package com.ecommerce.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.configuration.JwtRequestFilter;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.OrderDetail;
import com.ecommerce.jwt.entity.OrderInput;
import com.ecommerce.jwt.entity.OrderProductQuantity;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.TransactionDetails;
import com.ecommerce.jwt.entity.User;
import com.ecommerce.jwt.repository.CartRepository;
import com.ecommerce.jwt.repository.OrderDetailRepository;
import com.ecommerce.jwt.repository.ProductRepository;
import com.ecommerce.jwt.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class OrderDetailService {

	public static final String ORDER_PLACED= "Placed";
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	private static final String KEY= "rzp_test_RVpXdsyeoEMvEn";
	
	private static final String KEY_SECRET= "8tiupqcA13FekLaii2dv8WLX";
	
	private static final String CURRENCY= "INR";
	
	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<OrderProductQuantity> productQuantityList= orderInput.getOrderProductQuantities();
		
		String currentUser= JwtRequestFilter.CURRENT_USER;
		User user=	userRepository.findByUserName(currentUser).get();
			
		for(OrderProductQuantity o: productQuantityList) {
			Product product= productRepository.findById(o.getProductId()).get();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderFullName(orderInput.getFullName());
			orderDetail.setOrderFullAddres(orderInput.getFullAddress());
			orderDetail.setOrderContactNumber(orderInput.getContactNumber());
			orderDetail.setOrderAlternameContactNumber(orderInput.getAlternateContactNumber());
			orderDetail.setOrderStatus(ORDER_PLACED);
			orderDetail.setOrderAmount(product.getProductDiscountedPrice() * o.getQuantity());
			orderDetail.setProduct(product);
			orderDetail.setUser(user);
			orderDetail.setTransactionId(orderInput.getTransactionId());
			
			orderDetailRepository.save(orderDetail);
			
			//empty cart
			if(!isSingleProductCheckout) {
				Optional<Cart> carts= cartRepository.findByUser(user);
				carts.stream().forEach(x-> cartRepository.deleteById(x.getCartId()));
			}
		}
	}
	
	public List<OrderDetail> getOrderDetails() {
		 String username = JwtRequestFilter.CURRENT_USER;
		 
		 User user= userRepository.findByUserName(username)
				 .orElseThrow(() -> new RuntimeException("User not found"));
		 
		 return orderDetailRepository.findByUser(user);
	}
	
	public List<OrderDetail> getAllOrderDetails(String orderStatus){
		List<OrderDetail> orderDetails= new ArrayList<>();
		
		if(orderStatus.equals("All")) {
		 orderDetailRepository.findAll().forEach(x-> orderDetails.add(x));
		}
		else {
			orderDetailRepository.findByOrderStatus(orderStatus).forEach(x-> orderDetails.add(x));
		}
		return orderDetails;
		
	}
	
	public void markOrderAsDelivered(Long orderId) {
		OrderDetail orderDetail= orderDetailRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		
		if(orderDetail != null) {
			orderDetail.setOrderStatus("Delivered");
			orderDetailRepository.save(orderDetail);
		}
	}
	
	public TransactionDetails createTransaction(Double amount) {
		try {
			JSONObject jsonObject= new JSONObject();
			jsonObject.put("amount", (amount * 100));
			jsonObject.put("currency", CURRENCY);
			
			RazorpayClient razorpayClient= new RazorpayClient(KEY, KEY_SECRET);
			Order order= razorpayClient.orders.create(jsonObject);
			TransactionDetails transactionDetails= prepareTransactionDetails(order);
			return transactionDetails;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
	    String currency = order.get("currency");
	    //Double amount = order.get("amount");
	    Number amountValue = (Number) order.get("amount");
	    Double amount = amountValue.doubleValue() / 100.0;
		
		TransactionDetails transactionDetails= new TransactionDetails(orderId, currency, amount, KEY);
		return transactionDetails;
	}
	
	
}

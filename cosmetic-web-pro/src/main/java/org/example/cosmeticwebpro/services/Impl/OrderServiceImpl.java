package org.example.cosmeticwebpro.services.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.domains.OrderDetail;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
import org.example.cosmeticwebpro.models.OrderReviewDTO;
import org.example.cosmeticwebpro.repositories.CartLineRepository;
import org.example.cosmeticwebpro.repositories.DiscountRepository;
import org.example.cosmeticwebpro.repositories.OrderDetailRepository;
import org.example.cosmeticwebpro.repositories.OrderRepository;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.AddressService;
import org.example.cosmeticwebpro.services.OrderService;
import org.example.cosmeticwebpro.services.ProductService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final DiscountRepository discountRepository;
  private final UserRepository userRepository;
  private final CartLineRepository cartLineRepository;
  private final ProductService productService;
  private final AddressService addressService;

  // find a list order for a user
  @Override
  public List<Order> getAllOrderForAUser(Long userId) throws CosmeticException {
    if (userId == null) {
      throw new CosmeticException(
          ExceptionUtils.USER_ID_IS_NOT_EMPTY,
          ExceptionUtils.messages.get(ExceptionUtils.USER_ID_IS_NOT_EMPTY));
    }
    return orderRepository.findAllByUserId(userId);
  }

  // show detail an order

//  Sai cần xem lại
  @Override
  public OrderDetailDTO showDetailAnOrder(Long orderId) throws CosmeticException {
    if (orderId == null) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_ERROR_1, ExceptionUtils.messages.get(ExceptionUtils.ORDER_ERROR_1));
    }
    var order = orderRepository.findById(orderId);
    if (order.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ORDER_NOT_FOUND));
    }
    var orderDetailList = orderDetailRepository.findAllByOrderId(orderId);

    // Calculate total amount of the order
    double totalAmount =
        orderDetailList.stream()
            .mapToDouble(od -> od.getProductCost() * od.getQuantity() - od.getDiscountProduct())
            .sum();
    return OrderDetailDTO.builder()
        .order(order.get())
        .orderDetail(orderDetailList)
        .totalAmount(totalAmount)
        .build();
  }

  // update status of an order
  @Override
  public void updateStatusOfAnOrder(Long orderId) throws CosmeticException {}

  // create an order for a user
  @Override
  public OrderDetailDTO createAnOrder(Long userId, Address address, Long discountId)
      throws CosmeticException {
    if (userId == null || address.getId() == null) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_ERROR_2, ExceptionUtils.messages.get(ExceptionUtils.ORDER_ERROR_2));
    }
    // Fetch the user
    var user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new CosmeticException(
                        ExceptionUtils.USER_NOT_FOUND,
                        ExceptionUtils.messages.get(ExceptionUtils.USER_NOT_FOUND)));

    // Create a new order
    Order order =
        Order.builder()
            .fullName(address.getFullName())
            .phoneNumber(address.getPhoneNumber())
            .addressDetail(
                address.getProvinceName()
                    + " - "
                    + address.getDistrictName()
                    + " - "
                    + address.getWardName()
                    + " - "
                    + address.getAddressDetail())
            .orderDate(LocalDateTime.now())
            .build();

    // Save the order first to get the generated ID
    order = orderRepository.save(order);

    // Calculate the total cost (dummy data for order details here, you should fetch actual
    // products)
    List<OrderDetail> orderDetailList = getOrderDetailsForUser(order.getId(), userId);
    // You should implement this method to fetch actual order details
    double totalCost = 0.0;

    for (OrderDetail od : orderDetailList) {
      od.setOrderId(order.getId());
      totalCost += od.getProductCost() * od.getQuantity();
      orderDetailRepository.save(od);
    }
    // set shipping cost
    if (totalCost < Constants.ORDER_THRESHOLD_FOR_SHIPPING_COST) {
      order.setShippingCost(Constants.SHIPPING_COST);
    }

    // Fetch the discount if provided
    Discount discount = null;
    if (discountId != null && discountId != 0) {
      discount = discountRepository.findById(discountId).orElse(null);
    }

    // Apply discount if available
    double discountAmount = 0.0;
    if (discount != null) {
      discountAmount = totalCost * discount.getDiscountPercent() / 100;
      totalCost -= discountAmount;
      order.setDiscountOrder(discount.getDiscountPercent());
    }
    order.setTotalCost(totalCost);
    order.setStatus(Constants.ORDER_PLACED_SUCCESS);
    orderRepository.save(order);

    return OrderDetailDTO.builder()
        .order(order)
        .orderDetail(orderDetailList)
        .totalAmount(totalCost)
        .build();
  }

  // review the order before creating a new order
  @Override
  public OrderReviewDTO orderReview(Long userId) throws CosmeticException {
    var addressList = addressService.getAllAddress(userId);

    return null;
  }

  private List<OrderDetail> getOrderDetailsForUser(Long orderId, Long userId)
      throws CosmeticException {
    List<OrderDetail> orderDetails = new ArrayList<>();
    // Implement this method to fetch actual order details for the user
    // For now, returning a dummy list
    var cartLines = cartLineRepository.findAllByUserId(userId);
    for (CartLine c : cartLines) {
      var productDetail = productService.getByProductId(c.getProductId());
      var p = productDetail.getProduct();
      var i = productDetail.getProductImages();
      var productCost = p.getCurrentCost();
      if (p.getDisCountId() != null) {
        var discount = discountRepository.findById(p.getDisCountId());
        if (discount.isEmpty()) {
          throw new CosmeticException(
              ExceptionUtils.DISCOUNT_NOT_FOUND,
              ExceptionUtils.messages.get(ExceptionUtils.DISCOUNT_NOT_FOUND));
        }
        productCost = productCost - productCost * (discount.get().getDiscountPercent() / 100);
      }
      LocalDateTime today = LocalDateTime.now();
      OrderDetail orderDetail =
          OrderDetail.builder()
              .productId(c.getProductId())
              .orderId(orderId)
              .productTitle(p.getTitle())
              .productImageUrl(String.valueOf(i.get(1)))
              .productCost(productCost)
              .createdDate(today)
              .modifiedDate(today)
              .build();
      orderDetails.add(orderDetail);
    }
    // Add logic to fetch actual order details
    return orderDetails;
  }
}

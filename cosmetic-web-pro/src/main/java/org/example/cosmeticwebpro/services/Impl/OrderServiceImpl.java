package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.domains.OrderDetail;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
import org.example.cosmeticwebpro.models.OrderReviewDTO;
import org.example.cosmeticwebpro.models.request.OrderReqDTO;
import org.example.cosmeticwebpro.repositories.CartLineRepository;
import org.example.cosmeticwebpro.repositories.DiscountRepository;
import org.example.cosmeticwebpro.repositories.OrderDetailRepository;
import org.example.cosmeticwebpro.repositories.OrderRepository;
import org.example.cosmeticwebpro.services.AddressService;
import org.example.cosmeticwebpro.services.CartService;
import org.example.cosmeticwebpro.services.OrderService;
import org.example.cosmeticwebpro.services.ProductService;
import org.example.cosmeticwebpro.services.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final DiscountRepository discountRepository;
  private final UserService userService;
  private final CartLineRepository cartLineRepository;
  private final ProductService productService;
  private final AddressService addressService;
  private final CartService cartService;

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
    var order = this.getByOrderId(orderId);
    var orderDetailList = orderDetailRepository.findAllByOrderId(orderId);

    // Calculate total amount of the order
    double totalAmount =
        orderDetailList.stream()
            .mapToDouble(od -> od.getProductCost() * od.getQuantity() - od.getDiscountProduct())
            .sum();
    return OrderDetailDTO.builder()
        .order(order)
        .orderDetail(orderDetailList)
        .totalAmount(totalAmount)
        .build();
  }

  // update status of an order
  @Override
  public void updateStatusOfAnOrder(Long orderId) throws CosmeticException {}

  // create an order for a user
  @Transactional
  @Override
  public OrderDetailDTO createAnOrder(OrderReqDTO orderReqDTO) throws CosmeticException {
    var address = orderReqDTO.getAddress();
    if (orderReqDTO.getUserId() == null || address.getId() == null) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_ERROR_2, ExceptionUtils.messages.get(ExceptionUtils.ORDER_ERROR_2));
    }
    // check user exist
    userService.viewDetailAUser(orderReqDTO.getUserId());
    // Create a new order
    Order orderSetUp =
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
    var order = orderRepository.save(orderSetUp);

    // Calculate the total cost (dummy data for order details here, you should fetch actual
    // products)
    List<OrderDetail> orderDetailList =
        createOrderDetailsForUser(order.getId(), orderReqDTO.getUserId());
    // You should implement this method to fetch actual order details
    double totalCost = 0.0;

    // todo
    // check - product discount
    for (OrderDetail od : orderDetailList) {
      od.setOrderId(order.getId());
      totalCost += od.getProductCost() * od.getQuantity();
      orderDetailRepository.save(od);
    }
    double shippingCost = 0;
    // set shipping cost
    if (totalCost < Constants.ORDER_THRESHOLD_FOR_SHIPPING_COST) {
      shippingCost = Constants.SHIPPING_COST;
    }
    order.setShippingCost(shippingCost);
    order.setTotalItem(orderDetailList.size() - 1);
    order.setNote(orderReqDTO.getNote());
    order.setStatus(Constants.ORDER_PLACED_SUCCESS);
    order.setPaymentMethod(Constants.PAYMENT_CASH);
    order.setUserId(orderReqDTO.getUserId());

    OrderDetailDTO orderDetailDTO =
        OrderDetailDTO.builder().order(order).orderDetail(orderDetailList).build();

    // Apply discount if available
    double discountAmount = 0.0;
    var discount = orderReqDTO.getDiscount();
    if (discount != null) {
      discountAmount = totalCost * discount.getDiscountPercent() / 100;
      totalCost = totalCost - discountAmount;
      order.setDiscountOrder(discount.getDiscountPercent());
      orderDetailDTO.setDiscountOrder(discount);
    }
    order.setTotalCost(totalCost);
    orderDetailDTO.setTotalAmount(totalCost);
    orderRepository.save(order);
    // clear shopping cart by userId
    cartService.clearCartLine(orderReqDTO.getUserId());
    return orderDetailDTO;
  }

  // cancel an order
  @Transactional
  @Override
  public Order cancelAnOrder(Long orderId) throws CosmeticException {
    var order = this.getByOrderId(orderId);
    order.setStatus(Constants.ORDER_CANCELLED);
    return order;
  }

  @Transactional
  protected List<OrderDetail> createOrderDetailsForUser(Long orderId, Long userId)
      throws CosmeticException {
    List<OrderDetail> orderDetails = new ArrayList<>();
    // Implement this method to fetch actual order details for the user
    // For now, returning a dummy list
    var cartLines = cartLineRepository.findAllByUserId(userId);
    for (CartLine c : cartLines) {
      var productDetail = productService.getByProductId(c.getProductId());
      var p = productDetail.getProductDTO();
      var i = productDetail.getProductImages();
      var productCost = p.getCurrentCost();
      LocalDateTime today = LocalDateTime.now();
      OrderDetail orderDetail =
          OrderDetail.builder()
              .productId(c.getProductId())
              .orderId(orderId)
              .productTitle(p.getTitle())
              .productImageUrl(String.valueOf(i.get(0)))
              .quantity(c.getQuantity())
              .createdDate(today)
              .modifiedDate(today)
              .build();
      if (p.getProductDiscount() != null) {
        var discount =
            discountRepository.findByIdAndStatus(p.getProductDiscount().getId(), Constants.ACTIVE);
        if (discount.isPresent()
            & Objects.equals(discount.get().getDiscountStatus(), Constants.ACTIVE)) {
          productCost =
              productCost - productCost * ((double) discount.get().getDiscountPercent() / 100);
          orderDetail.setDiscountProduct(discount.get().getDiscountPercent());
        }
      }
      orderDetail.setProductCost(productCost);
      orderDetails.add(orderDetail);
      var quantity = productDetail.getProductDTO().getQuantity();
      productDetail.getProductDTO().setQuantity(quantity - c.getQuantity());
    }
    // Add logic to fetch actual order details
    return orderDetails;
  }

  private Order getByOrderId(Long orderId) throws CosmeticException {
    var order = orderRepository.findById(orderId);
    if (order.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ORDER_NOT_FOUND));
    }
    return order.get();
  }
}

package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.domains.OrderDetail;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.DisplayOrderDTO;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
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
  public DisplayOrderDTO showDetailAnOrder(Long orderId) throws CosmeticException {
    if (orderId == null) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_ERROR_1, ExceptionUtils.messages.get(ExceptionUtils.ORDER_ERROR_1));
    }
    var order = this.getByOrderId(orderId);
    var orderDetailList = orderDetailRepository.findAllByOrderId(orderId);

    DisplayOrderDTO displayOrderDTO = new DisplayOrderDTO();
    // Calculate total amount of the order
    double totalAmount = order.getTotalCost();
    if(order.getDiscountOrder() != null){
      totalAmount = totalAmount - totalAmount*order.getDiscountOrder();
    }
    displayOrderDTO.setOrder(order);
    displayOrderDTO.setOrderDetail(orderDetailList);
    displayOrderDTO.setDiscountPercent(order.getDiscountOrder());
    displayOrderDTO.setTotalAmount(totalAmount);
    return displayOrderDTO;
  }

  // create an order for a user
  @Transactional
  @Override
  public OrderDetailDTO createAnOrder(OrderReqDTO orderReqDTO) throws CosmeticException {
    var address = orderReqDTO.getAddress();
    if (orderReqDTO.getUserId() == null ) {
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

  // update status an order for a user
  @Transactional
  @Override
  public Order updateStatusAnOrderForUser(Long orderId, String newStatus) throws CosmeticException {
    // Fetch the current order
    var order = this.getByOrderId(orderId);
    String currentOrderStatus = order.getStatus();

    // Define valid status transitions for user updates
    Map<String, List<String>> validStatusCancel =
        Map.of(
            Constants.ORDER_PLACED_SUCCESS, List.of(Constants.ORDER_CANCELLED),
            Constants.SELLER_PREPARING_ORDER, List.of(Constants.ORDER_CANCELLED),
            Constants.IN_TRANSIT, List.of(),
            Constants.DELIVERY_SUCCESSFUL,
                List.of(Constants.ORDER_RECEIVED, Constants.RETURNED_AND_REFUNDED),
            Constants.DELIVERY_FAILED, List.of(),
            Constants.ORDER_RECEIVED, List.of(),
            Constants.RETURNED_AND_REFUNDED, List.of(),
            Constants.ORDER_CANCELLED, List.of());

    // Check if the new status is one of the allowed statuses for user updates
    if (!newStatus.equals(Constants.ORDER_CANCELLED)
        && !newStatus.equals(Constants.ORDER_RECEIVED)
        && !newStatus.equals(Constants.RETURNED_AND_REFUNDED)) {
      throw new CosmeticException(
          ExceptionUtils.NOT_PERMISSION,
          ExceptionUtils.messages.get(ExceptionUtils.NOT_PERMISSION));
    }

    // Check if the transition is valid
    if (!validStatusCancel.getOrDefault(currentOrderStatus, List.of()).contains(newStatus)) {
      throw new CosmeticException(
          ExceptionUtils.NOT_PERMISSION,
          ExceptionUtils.messages.get(ExceptionUtils.NOT_PERMISSION));
    }

    // Update the order status
    order.setStatus(newStatus);
    return orderRepository.save(order);
  }

  @Override
  public List<Order> searchOrderByStatusForAdmin(String orderStatus) throws CosmeticException {
    if (orderStatus == null || orderStatus.isEmpty()) {
      return orderRepository.findAll();
    }
    // otherwise, filter orders by the given status
    return orderRepository.findAllByStatus(orderStatus);
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

  @Override
  public Order getByOrderId(Long orderId) throws CosmeticException {
    var order = orderRepository.findById(orderId);
    if (order.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ORDER_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ORDER_NOT_FOUND));
    }
    return order.get();
  }

  @Transactional
  @Override
  public Order updateStatusAnOrderForAdmin(Long orderId, String newStatus)
      throws CosmeticException {
    // Fetch the order
    Order order = this.getByOrderId(orderId);

    // Get current status
    List<String> allowedStatuses = checkAllowStatus(order);
    if (allowedStatuses == null || !allowedStatuses.contains(newStatus)) {
      throw new CosmeticException(
          ExceptionUtils.INVALID_STATUS_TRANSITION,
          ExceptionUtils.messages.get(ExceptionUtils.INVALID_STATUS_TRANSITION));
    }

    // Update the status
    order.setStatus(newStatus);

    // Update shipping_date when status changes to IN_TRANSIT
    if (newStatus.equals(Constants.IN_TRANSIT)) {
      order.setShippingDate(LocalDateTime.now());
    }

    // Update compilation_date when status changes to ORDER_RECEIVED, DELIVERY_FAILED, or
    // RETURNED_AND_REFUNDED
    if (newStatus.equals(Constants.ORDER_RECEIVED)
        || newStatus.equals(Constants.DELIVERY_FAILED)
        || newStatus.equals(Constants.RETURNED_AND_REFUNDED)) {
      order.setCompletionDate(LocalDateTime.now());
    }

    order = orderRepository.save(order);

    return order;
  }

  private static List<String> checkAllowStatus(Order order) {
    String currentStatus = order.getStatus();

    Map<String, List<String>> validStatusTransitions =
        Map.of(
            Constants.ORDER_PLACED_SUCCESS,
            List.of(Constants.SELLER_PREPARING_ORDER, Constants.ORDER_CANCELLED),
            Constants.SELLER_PREPARING_ORDER,
            List.of(Constants.IN_TRANSIT, Constants.ORDER_CANCELLED),
            Constants.IN_TRANSIT, List.of(Constants.DELIVERY_SUCCESSFUL, Constants.DELIVERY_FAILED),
            Constants.DELIVERY_SUCCESSFUL, List.of(Constants.ORDER_RECEIVED),
            Constants.DELIVERY_FAILED, List.of(Constants.RETURNED_AND_REFUNDED),
            Constants.ORDER_RECEIVED, List.of(),
            Constants.RETURNED_AND_REFUNDED, List.of(),
            Constants.ORDER_CANCELLED, List.of());

    // Check if the new status is a valid transition
    List<String> allowedStatuses = validStatusTransitions.get(currentStatus);
    return allowedStatuses;
  }
}

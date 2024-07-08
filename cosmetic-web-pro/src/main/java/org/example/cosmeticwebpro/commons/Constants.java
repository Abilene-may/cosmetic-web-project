package org.example.cosmeticwebpro.commons;

/**
 * @author: Nga
 * management of the constants
 */
public class Constants {
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ACTIVE = "Active";
    public static final String PENDING_DELETION = "Pending deletion";
    public static final String DE_ACTIVE = "De active";
    public static final String UPCOMING = "upcoming";
    public static final String EXPIRED = "expired";

    // product management
    public static final String IN_STOCK = "Còn hàng";
    public static final String OUT_OF_STOCK= "Hết hàng";
    public static final String PRODUCT_HIDDEN = "Ẩn";
    public static final String ON_SALE = "Đang giảm giá";

    // order status
    public static final String ORDER_PLACED_SUCCESS = "Đặt hàng thành công.";
    public static final String ORDER_CANCELLED = "Đơn hàng đã bị hủy.";
    public static final String SELLER_PREPARING_ORDER = "Người bán đang chuẩn bị hàng.";
    public static final String IN_TRANSIT = "Đang giao hàng.";
    public static final String DELIVERY_SUCCESSFUL = "Giao hàng thành công.";
    public static final String DELIVERY_FAILED = "Giao hàng không thành công.";
    public static final String ORDER_RECEIVED = "Đã nhận hàng.";
    public static final String RETURNED_AND_REFUNDED = "Trả hàng /Hoàn tiền.";
    public static final String PRODUCT = "product";
    public static final String ORDER = "order";
    public static final double ORDER_THRESHOLD_FOR_SHIPPING_COST = 500000;
    public static final double SHIPPING_COST = 35000;

    // payment method
  public static final String PAYMENT_CASH = "Thanh toán tiền mặt.";

}

package org.example.cosmeticwebpro.exceptions;

import java.util.HashMap;
import java.util.Map;

/** Class declares exceptions */
public class ExceptionUtils {

  public static final String USER_SIGNUP_1 = "USER_SIGNUP_1";
  public static final String E_INTERNAL_SERVER = "E_INTERNAL_SERVER";
  public static final String SIGNUP_ERROR_NULL_1 = "SIGNUP_ERROR_NULL_1";
  public static final String SIGNUP_ERROR_NULL_2 = "SIGNUP_ERROR_NULL_2";

  // user
  public static final String USER_NOT_FOUND = "USER_IS_NOT_EMPTY";
  public static final String USERNAME_HAS_ALREADY = "USERNAME_HAS_ALREADY";

  // role
  public static final String ROLE_NAME_IS_NOT_BLANK = "ROLE_NAME_IS_NOT_BLANK";
  public static final String ROLE_NOT_FOUND = "ROLE_NOT_FOUND";

  // product
  public static final String PRODUCT_ID_IS_NOT_EXIST = "PRODUCT_ID_IS_NOT_EXIST";
  public static final String PRODUCT_HAS_BEEN_HIDDEN = "PRODUCT_HAS_BEEN_HIDDEN";
  public static final String PRODUCT_HAS_NO_QUANTITY_YET = "PRODUCT_HAS_NO_QUANTITY_YET";
  public static final String PRODUCTS_NOT_FOUND = "PRODUCTS_NOT_FOUND";
  public static final String PRODUCT_ERROR_1 = "PRODUCT_ERROR_1";
  public static final String PRODUCT_ERROR_2 = "PRODUCT_ERROR_2";
  public static final String PRODUCT_ERROR_3 = "PRODUCT_ERROR_3";

  // brand
  public static final String BRAND_ID_NOT_FOUND = "BRAND_ID_NOT_FOUND";

  // category
  public static final String CATEGORY_NOT_FOUND = "CATEGORY_NOT_FOUND";
  public static final String CATEGORY_NAME_NOT_FOUND = "CATEGORY_NAME_NOT_FOUND";

  // cart
  public static final String PRODUCT_IS_NOT_FOUND_IN_THE_CART = "PRODUCT_IS_NOT_FOUND_IN_THE_CART";

  // address
  public static final String ADDRESS_IS_NOT_FOUND = "ADDRESS_IS_NOT_FOUND";
  public static final String ADDRESS_ERROR_1 = "ADDRESS_ERROR_1";
  public static final String ADDRESS_ERROR_2 = "ADDRESS_ERROR_2";
  public static final String ADDRESS_ERROR_3 = "ADDRESS_ERROR_3";
  public static final String ADDRESS_ERROR_4 = "ADDRESS_ERROR_4";
  public static final String ADDRESS_ERROR_5 = "ADDRESS_ERROR_5";
  public static final String USER_ID_IS_NOT_EMPTY = "USER_ID_IS_NOT_EMPTY";

  // order
  public static final String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";
  public static final String ORDER_ERROR_1 = "ORDER_ERROR_1";
  public static final String ORDER_ERROR_2 = "ORDER_ERROR_2";

  public static Map<String, String> messages;

  static {
    messages = new HashMap<>();
    messages.put(ExceptionUtils.USER_SIGNUP_1, "Email đã được đăng ký. Vui lòng nhập Email khác.");
    messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server không phản hồi.");
    messages.put(ExceptionUtils.SIGNUP_ERROR_NULL_1, "Vui lòng nhập Email.");
    messages.put(ExceptionUtils.SIGNUP_ERROR_NULL_2, "Vui lòng nhập Password.");

    // user
    messages.put(
        ExceptionUtils.USER_NOT_FOUND,
        "Không tìm thấy thông tin người dùng hoặc người dùng không tồn tại.");
    messages.put(
        ExceptionUtils.USERNAME_HAS_ALREADY, "username đã tồn tại vui lòng chọn username khác.");

    // role
    messages.put(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK, "Tên vai trò không được bỏ trống.");
    messages.put(
        ExceptionUtils.ROLE_NOT_FOUND, "Không tìm thấy vai trò hoặc vai trò không tồn tại.");

    // product
    messages.put(
        ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin của sản phẩm hoặc sản phẩm không tồn tại.");
    messages.put(ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN, "Sản phẩm đã bị ẩn hoặc không tồn tại.");
    messages.put(ExceptionUtils.PRODUCT_HAS_NO_QUANTITY_YET, "Vui lòng nhâp số lượng sản phẩm.");
    messages.put(ExceptionUtils.PRODUCTS_NOT_FOUND, "Chưa có sản phẩm nào trong danh sách.");
    messages.put(ExceptionUtils.PRODUCT_ERROR_1, "Vui lòng nhập Tiêu đề của sản phẩm.");
    messages.put(ExceptionUtils.PRODUCT_ERROR_2, "Vui lòng nhập giá của sản phẩm.");
    messages.put(ExceptionUtils.PRODUCT_ERROR_3, "Vui lòng nhập Made in của sản phẩm.");

    // brand
    messages.put(
        ExceptionUtils.BRAND_ID_NOT_FOUND,
        "Không tìm thấy thông tin của brand hoặc brand không tồn tại.");

    // categpry
    messages.put(ExceptionUtils.CATEGORY_NOT_FOUND, "Không tìm thấy thông tin danh mục hàng.");
    messages.put(ExceptionUtils.CATEGORY_NAME_NOT_FOUND, "Tên danh mục không được bỏ trống.");

    // cart
    messages.put(
        ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART,
        "Không tìm thấy thông tin sản phẩm trong giỏ hàng.");

    // address
    messages.put(
        ExceptionUtils.ADDRESS_IS_NOT_FOUND,
        "Không tìm thấy thông tin của địa chỉ hoặc địa chỉ không tồn tại.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_1, "Vui lòng nhập Họ và tên.");

    messages.put(ExceptionUtils.ADDRESS_ERROR_2, "Vui lòng nhập Số điện thoại.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_3, "Vui lòng nhập Tỉnh/Thành phố.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_4, "Vui lòng nhập Quận/Huyện.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_5, "Vui lòng nhập Xã/Phường.");
    messages.put(ExceptionUtils.USER_ID_IS_NOT_EMPTY, "Trường userId không được bỏ trống.");

    // order
    messages.put(
        ExceptionUtils.ORDER_NOT_FOUND,
        "Không tìm thấy thông tin đơn hàng hoặc đơn hàng không tồn tại.");
    messages.put(ExceptionUtils.ORDER_ERROR_1, "orderId không được bỏ trống.");
    messages.put(ExceptionUtils.ORDER_ERROR_2, "userId và addressId không được bỏ trống.");
  }
}

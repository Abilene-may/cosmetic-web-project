package org.example.cosmeticwebpro.exceptions;

import java.util.HashMap;
import java.util.Map;

/** Class declares exceptions */
public class ExceptionUtils {

  public static final String USER_SIGNUP_1 = "USER_SIGNUP_1";
  public static final String E_INTERNAL_SERVER = "E_INTERNAL_SERVER";
  public static final String SIGNUP_ERROR_NULL_1 = "SIGNUP_ERROR_NULL_1";
  public static final String SIGNUP_ERROR_NULL_2 = "SIGNUP_ERROR_NULL_2";
  public static final String EMAIL_IS_NOT_REGISTERED = "EMAIL_IS_NOT_REGISTERED";

  // user
  public static final String USER_NOT_FOUND = "USER_IS_NOT_EMPTY";
  public static final String USERNAME_HAS_ALREADY = "USERNAME_HAS_ALREADY";
  public static final String ACCOUNT_DEACTIVATED = "ACCOUNT_DEACTIVATED";
  public static final String USER_REQ_NOT_EMPTY = "USER_REQ_NOT_EMPTY";
  public static final String PASSWORD_NOT_MATCH = "PASSWORD_NOT_MATCH";

  // role
  public static final String ROLE_NAME_IS_NOT_BLANK = "ROLE_NAME_IS_NOT_BLANK";
  public static final String ROLE_NOT_FOUND = "ROLE_NOT_FOUND";
  public static final String CANNOT_DELETE = "CANNOT_DELETE";
  public static final String ROLE_NAME_NOT_EMPTY = "ROLE_NAME_NOT_EMPTY";
  public static final String ROLE_CANNOT_BE_DELETED = "ROLE_CANNOT_BE_DELETED";

  // product
  public static final String PRODUCT_ID_IS_NOT_EXIST = "PRODUCT_ID_IS_NOT_EXIST";
  public static final String PRODUCT_HAS_BEEN_HIDDEN = "PRODUCT_HAS_BEEN_HIDDEN";
  public static final String PRODUCT_HAS_NO_QUANTITY_YET = "PRODUCT_HAS_NO_QUANTITY_YET";
  public static final String PRODUCTS_NOT_FOUND = "PRODUCTS_NOT_FOUND";
  public static final String PRODUCT_ERROR_1 = "PRODUCT_ERROR_1";
  public static final String PRODUCT_OUT_OF_STOCK = "PRODUCT_OUT_OF_STOCK";
  public static final String IMAGE_NOT_FOUND = "IMAGE_NOT_FOUND";

  // discount
  public static final String DISCOUNT_NOT_FOUND = "DISCOUNT_NOT_FOUND";

  // brand
  public static final String BRAND_ID_NOT_FOUND = "BRAND_ID_NOT_FOUND";
  public static final String BRAND_NAME_NOT_EMPTY = "BRAND_NAME_NOT_EMPTY";

  // category
  public static final String CATEGORY_NOT_FOUND = "CATEGORY_NOT_FOUND";
  public static final String CATEGORY_NAME_NOT_EMPTY = "CATEGORY_NAME_NOT_EMPTY";

  // cart
  public static final String CART_DOES_NOT_EXIST = "CART_DOES_NOT_EXIST";
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
  public static final String NOT_PERMISSION = "NOT_PERMISSION";
  public static final String INVALID_STATUS_TRANSITION = "INVALID_STATUS_TRANSITION";
  public static final String CART_LINE_IS_EMPTY = "CART_LINE_IS_EMPTY";

  // product review
  public static final String PRODUCT_REVIEW_NOT_FOUND = "PRODUCT_REVIEW_NOT_FOUND";
  public static final String PRODUCT_REVIEW_ERROR_1 = "PRODUCT_REVIEW_ERROR_1";
  public static final String PRODUCT_REVIEW_ERROR_2 = "PRODUCT_REVIEW_ERROR_2";
  public static final String PRODUCT_REVIEW_ERROR_3 = "PRODUCT_REVIEW_ERROR_3";
  public static final String PRODUCT_REVIEW_ERROR_4 = "PRODUCT_REVIEW_ERROR_4";

  public static Map<String, String> messages;

  static {
    messages = new HashMap<>();
    messages.put(
        ExceptionUtils.USER_SIGNUP_1, "The Email was registered. Please enter another Email.");
    messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server is not responding.");
    messages.put(ExceptionUtils.SIGNUP_ERROR_NULL_1, "Please enter Email.");
    messages.put(ExceptionUtils.SIGNUP_ERROR_NULL_2, "Please enter Password.");
    messages.put(ExceptionUtils.EMAIL_IS_NOT_REGISTERED, "Email is not registered. Please check again.");
    messages.put(ExceptionUtils.IMAGE_NOT_FOUND, "Image is not found.");

    // user
    messages.put(
        ExceptionUtils.USER_NOT_FOUND, "User information not found or user does not exist.");
    messages.put(
        ExceptionUtils.USERNAME_HAS_ALREADY,
        "username already exists, please choose another username.");
    messages.put(ExceptionUtils.ACCOUNT_DEACTIVATED, "Your account has been disabled.");
    messages.put(
        ExceptionUtils.USER_REQ_NOT_EMPTY,
        "Emai, username, account status, password, roleid cannot be left blank.");
    messages.put(ExceptionUtils.PASSWORD_NOT_MATCH, "Passwords don't match.");

    // role
    messages.put(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK, "Role name cannot be left blank.");
    messages.put(ExceptionUtils.ROLE_NOT_FOUND, "Role not found or role does not exist.");
    messages.put(ExceptionUtils.CANNOT_DELETE, "USER and ADMIN roles cannot be deleted.");
    messages.put(ExceptionUtils.ROLE_NAME_NOT_EMPTY, "Role name cannot be left blank.");
    messages.put(ExceptionUtils.ROLE_CANNOT_BE_DELETED, "Cannot delete this role.");

    // product
    messages.put(
        ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST, "Product not found or product does not exist.");
    messages.put(
        ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN, "The product is hidden or does not exist.");
    messages.put(ExceptionUtils.PRODUCT_HAS_NO_QUANTITY_YET, "Please enter product quantity.");
    messages.put(ExceptionUtils.PRODUCTS_NOT_FOUND, "There are no products in the list yet.");
    messages.put(
        ExceptionUtils.PRODUCT_ERROR_1,
        "Please enter the Product's Title, product's cost, brandId and Made in.");
    messages.put(
        ExceptionUtils.PRODUCT_OUT_OF_STOCK,
        "This product is currently out of stock and cannot be restocked.");

    // discount
    messages.put(ExceptionUtils.DISCOUNT_NOT_FOUND, "No discount code information found.");

    // brand
    messages.put(
        ExceptionUtils.BRAND_ID_NOT_FOUND, "Brand information not found or brand does not exist.");
    messages.put(ExceptionUtils.BRAND_NAME_NOT_EMPTY, "Category name cannot be left blank.");

    // categpry
    messages.put(ExceptionUtils.CATEGORY_NOT_FOUND, "No product catalog information found.");
    messages.put(ExceptionUtils.CATEGORY_NAME_NOT_EMPTY, "Category name cannot be left blank.");

    // cart
    messages.put(
        ExceptionUtils.CART_DOES_NOT_EXIST, "Cart information not found or cart does not exist.");
    messages.put(
        ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART,
        "No product information found in the cart.");

    // address
    messages.put(
        ExceptionUtils.ADDRESS_IS_NOT_FOUND,
        "Address information could not be found or the address does not exist.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_1, "Please enter your first and last name.");

    messages.put(ExceptionUtils.ADDRESS_ERROR_2, "Please enter the phone number.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_3, "Please enter Province/City.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_4, "Please enter District.");
    messages.put(ExceptionUtils.ADDRESS_ERROR_5, "Please enter Ward.");
    messages.put(ExceptionUtils.USER_ID_IS_NOT_EMPTY, "The userId field cannot be left blank.");

    // order
    messages.put(
        ExceptionUtils.ORDER_NOT_FOUND, "Order information not found or order does not exist.");
    messages.put(ExceptionUtils.ORDER_ERROR_1, "the orderId field cannot be left blank.");
    messages.put(ExceptionUtils.ORDER_ERROR_2, "The userId field cannot be left blank.");
    messages.put(ExceptionUtils.NOT_PERMISSION, "Not permission.");
    messages.put(ExceptionUtils.INVALID_STATUS_TRANSITION, "Invalid state transition.");
    messages.put(ExceptionUtils.CART_LINE_IS_EMPTY, "Please add products before ordering.");

    // product review
    messages.put(
        ExceptionUtils.PRODUCT_REVIEW_NOT_FOUND, "No reviews found or reviews do not exist.");
    messages.put(ExceptionUtils.PRODUCT_REVIEW_ERROR_1, "Incomplete orders cannot be evaluated.");
    messages.put(
        ExceptionUtils.PRODUCT_REVIEW_ERROR_2,
        "The productId and orderId fields cannot be left blank.");
    messages.put(
        ExceptionUtils.PRODUCT_REVIEW_ERROR_3,
        "The product is not included in the order. Cannot rate.");
    messages.put(ExceptionUtils.PRODUCT_REVIEW_ERROR_4,
        "You have already rated the product and cannot rate it again.");
  }
}

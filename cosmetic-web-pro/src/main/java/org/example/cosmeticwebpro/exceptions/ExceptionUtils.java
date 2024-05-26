package org.example.cosmeticwebpro.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Class declares exceptions
 */
public class ExceptionUtils {

    public static final String USER_SIGNUP_1 = "USER_SIGNUP_1";
    public static final String E_INTERNAL_SERVER = "E_INTERNAL_SERVER";

    // product
    public static final String PRODUCT_ID_IS_NOT_EXIST = "PRODUCT_ID_IS_NOT_EXIST";
    public static final String PRODUCT_HAS_BEEN_HIDDEN = "PRODUCT_HAS_BEEN_HIDDEN";
    public static final String PRODUCT_HAS_NO_QUANTITY_YET = "PRODUCT_HAS_NO_QUANTITY_YET";
    public static final String PRODUCTS_NOT_FOUND = "PRODUCTS_NOT_FOUND";

    // brand
    public static final String BRAND_ID_NOT_FOUND = "BRAND_ID_NOT_FOUND";

    public static Map<String, String> messages;

    static {
        messages = new HashMap<>();
    messages.put(
        ExceptionUtils.USER_SIGNUP_1,
        "Email or Username has been registered. Please enter Email or other Username.");
    messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server không phản hồi.");
    // product
    messages.put(
        ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin của sản phẩm hoặc sản phẩm không tồn tại.");
    messages.put(
        ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN,
        "Sản phẩm đã bị ẩn hoặc không tồn tại.");
    messages.put(ExceptionUtils.PRODUCT_HAS_NO_QUANTITY_YET, "Vui lòng nhâp số lượng sản phẩm.");
    messages.put(ExceptionUtils.PRODUCTS_NOT_FOUND, "Chưa có sản phẩm nào trong danh sách.");

    // brand
    messages.put(ExceptionUtils.BRAND_ID_NOT_FOUND, "Không tìm thấy thông tin của brand hoặc brand không tồn tại.");

    }

}

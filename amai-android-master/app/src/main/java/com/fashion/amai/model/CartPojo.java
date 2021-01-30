package com.fashion.amai.model;

import java.util.List;


public class CartPojo {
    private boolean status;

    private String message;

    private Data data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public class Data
    {
        private int total_actualPrice;

        private int total_discountPrice;

        private int total_netPrice;

        private List<CartItems> cartItems;

        public int getTotal_actualPrice() {
            return total_actualPrice;
        }

        public int getTotal_discountPrice() {
            return total_discountPrice;
        }

        public int getTotal_netPrice() {
            return total_netPrice;
        }

        public List<CartItems> getCartItems() {
            return cartItems;
        }
    }

    public class CartItems
    {
        private int cart_id;

        private int product_id;

        private String product_name;

        private int product_quantity;

        private int actual_price;

        private String discount_per;

        private int net_price;

        private int total_discount;

        private String size;

        private String deliver_by;

        private int supplier_id;

        private String image;

        public int getCart_id() {
            return cart_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public int getProduct_quantity() {
            return product_quantity;
        }

        public int getActual_price() {
            return actual_price;
        }

        public String getDiscount_per() {
            return discount_per;
        }

        public int getNet_price() {
            return net_price;
        }

        public int getTotal_discount() {
            return total_discount;
        }

        public String getSize() {
            return size;
        }

        public String getDeliver_by() {
            return deliver_by;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public String getImage() {
            return image;
        }
    }

}

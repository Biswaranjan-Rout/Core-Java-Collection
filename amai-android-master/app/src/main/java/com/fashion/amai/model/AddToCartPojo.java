package com.fashion.amai.model;

public class AddToCartPojo {

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
        private int id;

        private int user_id;

        private int product_id;

        private String product_size;

        private int product_quantity;

        private String updated_at;

        private String created_at;

        public int getId() {
            return id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public String getProduct_size() {
            return product_size;
        }

        public int getProduct_quantity() {
            return product_quantity;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }
    }

}

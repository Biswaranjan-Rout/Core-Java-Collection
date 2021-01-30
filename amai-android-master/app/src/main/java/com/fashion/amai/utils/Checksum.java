package com.fashion.amai.utils;

public class Checksum {
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

    public class ChecksumGenData
    {
        private String MID;

        private String ORDER_ID;

        private String CUST_ID;

        private String INDUSTRY_TYPE_ID;

        private String CHANNEL_ID;

        private String TXN_AMOUNT;

        private String WEBSITE;

        private String CALLBACK_URL;

        private String EMAIL;

        private String MOBILE_NO;

        public String getMID() {
            return MID;
        }

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public String getCUST_ID() {
            return CUST_ID;
        }

        public String getINDUSTRY_TYPE_ID() {
            return INDUSTRY_TYPE_ID;
        }

        public String getCHANNEL_ID() {
            return CHANNEL_ID;
        }

        public String getTXN_AMOUNT() {
            return TXN_AMOUNT;
        }

        public String getWEBSITE() {
            return WEBSITE;
        }

        public String getCALLBACK_URL() {
            return CALLBACK_URL;
        }

        public String getEMAIL() {
            return EMAIL;
        }

        public String getMOBILE_NO() {
            return MOBILE_NO;
        }
    }

    public class OrderData
    {
        private int id;

        private int buyer_id;

        private int supplier_id;

        private String order_detail;

        private int order_status_code;

        private String order_date;

        private int order_type;

        private int product_id;

        private int product_quantity;

        private int product_price;

        private int product_discount_per;

        private String updated_at;

        private String created_at;

        private String order_number;

        public int getId() {
            return id;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public String getOrder_detail() {
            return order_detail;
        }

        public int getOrder_status_code() {
            return order_status_code;
        }

        public String getOrder_date() {
            return order_date;
        }

        public int getOrder_type() {
            return order_type;
        }

        public int getProduct_id() {
            return product_id;
        }

        public int getProduct_quantity() {
            return product_quantity;
        }

        public int getProduct_price() {
            return product_price;
        }

        public int getProduct_discount_per() {
            return product_discount_per;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getOrder_number() {
            return order_number;
        }
    }

    public class Data
    {
        private ChecksumGenData checksumGenData;

        private String checksum;

        private OrderData orderData;

        public ChecksumGenData getChecksumGenData() {
            return checksumGenData;
        }

        public String getChecksum() {
            return checksum;
        }

        public OrderData getOrderData() {
            return orderData;
        }
    }


}

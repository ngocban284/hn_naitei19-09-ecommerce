package com.example.ecommerce.model;

public enum PaymentMethod {

        COD("COD"),
        ONLINE("ONLINE");

        private final String method;

        PaymentMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
}

package site.brainbrain.iqtest.controller.dto;

public record TesteeRequest(String email,
                            String name,
                            int age,
                            String gender,
                            String country,
                            String paymentOption) {
}

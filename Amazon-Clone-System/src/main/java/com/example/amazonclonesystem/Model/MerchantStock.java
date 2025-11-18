package com.example.amazonclonesystem.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "id must be filled")
    private String id;
    @NotEmpty(message = "productId must be filled")
    private String productId;
    @NotEmpty
    private String merchantId;
    @NotNull
    @Min(10)
    private int stock;
}

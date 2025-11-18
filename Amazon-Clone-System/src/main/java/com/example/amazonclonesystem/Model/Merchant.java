package com.example.amazonclonesystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "id must be filled")
    private String id;
    @NotEmpty(message = "name must be filled")
    @Size(min = 4, message = "name must be more than 3 length")
    private String name;
}

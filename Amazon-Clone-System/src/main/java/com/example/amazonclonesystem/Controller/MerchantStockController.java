package com.example.amazonclonesystem.Controller;

import com.example.amazonclonesystem.ApiResponse.ApiResponse;
import com.example.amazonclonesystem.Model.MerchantStock;
import com.example.amazonclonesystem.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks(){
        return (!merchantStockService.getMerchantStocks().isEmpty()) ? ResponseEntity.status(200).body(merchantStockService.getMerchantStocks())
                : ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        return (merchantStockService.addStockToMerchant(merchantStock)) ? ResponseEntity.status(200).body(new ApiResponse("Merchant stock added"))
                :ResponseEntity.status(400).body(new ApiResponse("Merchant Id or product Id not found"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id,@RequestBody @Valid MerchantStock merchantStock,Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return (merchantStockService.updateMerchantStock(id,merchantStock)) ? ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated"))
                : ResponseEntity.status(400).body(new ApiResponse("Merchant stock or merchant ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        return (merchantStockService.deleteMerchantStock(id)) ? ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted"))
                : ResponseEntity.status(400).body(new ApiResponse("Merchant stock with id: " + id + " not found"));

    }

    @PostMapping("/add-more-stock/{merchantid}/{productid}/{additionalstock}")
    public ResponseEntity<?> addMoreStock(@PathVariable String productid,@PathVariable String merchantid,@PathVariable int additionalstock){
        return (merchantStockService.addMoreStock(productid,merchantid,additionalstock)) ? ResponseEntity.status(200).body(new ApiResponse("Additional merchant stock added"))
                :ResponseEntity.status(400).body(new ApiResponse("Merchant Id or product Id not found"));

    }

    @GetMapping("/get-available-products-by-merchant/{merchantId}")
    public ResponseEntity<?> getAvailableProductsByMerchant(@PathVariable String merchantId){
        return (!merchantStockService.getAvailableProductsByMerchant(merchantId).isEmpty()) ? ResponseEntity.status(200).body(merchantStockService.getAvailableProductsByMerchant(merchantId))
                : ResponseEntity.status(400).body(new ApiResponse("Merchant Id not found"));
    }

    @GetMapping("/get-merchants-by-product/{productId}")
    public ResponseEntity<?> getMerchantsByProduct(@PathVariable String productId){
        return (!merchantStockService.getMerchantsSellingProduct(productId).isEmpty())
                ? ResponseEntity.status(200).body(merchantStockService.getMerchantsSellingProduct(productId))
                : ResponseEntity.status(400).body(new ApiResponse("No merchants found selling this product"));
    }


}

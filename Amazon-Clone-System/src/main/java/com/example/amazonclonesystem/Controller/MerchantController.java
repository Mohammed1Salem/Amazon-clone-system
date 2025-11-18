package com.example.amazonclonesystem.Controller;

import com.example.amazonclonesystem.ApiResponse.ApiResponse;
import com.example.amazonclonesystem.Model.Merchant;
import com.example.amazonclonesystem.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        return (!merchantService.getMerchants().isEmpty()) ? ResponseEntity.status(200).body(merchantService.getMerchants())
                : ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,@RequestBody @Valid Merchant merchant,Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return (merchantService.updateMerchant(id,merchant)) ? ResponseEntity.status(200).body(new ApiResponse("Merchant updated"))
                : ResponseEntity.status(400).body(new ApiResponse("Merchant with id: " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        return (merchantService.deleteMerchant(id)) ? ResponseEntity.status(200).body(new ApiResponse("Merchant deleted"))
                : ResponseEntity.status(400).body(new ApiResponse("Merchant with id: " + id + " not found"));

    }

    @GetMapping("/get-start-with-name/{letter}")
    public ResponseEntity<?> getMerchantsStartingWith(@PathVariable char letter){
        return (!merchantService.getMerchantsStartingWith(letter).isEmpty())
                ? ResponseEntity.status(200).body(merchantService.getMerchantsStartingWith(letter))
                : ResponseEntity.status(400).body(new ApiResponse("No merchants start with: "+ letter+" found"));
    }

}

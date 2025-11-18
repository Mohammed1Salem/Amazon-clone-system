package com.example.amazonclonesystem.Controller;

import com.example.amazonclonesystem.ApiResponse.ApiResponse;
import com.example.amazonclonesystem.Model.User;
import com.example.amazonclonesystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return (!userService.getUsers().isEmpty()) ? ResponseEntity.status(200).body(userService.getUsers())
                : ResponseEntity.status(400).body(new ApiResponse("users not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,@RequestBody @Valid User user,Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return (userService.updateUser(id,user)) ? ResponseEntity.status(200).body(new ApiResponse("user updated"))
                : ResponseEntity.status(400).body(new ApiResponse("user with id: " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return (userService.deleteUser(id)) ? ResponseEntity.status(200).body(new ApiResponse("user deleted"))
                : ResponseEntity.status(400).body(new ApiResponse("user with id: " + id + " not found"));
    }

    @PostMapping("/buy-product/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId){
        return userService.buyProduct(userId,productId,merchantId) ? ResponseEntity.status(200).body(new ApiResponse("Product purchased"))
                : ResponseEntity.status(400).body(new ApiResponse("something went wrong"));
    }
    @PostMapping("/add-funds/{userId}/{amount}")
    public ResponseEntity<?> addFunds(@PathVariable String userId, @PathVariable double amount){
        return userService.addFunds(userId,amount) ? ResponseEntity.status(200).body(new ApiResponse("User funds increased"))
                : ResponseEntity.status(400).body(new ApiResponse("no user found or funds not positive"));
    }
}

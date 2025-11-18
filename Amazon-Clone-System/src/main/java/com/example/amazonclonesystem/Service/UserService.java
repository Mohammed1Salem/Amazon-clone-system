package com.example.amazonclonesystem.Service;

import com.example.amazonclonesystem.Model.Merchant;
import com.example.amazonclonesystem.Model.MerchantStock;
import com.example.amazonclonesystem.Model.Product;
import com.example.amazonclonesystem.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();

    private final MerchantStockService merchantStockService;
    private final MerchantService merchantService;
    private final ProductService productService;
    public ArrayList<User> getUsers(){
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean updateUser(String id, User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean buyProduct(String userId, String productId, String merchantId) {

        User user = null;
        for (User u : getUsers()) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) return false;

        Product product = null;
        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) return false;

        Merchant merchant = null;
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantId)) {
                merchant = m;
                break;
            }
        }
        if (merchant == null) return false;

        MerchantStock stock = null;
        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId) &&
                    ms.getProductId().equals(productId)) {
                stock = ms;
                break;
            }
        }
        if (stock == null) return false;
        if (stock.getStock() <= 0) return false;

        if (user.getBalance() < product.getPrice()) return false;

        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        return true;
    }

    public boolean addFunds(String userId, double amount){
        if(amount <= 0) return false;

        for(User user : users){
            if(user.getId().equals(userId)){
                user.setBalance(user.getBalance() + amount);
                return true;
            }
        }
        return false;
    }


}

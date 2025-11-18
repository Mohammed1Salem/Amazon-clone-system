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

    public boolean transferMoney(String fromUserId, String toUserId, double amount) {
        if (amount <= 0) return false;

        User fromUser = null;
        User toUser = null;

        for (User user : users) {
            if (user.getId().equals(fromUserId)) {
                fromUser = user;
            }
            if (user.getId().equals(toUserId)) {
                toUser = user;
            }
        }

        if (fromUser == null || toUser == null || fromUser.getBalance() < amount) {
            return false;
        }

        fromUser.setBalance(fromUser.getBalance() - amount);
        toUser.setBalance(toUser.getBalance() + amount);

        return true;
    }

    public boolean buyProductWithTwoUsers(String user1Id, String user2Id, String productId, String merchantId) {
        User user1 = null;
        User user2 = null;

        for (User user : users) {
            if (user.getId().equals(user1Id)) {
                user1 = user;
            }
            if (user.getId().equals(user2Id)) {
                user2 = user;
            }
        }
        if (user1 == null || user2 == null) return false;

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
        if (stock == null || stock.getStock() <= 0) return false;

        double halfPrice = product.getPrice() / 2;

        if (user1.getBalance() < halfPrice || user2.getBalance() < halfPrice) {
            return false;
        }

        user1.setBalance(user1.getBalance() - halfPrice);
        user2.setBalance(user2.getBalance() - halfPrice);
        stock.setStock(stock.getStock() - 1);

        return true;
    }

}

package com.example.amazonclonesystem.Service;

import com.example.amazonclonesystem.Model.Merchant;
import com.example.amazonclonesystem.Model.MerchantStock;
import com.example.amazonclonesystem.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final MerchantService merchantService;
    private final ProductService productService;

    public ArrayList<MerchantStock> getMerchantStocks (){
        return merchantStocks;
    }

    public boolean addStockToMerchant(MerchantStock merchantStock){
        for(Merchant merchant: merchantService.getMerchants()){
            if(merchant.getId().equals(merchantStock.getMerchantId())) {
                for (Product product : productService.getProducts()) {
                    if (product.getId().equals(merchantStock.getProductId())) {
                        merchantStocks.add(merchantStock);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean updateMerchantStock(String id,MerchantStock merchantStock){
        boolean merchantExist = false;
        for (Merchant merchant:merchantService.getMerchants()){
            if (merchant.getId().equals(merchantStock.getMerchantId())){
                merchantExist = true;
                break;
            }
        }
        if(!merchantExist) return false;

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                for (Product product : productService.getProducts()) {
                    if (product.getId().equals(merchantStock.getProductId())){
                    merchantStocks.set(i, merchantStock);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean addMoreStock(String productId, String merchantId, int additionalStock){

        boolean merchantExists = false;
        for (Merchant merchant : merchantService.getMerchants()) {
            if (merchant.getId().equals(merchantId)) {
                merchantExists = true;
                break;
            }
        }
        if (!merchantExists) return false;

        boolean productExists = false;
        for (Product product : productService.getProducts()) {
            if (product.getId().equals(productId)) {
                productExists = true;
                break;
            }
        }
        if (!productExists) return false;

        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantId().equals(merchantId)
                    && stock.getProductId().equals(productId)) {

                stock.setStock(stock.getStock() + additionalStock);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getAvailableProductsByMerchant(String merchantId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantId().equals(merchantId)) {
                for (Product product : productService.getProducts()) {
                    if (product.getId().equals(stock.getProductId())) {
                        availableProducts.add(product);
                        break;
                    }
                }
            }
        }
        return availableProducts;
    }

    public ArrayList<MerchantStock> getMerchantsSellingProduct(String productId) {

        ArrayList<MerchantStock> result = new ArrayList<>();

        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId().equals(productId)) {
                result.add(stock);
            }
        }

        return result;
    }

}


package com.easymall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.easymall.cart.feign.ProductFeignService;
import com.easymall.cart.interceptor.CartInterceptor;
import com.easymall.cart.service.CartService;
import com.easymall.cart.to.UserInfoTo;
import com.easymall.cart.vo.CartItemVo;
import com.easymall.cart.vo.CartVo;
import com.easymall.cart.vo.SkuInfoVo;
import easymall.easymallcommon.constant.CartConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Name: CartServiceImpl
 * @Author peipei
 * @Date 2022/4/25
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    @Override
    public List<CartItemVo> checkItem() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        List<CartItemVo> cartByKey = getCartByKey(userInfoTo.getUserKey());
        List<CartItemVo> collect = cartByKey.stream().filter(CartItemVo::getCheck).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void checkCart(Long skuId, Integer isCheck) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String cartItemStr = (String) cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(cartItemStr, CartItemVo.class);
        cartItemVo.setCheck(isCheck == 1);
        cartOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));
    }

    @Override
    public void countItem(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String itemStr = (String) cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(itemStr, CartItemVo.class);
        cartItemVo.setCount(num);
        cartOps.put(skuId.toString(), cartItemVo.toString());
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }

    @Override
    public List<CartItemVo> getCheckedItems() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() == null) {
            return null;
        }
        CartVo cart = getCart();
        List<CartItemVo> cartItems = cart.getItems();
        ArrayList<CartItemVo> resCartVos = new ArrayList<>();
        for (CartItemVo cartItem : cartItems) {
            if (cartItem.getCheck()) {
                resCartVos.addAll(cartItems);
            }
        }
        return resCartVos;
    }


    @Override
    public CartVo getCart() {
        CartVo cartVo = new CartVo();
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        List<CartItemVo> cartItems = getCartByKey(userInfoTo.getUserKey());
        if (userInfoTo.getUserId() != null) {
            cartVo.setItems(cartItems);
        } else {
            List<CartItemVo> cartByUserId = getCartByKey(userInfoTo.getUserId().toString());
            for (CartItemVo cartItem : cartItems) {
                cartByUserId.add(cartItem);
                addCartItem(cartItem.getSkuId(), cartItem.getCount());
            }
            cartVo.setItems(cartByUserId);
            redisTemplate.delete(CartConstant.CART_PREFIX + userInfoTo.getUserKey());
        }
        return cartVo;
    }


    @Override
    public CartItemVo addCartItem(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String skuStrId = skuId + "";
        String skuStr = (String) ops.get(skuStrId);
        if (skuId != null) {
            CartItemVo cartItemVo = JSON.parseObject(skuStr, CartItemVo.class);
            cartItemVo.setCount(cartItemVo.getCount() + num);
            String cartVoJson = JSON.toJSONString(cartItemVo);
            ops.put(skuStrId, cartVoJson);
            return cartItemVo;
        } else {
            CartItemVo cartItemVo = new CartItemVo();
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                R info = productFeignService.info(skuId);
                SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<CartItemVo>());
                cartItemVo.setSkuId(skuId);
                cartItemVo.setTitle(skuInfo.getSkuTitle());
                cartItemVo.setImage(skuInfo.getSkuDefaultImg());
                cartItemVo.setPrice(skuInfo.getPrice());
                cartItemVo.setCount(num);
                cartItemVo.setCheck(true);
            }, threadPoolExecutor);

            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                cartItemVo.setSkuAttrValues(productFeignService.getSkuSaleAttrValuesAsString(skuId));
            }, threadPoolExecutor);

            CompletableFuture.allOf(future1, future2);
            String toJSONString = JSON.toJSONString(cartItemVo);
            ops.put(skuStrId, toJSONString);
            return cartItemVo;
        }

    }

    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String string = (String) cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(string, CartItemVo.class);
        return cartItemVo;
    }

    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (!userInfoTo.getTempUser()) {
            return redisTemplate.boundHashOps(CartConstant.CART_PREFIX + userInfoTo.getUserId());
        } else {
            return redisTemplate.boundHashOps(CartConstant.CART_PREFIX + userInfoTo.getUserKey());
        }

    }

    private List<CartItemVo> getCartByKey(String userKey) {
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(CartConstant.CART_PREFIX + userKey);
        List<Object> values = ops.values();
        if (values.size() == 0) {
            return null;
        }
        ArrayList<CartItemVo> cartItemVos = new ArrayList<>();
        for (Object value : values) {
            CartItemVo cartItemVo = JSON.parseObject((String) value, CartItemVo.class);
            cartItemVos.add(cartItemVo);
        }
        return cartItemVos;
    }


}

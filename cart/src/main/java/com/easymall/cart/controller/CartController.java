package com.easymall.cart.controller;

import com.easymall.cart.service.CartService;
import com.easymall.cart.vo.CartItemVo;
import com.easymall.cart.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @Name: CartController
 * @Author peipei
 * @Date 2022/4/25
 */
@Controller
public class CartController {
    @Autowired
    CartService cartService;


    @RequestMapping("/cartList")
    public R getCartList() {
        CartVo cartVo = cartService.getCart();
        return R.ok().put("cartItemVos", cartVo);
    }

    @GetMapping("/countItem")
    public R countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        cartService.countItem(skuId, num);
        return null;
    }

    @GetMapping("/checkItem")
    public R checkCart(@RequestParam("skuId") Long skuId, @RequestParam("isChecked") Integer isCheck) {
        cartService.checkCart(skuId, isCheck);
        return null;
    }


    @PostMapping("/addCartItem")
    public R addCartItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        CartItemVo cartItemVo = cartService.addCartItem(skuId, num);
        return R.ok().put("cartItemVo", cartItemVo);
    }

    @RequestMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {
        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }


    @GetMapping()
    public R getCartItem(@RequestParam("skuId") Long skuId) {
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        return null;
    }

    @GetMapping("/getCheckedItems")
    public List<CartItemVo> getCheckedItems() {
        return cartService.getCheckedItems();
    }


}

package com.easymall.cart.service;

import com.easymall.cart.vo.CartItemVo;
import com.easymall.cart.vo.CartVo;

import java.util.List;

/**
 * @Description:
 * @Name: CartService
 * @Author peipei
 * @Date 2022/4/25
 */
public interface CartService {

    /**
     * 获取购物车内的所有商品
     *
     * @return
     */
    CartVo getCart();

    /**
     * 添加商品至购物车
     *
     * @param skuId
     * @param num
     */
    CartItemVo addCartItem(Long skuId, Integer num);

    /**
     * 获取购物车内的商品信息
     *
     * @param skuId
     * @return
     */
    CartItemVo getCartItem(Long skuId);

    /**
     * 获取选中的商品
     *
     * @return
     */
    List<CartItemVo> checkItem();

    /**
     * 更改购物车商品状态
     *
     * @param skuId
     * @param isCheck
     */
    void checkCart(Long skuId, Integer isCheck);

    /**
     * 改变商品购物车内的数量
     *
     * @param skuId
     * @param num
     */
    void countItem(Long skuId, Integer num);

    /**
     * 删除购物车内的购物项
     *
     * @param skuId
     */
    void deleteItem(Long skuId);

    /**
     * 获取购物车内勾选的商品
     * @return
     */
    List<CartItemVo> getCheckedItems();


}

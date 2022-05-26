package com.easymall.easymallcoupon.sms.controller;


import com.easymall.easymallcoupon.sms.model.SmsSeckillSession;
import com.easymall.easymallcoupon.sms.service.SmsSeckillSessionService;
import easymall.easymallcommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 秒杀活动场次 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/sms/smsSeckillSession")
public class SmsSeckillSessionController {
    @Autowired
    SmsSeckillSessionService seckillSessionService;

    @GetMapping("/getSeckillSessionsIn3Days")
    public R getSeckillSessionsIn3Days() {
        List<SmsSeckillSession> seckillSessionList = seckillSessionService.getSeckillSessionsIn3Days();
        return R.ok().setData(seckillSessionList);
    }


}


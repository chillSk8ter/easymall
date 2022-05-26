package com.easymall.easymallcoupon.sms.service;

import com.easymall.easymallcoupon.sms.model.SmsSeckillSession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 秒杀活动场次 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
public interface SmsSeckillSessionService extends IService<SmsSeckillSession> {

    List<SmsSeckillSession> getSeckillSessionsIn3Days();


}

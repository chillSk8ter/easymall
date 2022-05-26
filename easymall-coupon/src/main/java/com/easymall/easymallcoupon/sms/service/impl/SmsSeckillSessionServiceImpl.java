package com.easymall.easymallcoupon.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easymall.easymallcoupon.sms.model.SmsSeckillSession;
import com.easymall.easymallcoupon.sms.mapper.SmsSeckillSessionMapper;
import com.easymall.easymallcoupon.sms.model.SmsSeckillSkuRelation;
import com.easymall.easymallcoupon.sms.service.SmsSeckillSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.easymallcoupon.sms.service.SmsSeckillSkuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 秒杀活动场次 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Service
public class SmsSeckillSessionServiceImpl extends ServiceImpl<SmsSeckillSessionMapper, SmsSeckillSession> implements SmsSeckillSessionService {
    @Autowired
    SmsSeckillSkuRelationService seckillSkuRelationService;

    @Override
    public List<SmsSeckillSession> getSeckillSessionsIn3Days() {
        QueryWrapper<SmsSeckillSession> queryWrapper = new QueryWrapper<SmsSeckillSession>()
                .between("start_time", getStartTime(), getEndTime());
        List<SmsSeckillSession> seckillSessionEntities = this.list(queryWrapper);
        List<SmsSeckillSession> list = seckillSessionEntities.stream().map(session -> {
            List<SmsSeckillSkuRelation> skuRelationEntities = seckillSkuRelationService.list(new QueryWrapper<SmsSeckillSkuRelation>().eq("promotion_session_id", session.getId()));
            session.setSeckillSkuRelationList(skuRelationEntities);
            return session;
        }).collect(Collectors.toList());
        return list;
    }

    //当前天数的 00:00:00
    private String getStartTime() {
        LocalDate now = LocalDate.now();
        LocalDateTime time = now.atTime(LocalTime.MIN);
        String format = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }

    //当前天数+2 23:59:59..
    private String getEndTime() {
        LocalDate now = LocalDate.now();
        LocalDateTime time = now.plusDays(2).atTime(LocalTime.MAX);
        String format = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }


}

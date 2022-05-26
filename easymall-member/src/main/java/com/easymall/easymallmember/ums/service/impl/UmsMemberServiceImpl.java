package com.easymall.easymallmember.ums.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.easymallmember.ums.Exception.PhoneExistException;
import com.easymall.easymallmember.ums.Exception.UserExistException;
import com.easymall.easymallmember.ums.mapper.UmsMemberMapper;
import com.easymall.easymallmember.ums.model.UmsMember;
import com.easymall.easymallmember.ums.model.UmsMemberLevel;
import com.easymall.easymallmember.ums.service.UmsMemberLevelService;
import com.easymall.easymallmember.ums.service.UmsMemberService;
import com.easymall.easymallmember.ums.vo.MemberLoginVo;
import com.easymall.easymallmember.ums.vo.MemberRegisterVo;
import com.easymall.easymallmember.ums.vo.SocialUser;
import easymall.easymallcommon.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-30
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    UmsMemberLevelService umsMemberLevelService;

    @Override
    public void register(MemberRegisterVo registerVo) {
        checkNameUnique(registerVo.getUserName());
        checkPhoneUnique(registerVo.getPhone());
        UmsMember umsMember = new UmsMember();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        umsMember.setMobile(registerVo.getPhone());
        umsMember.setUsername(registerVo.getUserName());
        umsMember.setPassword(bCryptPasswordEncoder.encode(registerVo.getPassword()));
        Long defaultLevelId = getDefaultLevelId();
        umsMember.setLevelId(defaultLevelId);
        save(umsMember);
    }

    private Long getDefaultLevelId() {
        UmsMemberLevel memberLevel = umsMemberLevelService.getOne(new QueryWrapper<UmsMemberLevel>().lambda().eq(UmsMemberLevel::getDefaultStatus, 1));
        return memberLevel.getId();

    }

    @Override
    public void checkPhoneUnique(String phone) {
        LambdaQueryWrapper<UmsMember> lambda = new QueryWrapper<UmsMember>().lambda();
        lambda.eq(UmsMember::getMobile, phone);
        long count = count(lambda);
        if (count > 0) {
            throw new PhoneExistException();
        }

    }

    @Override
    public void checkNameUnique(String userName) {
        LambdaQueryWrapper<UmsMember> lambda = new QueryWrapper<UmsMember>().lambda();
        lambda.eq(UmsMember::getUsername, userName);
        long count = count(lambda);
        if (count > 0) {
            throw new UserExistException();
        }
    }

    @Override
    public UmsMember login(MemberLoginVo loginVo) {
        String account = loginVo.getLoginAccount();
        LambdaQueryWrapper<UmsMember> queryWrapper = new QueryWrapper<UmsMember>().lambda();
        queryWrapper.eq(UmsMember::getUsername, account).or().eq(UmsMember::getMobile, account);
        UmsMember member = getOne(queryWrapper);
        if (member == null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(loginVo.getPassword(), member.getPassword())) {
            member.setPassword("");
            return member;
        }
        return null;
    }

    @Override
    public UmsMember login(SocialUser socialUser) {
        LambdaQueryWrapper<UmsMember> lambda = new QueryWrapper<UmsMember>().lambda();
        lambda.eq(UmsMember::getSocialUid, socialUser.getUid());
        UmsMember uid = getOne(lambda);
        if (uid == null) {
            HashMap<String, String> queryMap = new HashMap<>();
            queryMap.put("access_token", socialUser.getAccess_token());
            queryMap.put("uid", socialUser.getUid());
            String json = null;
            try {
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<>(), queryMap);
                json = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(json);
                String name = jsonObject.getString("name");
                String gender = jsonObject.getString("gender");
                String profile_image_url = jsonObject.getString("profile_image_url");
                uid = new UmsMember();
                UmsMemberLevel defaultLevel = umsMemberLevelService.getOne(new QueryWrapper<UmsMemberLevel>().lambda().eq(UmsMemberLevel::getDefaultStatus, 1));
                uid.setLevelId(defaultLevel.getId());
                uid.setNickname(name);
                uid.setGender("m".equals(gender) ? 0 : 1);
                uid.setHeader(profile_image_url);
                uid.setAccessToken(socialUser.getAccess_token());
                uid.setSocialUid(socialUser.getUid());
                uid.setExpiresIn(socialUser.getExpires_in() + "");
            } catch (Exception e) {
                log.error("调用第三方接口失败");
            }
        } else {
            uid.setAccessToken(socialUser.getAccess_token());
            uid.setExpiresIn(socialUser.getExpires_in() + "");
            uid.setSocialUid(socialUser.getUid());
            updateById(uid);
        }
        return uid;
    }
}

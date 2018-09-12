package com.sv.mc.service;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.pojo.vo.HomeVO;

public interface HomeService {

    //本接口用于登陆页面的数据显示

    public HomeVO dataDisplay(UserEntity user);

}

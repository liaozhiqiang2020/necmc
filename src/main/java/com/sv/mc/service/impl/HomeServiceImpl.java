package com.sv.mc.service.impl;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.pojo.VendorEntity;
import com.sv.mc.pojo.vo.HomeVO;
import com.sv.mc.repository.AccountDetailRepository;
import com.sv.mc.repository.DeviceRepository;
import com.sv.mc.repository.OrderRepository;
import com.sv.mc.repository.VendorRepository;
import com.sv.mc.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountDetailRepository accountDetailRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Override
    public HomeVO dataDisplay(UserEntity user) {
        //判断用户的等级
        int gradeId = user.getGradeId(); //用户等级
        int pId=user.getpId();//隶属单位
        HomeVO result = new HomeVO();//结果集
        if (gradeId==1){//1为最高权限查询所有数据
            result.setNmoDevice(deviceRepository.getDeviceCount());//正常设备数
            result.setExpDevice(deviceRepository.getDeviceErrorCount()); //异常设备数
            result.setYedOrder(orderRepository.getYeOrder());//昨天的订单数
            result.setYedInc(accountDetailRepository.getInc());//昨天的收入
        }
        else if (gradeId==2){//分公司
            result.setNmoDevice(deviceRepository.getDevCotBySuperiorId1(pId));//正常设备数
            result.setExpDevice(deviceRepository.getDevErrorCotBySuperiorId1(pId)); //异常设备数
            result.setYedOrder(orderRepository.getYeOrderTwo(pId));//昨天的订单
            result.setYedInc(accountDetailRepository.getIncTwo(pId));//昨天的收入

        }else if (gradeId==3){//代理商
            result.setNmoDevice(deviceRepository.getDevCotBySuperiorId(pId));//正常设备数
            result.setExpDevice(deviceRepository.getDevErrorCotBySuperiorId(pId)); //异常设备数
            result.setYedOrder(orderRepository.getYeOrderThree(pId));//昨天的订单
            result.setYedInc(accountDetailRepository.getIncThree(pId));//昨天的收入
        }
        else {//场地管理员权限
            result.setNmoDevice(deviceRepository.getcDeviceByPid(pId));//正常设备数
            result.setExpDevice(deviceRepository.geteDeviceByPid(pId)); //异常设备数
            result.setYedOrder(orderRepository.getYeOrderFour(pId));//昨天的订单
            result.setYedInc(accountDetailRepository.getIncFour(pId));//昨天的收入
             }
        return result;
    }
}

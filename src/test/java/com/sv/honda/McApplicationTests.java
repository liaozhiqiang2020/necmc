package com.sv.honda;

import com.sv.mc.MCApplication;
import com.sv.mc.pojo.DeviceEntity;

import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.service.CountService;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MCApplication.class)
public class McApplicationTests {
@Autowired
    DeviceService d;
@Autowired
CountService countService;

    @Test
    public void contextLoads() {

    }



    @Test
    public void text2(){
        List<Integer> a= new ArrayList();
       for (int i=11000001;i<=11000024;i++){
           a.add(i);

       }
        System.out.println(a);
    }

}

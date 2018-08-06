package com.sv.honda;

import com.sv.mc.MCApplication;
import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.service.DeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MCApplication.class)
public class McApplicationTests {
@Autowired
    DeviceService d;
    @Test
    public void contextLoads() {
    }



    @Test
    public void text2(){
        List<DeviceEntity> D=d.findDevice2();
        System.out.println(D);
    }
}

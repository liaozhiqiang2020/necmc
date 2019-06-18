//package com.sv.honda;
//
//import com.sv.mc.MCApplication;
//import com.sv.mc.pojo.DeviceEntity;
//
//import com.sv.mc.pojo.PriceEntity;
//import com.sv.mc.pojo.ProvinceEntity;
//import com.sv.mc.quartz.ScheduleTask;
//import com.sv.mc.service.*;
//import com.sv.mc.util.WxUtil;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.jms.Destination;
//import javax.jms.JMSException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MCApplication.class)
//public class McApplicationTests {
//
//    @Autowired
//    JMSProducer jmsProducer;
//
//    @Test
//    public void test()throws Exception {
////        for (int i = 1; i < 10000; i++) {
//////            String ss = "3131303030393031";
////            String message = "第" + i + "条消息";
////
//////            String ss = "3131303030393031";
//////            String message = "faaf0f09"+ss+"010000";
////
//////            String ss = "3131303030393031";
//////            String message = "faaf0e10"+ss+"0000";
////            jmsProducer.sendMessage(message);
//////
////        }
//
//
//
//        String res = "EFBFBDEFBFBD0F083131303030303334EFBFBDEFBFBD32";
//        // 写入消息队列
//        Destination destination = new ActiveMQQueue("heartbeat.queue");
//
//        for (int i = 0; i <2000; i++) {
//            jmsProducer.sendMessage2(destination, res);
//        }
//
//
//    }
//
//    /**
//     * 计算校验和
//     * @throws Exception
//     */
//    @Test
//    public void test2()throws Exception {
//        //21000000    035d
////        String str = "faaf0f21323130303030303001";
//        //035e
////         String str = "faaf0f21323130303030303002";
//
//        //22000000    035e
////        String str = "faaf0f21323230303030303001";
//        //035f
////        String str = "faaf0f21323230303030303002";
//
//        String str = "faaf0f06640001000100000000000000000000000000000000000000000000000000000000\n" +
//                "0000000000000000000000000000000000000000000000000000000000000000";
//
//        WxUtil wxUtil = new WxUtil();
////            String message = "faaf0f09" + chairCode + time;
////
//        byte[] srtbyte = WxUtil.toByteArray(str);  //字符串转化成byte[]
//        byte[] newByte = wxUtil.SumCheck(srtbyte, 2);  //计算校验和
//        String res = WxUtil.bytesToHexString(newByte).toLowerCase();  //byte[]转16进制字符串
//        str = str + res;
//        System.out.println(str);
//    }
//
//    @Test
//    public void test3() throws JMSException {
//        ScheduleTask scheduleTask = new ScheduleTask();
//        scheduleTask.scheduleTest();
//    }
//
//}

//package com.sv.honda;
//
//import com.alipay.api.AlipayApiException;
//import com.sv.mc.MCApplication;
//import com.sv.mc.controller.AlipayController;
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
//    @Autowired
//    AlipayController alipayController;
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
//    @Test
//    public void test4(){
//        int n =5;
//        String n2="";
//        if(n <= 9 && n >= 0){
//            n2 = "0"+Integer.toHexString(n);
//        }else{
//            n2 = Integer.toHexString(n);
//        }
//        System.out.print(n2);
//    }
//
//
//    @Test
//    public void test5(){
//
//        String devivesn = "21000201";
//        devivesn = decimalToHex(Integer.parseInt(devivesn));
//        System.out.println(devivesn);
//    }
//
//    public static String decimalToHex(int decimal) {
//        String hex = "";
//        while(decimal != 0) {
//            int hexValue = decimal % 16;
//            hex = toHexChar(hexValue) + hex;
//            decimal = decimal / 16;
//        }
//        return  hex;
//    }
//
//    //将0~15的十进制数转换成0~F的十六进制数
//    public static char toHexChar(int hexValue) {
//        if(hexValue <= 9 && hexValue >= 0)
//            return (char)('0'+hexValue);
//        else
//            return (char)(hexValue - 10 + 'a');
//    }
//
//
//    @Test
//    public void test6() throws AlipayApiException {
//        alipayController.alipayPay();
//    }
//}

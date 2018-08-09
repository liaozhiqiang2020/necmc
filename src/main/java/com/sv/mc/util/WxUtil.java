package com.sv.mc.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 小程序通用的一些方法
 */
public class WxUtil {
    /**
     * 未支付订单生成编号
     * @param openid
     * @param deviceCode
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    public String createUnPaidOrderCode(String openid,String deviceCode){
        String unPaidOrderCode;

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        unPaidOrderCode = "unPaidOrder_"+year+month+date+hour+minute+second+"_"+openid+"_"+deviceCode;
        System.out.println(unPaidOrderCode);
        return unPaidOrderCode;
    }

    /**
     * 已支付订单生成编号
     * @param openid
     * @param deviceCode
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    public String createPaidOrderCode(String openid,String deviceCode){
        String PaidOrderCode;

//        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int date = c.get(Calendar.DATE);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//        int second = c.get(Calendar.SECOND);

        Timestamp ts = getNowDate();//获取当前时间(时间戳)
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timer = sdf.format(ts);

        PaidOrderCode = timer+"_"+openid+"_"+deviceCode;
        System.out.println(PaidOrderCode);
        return PaidOrderCode;
    }

    /**
     * 获取当前时间戳
     * @author: lzq
     * @date: 2018年7月6日
     */
    public Timestamp getNowDate(){
        //获取当前时间
        Date date = new Date();
        //转为时间戳
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }

    /**
     * 获取按摩结束时间
     * @return
     * @author: lzq
     * @date: 2018年7月6日
     */
    public Timestamp getAfterDate(int mcTime){
        long curren = System.currentTimeMillis();
        curren += mcTime * 60 * 1000;
        Date da = new Date(curren);
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss");

        //转为时间戳
        Timestamp ts = new Timestamp(da.getTime());
        return ts;
    }

    /**
     * 获取两个时间戳差值
     * @author: lzq
     * @date: 2018年7月6日
     */
    public Map<String,Object> getDifferTime(long time1, long time2){
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;

        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }

        Map<String,Object> map = new HashMap<String,Object>();
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        map.put("hour",hour);
        map.put("min",min);
        return map;
    }


    /**
     * 两个时间间的时间戳计算函数
     * @param beginDate
     * @param endDate
     * @param f  时间差的形式0:秒,1:分种,2:小时,3:天
     * @return  long 秒
     * @author: lzq
     * @date: 2018年7月6日
     */
    public long getDifference(Date beginDate, Date endDate, int f) {
        long result = 0;
        if (beginDate == null || endDate == null) {
            return 0;
        }
        try {
            // 日期相减获取日期差X(单位:毫秒)
            long millisecond = endDate.getTime() - beginDate.getTime();
            /**
             * Math.abs((int)(millisecond/1000)); 绝对值 1秒 = 1000毫秒
             * millisecond/1000 --> 秒 millisecond/1000*60 - > 分钟
             * millisecond/(1000*60*60) -- > 小时 millisecond/(1000*60*60*24) -->
             * 天
             * */
            switch (f) {
                case 0: // second
                    return  (millisecond / 1000);
                case 1: // minute
                    return (millisecond / (1000 * 60));
                case 2: // hour
                    return  (millisecond / (1000 * 60 * 60));
                case 3: // day
                    return (millisecond / (1000 * 60 * 60 * 24));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     *把16进制转为AscII
     * @param str
     * @return
     */
    public String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }


    /**
     * Ascii转为16进制
     * @param hex
     * @return
     */
    public String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

}

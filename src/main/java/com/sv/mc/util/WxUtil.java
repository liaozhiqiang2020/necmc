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
        Timestamp ts = new Timestamp(da.getTime()+1000);
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


    /**
     * 数组转换成十六进制字符串
     * @param
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    /**
     * 校验和
     *
     * @param msg 需要计算校验和的byte数组
     * @param length 校验和位数
     * @return 计算出的校验和数组
     */
    public byte[] SumCheck(byte[] msg, int length) {
        long mSum = 0;
        byte[] mByte = new byte[length];

        /** 逐Byte添加位数和 */
        for (byte byteMsg : msg) {
            long mNum = ((long)byteMsg >= 0) ? (long)byteMsg : ((long)byteMsg + 256);
            mSum += mNum;
        } /** end of for (byte byteMsg : msg) */

        /** 位数和转化为Byte数组 */
        for (int liv_Count = 0; liv_Count < length; liv_Count++) {
            mByte[length - liv_Count - 1] = (byte)(mSum >> (liv_Count * 8) & 0xff);
        } /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

        return mByte;
    }

    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**转大写**/
    private char charToUpperCase(char ch){
        if(ch <= 122 && ch >= 97){
            ch -= 32;
        }
        return ch;
    }

    /***转小写**/
    private char charToLowerCase(char ch){
        if(ch <= 90 && ch >= 65){
            ch += 32;
        }
        return ch;
    }

    /**
     *
     * 功能描述：金额字符串转换：单位元转成单分

     * @param
     * @return 转换后的金额字符串
     */
    public static String yuanToFen(Object o) {
        if(o == null)
            return "0";
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
            posIndex = s.indexOf(".");
            if(posIndex>0){
                int len = s.length();
                if(len == posIndex+1){
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append("00");
                }else if(len == posIndex+2){
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append(s, posIndex+1, posIndex+2).append("0");
                }else if(len == posIndex+3){
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append(s, posIndex+1, posIndex+3);
                }else{
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append(s, posIndex+1, posIndex+3);
                }
            }else{
                sb.append(s).append("00");
            }
        }else{
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
            return str;
        }else{
            return "0";
        }
    }


    /**
     *
     * 功能描述：去除字符串首部为"0"字符

     * @param str 传入需要转换的字符串
     * @return 转换后的字符串
     */
    public static String removeZero(String str){
        char  ch;
        String result = "";
        if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
            try{
                for(int i=0;i<str.length();i++){
                    ch = str.charAt(i);
                    if(ch != '0'){
                        result = str.substring(i);
                        break;
                    }
                }
            }catch(Exception e){
                result = "";
            }
        }else{
            result = "";
        }
        return result;

    }


    /**
     * 10进制转16进制
     * @param n
     * @return
     */
    public static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }

}

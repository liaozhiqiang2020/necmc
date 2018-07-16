package com.sv.mc.util;

import java.sql.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通用类
 */
public class BaseUtil {
    //数据库连接
    private static final String URL = "jdbc:mysql://111.231.85.247:3306/honda_om?useUnicode=true&characterEncoding=utf-8";
    private static final String NAME = "root";
    private static final String PASS = "13901183126_5369n5xGG";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 获取所有表名，map中为多个key,value形式
     */
    public List<Map<String,Object>> getAllTables() {
        List<Map<String,Object>> listMap = new ArrayList<>();
        Connection con;
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'honda_om' ORDER BY table_name DESC;";
        PreparedStatement pStemt;
        ResultSet rs;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            rs = pStemt.executeQuery();

            int count = 1;
            while (rs.next()) {
                Map<String,Object> tableNameMap = new HashMap<>();
                String tableName = rs.getString("table_name");
                tableNameMap.put("id",count);
                tableNameMap.put("name",tableName);
                count++;
                if(!tableName.equals("system_log")){
                    listMap.add(tableNameMap);
                }
            }
        }catch (SQLException e){
           e.printStackTrace();
        }
        return listMap;
    }

    /**
     * 获取所有表名，map中为一个key,value形式
     */
    public Map<String,Object> getTables() {
//        Map<String,Object> listMap = new HashMap<>();
        Map<String,Object> tableNameMap = new HashMap<>();
        Connection con;
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'honda_om' ORDER BY table_name DESC;";
        PreparedStatement pStemt;
        ResultSet rs;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            rs = pStemt.executeQuery();

            int count = 1;

            while (rs.next()) {

                String tableName = rs.getString("table_name");
                if(!tableName.equals("system_log")){
                    tableNameMap.put(String.valueOf(count),tableName);
                }
                count++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tableNameMap;
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * @param dateString (中国标准时间)
     * @return 年月日;
     */
    public static String parseTime(String dateString) {
        dateString = dateString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        //将字符串转化为date类型，格式yyyy-MM-dd HH:mm
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        ParsePosition pos = new ParsePosition(0);
        java.util.Date dateTrans = null;
        try {
            dateTrans = format.parse(dateString,pos);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateTrans);//.replace("-","/")
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }
}

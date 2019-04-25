package com.sv.mc.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;

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


//    /**
//     * 发送post请求
//     * @param url
//     * @param param
//     * @return
//     */
//    public static String sendPost(String url, String param) {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        try {
//            java.net.URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            // 发送请求参数
//            out.print(param);
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            System.out.println("[POST请求]向地址：" + url + " 发送数据：" + param + " 发生错误!");
//        } finally {// 使用finally块来关闭输出流、输入流
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//                System.out.println("关闭流异常");
//            }
//        }
//        return result;
//    }


//    /**
//     * 流图片解码
//     * @param   input
//     * @return  String
//     */
//    public static String decodeQrcode(InputStream input) throws NotFoundException, IOException {
//
//        BufferedImage image = ImageIO.read(input);
//        LuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//
//        Map<DecodeHintType,Object> hints = new LinkedHashMap<DecodeHintType,Object>();
//        // 解码设置编码方式为：utf-8，
//        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
//        //优化精度
//        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//        //复杂模式，开启PURE_BARCODE模式
//        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
//        Result result = new MultiFormatReader().decode(bitmap,hints);
//
//
//        return result.getText();
//    }
//
//
//    /**
//     * 解析二维码（QRCode）
//     * @param image
//     * @return
//     */
//    public static String decodeQrcode(BufferedImage image) throws NotFoundException {
//
//        MultiFormatReader formatReader = new MultiFormatReader();
//
//        BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
//
//        //定义二维码的参数:
//        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
//        hints.put(DecodeHintType.CHARACTER_SET,"utf-8");//定义字符集
//        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
//        Result result = formatReader.decode(binaryBitmap, hints);//开始解析
//
//        return result.getText();
//    }

//
//    public static void base64StringToImage(String base64String) {
//        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//        try {
//            byte[] bytes1 = decoder.decodeBuffer(base64String);
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
//            BufferedImage bi1 = ImageIO.read(bais);
//            File f1 = new File("d://out.jfif");
//            ImageIO.write(bi1, "JFIF", f1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //获取小程序二维码图片
    public Map getQrCode(String accessToken) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<>();
            //param.put("path", "pages/要跳转小程序的路径")//有限<br>          //param.put("page", "pages/index/index"); //无限
            param.put("scene","20000002");
            param.put("width", 430);
            param.put("auto_color", false);
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            System.out.println("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(JSONObject.fromObject(param), headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class);
            System.out.println("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);

            File file = new File("d://out.png");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();

            //获取二维码路径
//            ByteArrayInputStream inputStreams= new ByteArrayInputStream(result);
//            String xxx = decodeQrcode(inputStreams);
//            System.out.println(xxx);
        } catch (Exception e) {
            System.out.println("调用小程序生成微信永久小程序码URL接口异常");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

package com.sv.mc.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import static com.sv.mc.util.QrCodeUtils.generateQrCode;
import static com.sv.mc.util.TestMain.zip;

/**
 * 二维码小工具控制层
 */
@RestController
public class QRCodeController {


    /**
     * 跳转到二维码生成
     */
    @GetMapping(value = "/util/qrCode")
    public ModelAndView turnToOrderMgr() {
        return new ModelAndView("./util/QrUtil");
    }



    /**
     * 根据设备编号生成二维码
     * @param response 响应下载
     * @param sn 设备编号
    */
    @PostMapping(value = "/util/getQrCode")
    public @ResponseBody
    boolean getQRCode(@RequestBody String sn, HttpServletResponse response) throws Exception {
        boolean flag = false;
        String zipPath = "./qrzip"; //压缩后的zip文件放置的位置
        String imageDir = "./qrimage"; //要压缩的目录及目录中的文件
        String zipFileName = "qr.zip"; //zip文件文件名
        File f = new File(imageDir); //图片文件
        File z = new File(zipPath);  //压缩文件
        //如果文件存在删除
        if (f.exists()) {
           // System.out.println("图片文件存在");

            //删除文件
        deleteDir(imageDir);

            //f.mkdir();
        }
        if(z.exists()){
            //System.out.println("压缩文件存在");
           deleteDir(zipPath);
           //System.out.println("删除成功");
        }

        String sn1 = sn.replace("sn=", "");
        String[] b = sn1.split("%2C");
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
            String qrCodeContent = "https://www.infhp.cn/mc/wx/wxApp?sn="+b[i];
            //https://www.infhp.cn/mc/wx/wxApp?sn=11000102
            File qrcFile = new File(imageDir+"/" + b[i] + ".jpg");

            String pressText = b[i];
            generateQrCode(qrcFile, qrCodeContent, pressText,b[i].length());


            /*try {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                @SuppressWarnings("rawtypes")
                Map hints = new HashMap();

                //设置UTF-8， 防止中文乱码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //设置二维码四周白色区域的大小
                hints.put(EncodeHintType.MARGIN, 2);
                //设置二维码的容错性
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                //width:图片完整的宽;height:图片完整的高
                //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
                int width = 280;//352 草料默认为280px
                int height = 450;//612

                //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
                BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height, hints);

                //qrcFile用来存放生成的二维码图片（无logo，无文字）
                File logoFile = new File(imageDir, b[i]+".jpg");

                //开始画二维码
                BufferedImage barCodeImage = MatrixToImageWriter.writeToFile(bitMatrix, "jpg");

                //在二维码中加入图片
                LogoConfig logoConfig = new LogoConfig(); //LogoConfig中设置Logo的属性
                BufferedImage image = addLogo_QRCode(barCodeImage,  logoConfig);

                int font = 18; //字体大小
                int fontStyle = 1; //字体风格

                //用来存放带有logo+文字的二维码图片
                String newImageWithText = imageDir + "/"+b[i]+".jpg";
                //附加在图片上的文字信息
                String text = b[i];

                //在二维码下方添加文字（文字居中）
                pressText(text, newImageWithText, image, fontStyle, Color.black, font, width, height);

            } catch (Exception e) {
                e.printStackTrace();
            }*/













        }

        //生成zip文件
        try {
            zip(imageDir, zipPath, zipFileName);
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 压缩文件下载
     * @param response
     * @throws FileNotFoundException
     */
    @GetMapping(value = "/util/returnQrCode")
    public void getCode(HttpServletResponse response) throws FileNotFoundException {
        String zipPath = "./qrzip";

        String fileName = "qr.zip"; //zip文件文件名

        // 读到流中
        InputStream inStream = new FileInputStream(zipPath+"/"+fileName);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static boolean deleteDir(String path){
        File file = new File(path);
        if(!file.exists()){//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(path, name);
            if(temp.isDirectory()){//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }



}



package com.sv.mc.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import static com.sv.mc.util.QrCodeUtils.generateQrCode;
import static com.sv.mc.util.TestMain.zip;
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
     * 生成二维码
     */
    @PostMapping(value = "/util/getQrCode")
    public @ResponseBody
    boolean getQRCode(@RequestBody String sn, HttpServletResponse response) throws Exception {
        boolean flag = false;
        String zipPath = "/qrzip"; //压缩后的zip文件放置的位置
        String imageDir = "/qrimage"; //要压缩的目录及目录中的文件
        String zipFileName = "qr.zip"; //zip文件文件名
        File f = new File(imageDir); //图片文件
        File z = new File(zipPath);  //压缩文件
        //如果文件存在删除
        if (f.exists()) {
           // System.out.println("图片文件存在");

            //删除文件
        deleteDir(imageDir);

            f.mkdir();
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

            File qrcFile = new File(imageDir+"/" + b[i] + ".jpg");
            String qrCodeContent = b[i];
            String pressText = b[i];
            generateQrCode(qrcFile, qrCodeContent, pressText);
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


    @GetMapping(value = "/util/returnQrCode")
    public void getCode(HttpServletResponse response) throws FileNotFoundException {
        String zipPath = "/qrzip";

        String fileName = "qr.zip"; //zip文件文件名

        // 读到流中
        InputStream inStream = new FileInputStream(zipPath+"/"+fileName);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
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



package com.sv.mc.util;
import org.apache.poi.hssf.usermodel.*;

public class ExcelUtil {

        /**
         * 导出Excel
         * @param sheetName sheet名称
         * @param title 标题
         * @param values 内容
         * @param wb HSSFWorkbook对象
         * @return
         */
        public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

            // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
            if(wb == null){
                wb = new HSSFWorkbook();
            }

            // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

            //声明列对象
            HSSFCell cell = null;

            //创建标题
            for(int i=0;i<title.length;i++){
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }

            //创建内容
            for(int i=0;i<values.length;i++){
                row = sheet.createRow(i + 1);
                Boolean isNum = false;//data是否为数值型
                Boolean isInteger=false;//data是否为整数
                Boolean isPercent=false;//data是否为百分数
                for(int j=0;j<values[i].length;j++){
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                    if (values[i][j] != null || "".equals(values[i][j])) {
                        //判断data是否为数值型
                        isNum = values[i][j].matches("^(-?\\d+)(\\.\\d+)?$");
                        //判断data是否为整数（小数部分是否为0）
                        isInteger= values[i][j].matches("^[-\\+]?[\\d]*$");
                        //判断data是否为百分数（是否包含“%”）
                        isPercent= values[i][j].contains("%");
                    }
                    if (isNum && !isPercent) {
                        HSSFDataFormat df = wb.createDataFormat(); // 此处设置数据格式
                        if (isInteger) {
                            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,#0"));//数据格式只显示整数
                        }else{
                            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//保留两位小数点
                        }
                        // 设置单元格格式
                        row.createCell(j).setCellStyle(style);
                        // 设置单元格内容为double类型
                        if (values[i][j]!=null){
                        row.createCell(j).setCellValue(Double.parseDouble(values[i][j]));
                        }
                    } else {
                        row.createCell(j).setCellStyle(style);
                        // 设置单元格内容为字符型
                       if (values[i][j]!=null){
                        row.createCell(j).setCellValue(values[i][j]);
                       }
                    }
                }
            }
            return wb;
        }
    }


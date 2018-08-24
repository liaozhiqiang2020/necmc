package com.sv.mc.controller;

import com.sv.mc.pojo.PriceEntity;
import com.sv.mc.service.DeviceService;
import com.sv.mc.service.PriceService;
import com.sv.mc.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class PriceController {

    @Resource
    private PriceService priceService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 分页查询价格列表
     *
     * @param page     当前开始页码（代码中从0开始）
     * @param pageSize 页号
     * @return
     */
    @GetMapping("/price/pageAll")
    public String findAllPagePrice(@RequestParam("page") String page, @RequestParam("pageSize") String pageSize) {
        return this.priceService.findAllPagePrice(Integer.parseInt(page), Integer.parseInt(pageSize));
    }

    /**
     * 不分页查询价格列表,价格状态为可用的
     *
     * @return 价格集合
     */
    @GetMapping("/price/status")
    public String findStatusPrice() {
        String price = this.priceService.findStatusPrice();

        return price;
    }

    /**
     * 不分页查询价格列表,价格状态为可用的并不过期可绑定的
     *
     * @return 价格集合
     */
    @GetMapping("/price/statusOrDate")
    public String findEndPrice() {
        return this.priceService.findPriceEntitiesByEnd();
    }

    /**
     * 不分页查询价格列表
     *
     * @return 价格集合
     */
    @GetMapping("/price/all")
    public List<PriceEntity> findAllPrice() {
        List<PriceEntity> priceList = priceService.findAllPrice();
        for (PriceEntity priceEntity : priceList) {
            int useTime = priceEntity.getUseTime() / 60;
            priceEntity.setUseTime(useTime);
        }
        return priceList;
    }

//    /**
//     * 不分页查询历史价格列表
//     * @return 价格集合
//     */
//    @GetMapping("/price/allHistory")
//    public List<PriceHistoryEntity> findAllHistoryPrice(){
//        List<PriceHistoryEntity> priceList = priceHistoryService.findAllPrice();
//        for (PriceHistoryEntity priceEntity:priceList) {
//            int useTime = priceEntity.getUseTime()/60;
//            priceEntity.setUseTime(useTime);
//        }
//        return priceList;
//    }

    /**
     * 新建价格
     *
     * @param map 价格对象
     */
    @PostMapping("/price/save")
    public PriceEntity addPrice(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        return this.priceService.addPrice(map, request);
    }

    /**
     * 更新价格
     *
     * @param map 价格对象
     */
    @PostMapping("/price/update")
    public PriceEntity updatePrice(@RequestBody Map<String, Object> map) {
        return this.priceService.updatePrice(map);
    }

    /**
     * 批量更新或保存价格
     *
     * @param models 价格对象集合
     */
    @PostMapping("/price/updateList")
    public @ResponseBody
    List<PriceEntity> updatePrice(@RequestBody List<PriceEntity> models) {
        List<PriceEntity> priceEntityList = this.priceService.batchSaveOrUpdatePrice(models);
        for (PriceEntity price : priceEntityList
                ) {
            price.setUseTime(price.getUseTime() / 60);
        }
        return priceEntityList;
    }

    /**
     * 批量删除价格
     *
     * @param models 价格对象集合
     */
    @PostMapping("/price/batchDelete")
    public List<PriceEntity> batchDeletePrice(@RequestBody List<PriceEntity> models) {
        List<PriceEntity> priceEntityList = this.priceService.batchDeletePrice(models);
        return priceEntityList;
    }

    /**
     * 删除价格
     *
     * @param priceEntity 价格Id
     */
    @PostMapping("/price/delete")
    public void deletePrice(@RequestBody PriceEntity priceEntity) {
        this.priceService.deletePrice(priceEntity);
    }

    /**
     * 根据设备id查询价格和时长
     *
     * @param deviceId
     * @return 当前设备的价格，时长集合
     */
    @PostMapping("/price/deviceUse")
    public List<Object[]> findPriceByDeviceId(@RequestParam int deviceId) {

        return this.priceService.findPriceByDeviceId(deviceId);

    }

    /**
     * 根据机器查询当前机器已绑定价格
     *
     * @param deviceId
     * @return
     */
    @GetMapping("/price/devicePrice")
    public Set<PriceEntity> findDevicePrice(@RequestParam("deviceId") int deviceId) {
        Set<PriceEntity> priceList = this.priceService.findDevicePrice(deviceId);
        for (PriceEntity price : priceList
                ) {
            int useTime = price.getUseTime() / 60;
            price.setUseTime(useTime);
        }
        return priceList;
    }

    /**
     * 查询当前设备可绑定的未绑定价格
     *
     * @param deviceId
     * @return 价格集合
     */
    @GetMapping("/price/deviceUnPrice")
    public List<PriceEntity> findDeviceUnPrice(@RequestParam int deviceId) {
        List<PriceEntity> priceList = this.priceService.findUnDevicePrice(deviceId);
        for (PriceEntity priceEntity : priceList) {
            int useTime = priceEntity.getUseTime() / 60;
            priceEntity.setUseTime(useTime);
        }
        return priceList;
    }

    /**
     * 给对应机器绑定价格
     *
     * @param listMap 价格id集合与机器Id
     * @return 保存成功消息
     */
    @PostMapping("/price/deviceSavePrice")
    public List<PriceEntity> deviceSavePrice(@RequestBody Map<String, Object> listMap) {
        return this.priceService.deviceSavePrice(listMap);
    }

    /**
     * 为机器删除其绑定的价格
     *
     * @param listMap 价格Id与机器Id
     * @return 该机器已绑定集合
     */
    @PostMapping("/price/deviceDeletePrice")
    public List<PriceEntity> deviceDeletePrice(@RequestBody Map<String, Object> listMap) {
        return this.priceService.deviceDeletePrice(listMap);
    }

    /**
     * 为场地上某种类型的所有的机器进行价格绑定
     *
     * @param listMap 场地id 与 价格id集合
     * @return 可绑定价格集合
     */
    @PostMapping("/price/placeAddPrice")
    public List<PriceEntity> placeAddPrice(@RequestBody Map<String, Object> listMap) {
        return this.priceService.placeAddPrice(listMap);
    }

    @PostMapping("/weixin/devicePrice")
    public Set<PriceEntity> findDeviceAllPrice(@RequestBody int deviceId){
        return this.priceService.findDeviceAllPrice(deviceId);
    }


//    @GetMapping("/price/status")
//    public List<PriceEntity> priceStatus(@RequestParam int status){
//        return priceService.statusPrice(status);
//    }

    /**
     * 跳转到priceTest页面
     *
     * @return
     */
    @GetMapping(value = "/price/priceTest")
    public ModelAndView turnToRoleMgrTest() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("./priceManagement/priceDemo");
        return mv;
    }

    /**
     * 跳转到priceTest页面
     *
     * @return
     */
    @GetMapping(value = "/price/priceTest1")
    public ModelAndView turnTopriceMgrTest() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("./priceManagement/priceForplace");
        return mv;
    }

//    /**
//     * 跳转到priceTest页面
//     *
//     * @return
//     */
//    @GetMapping(value = "/login1")
//    public ModelAndView Test() {
//        ModelAndView mv = new ModelAndView();
//
//        mv.setViewName("./login");
//        return mv;
//    }

    @GetMapping(value = "/price/export")

    public void exportReport(@RequestParam("id") int id, HttpServletResponse response) {
        //获取数据

        //System.out.println(id);
        List<String> list = this.deviceService.getFill_SN(id);
        //标题
        String[] title = {"设备ID","价格名称", "价格", "使用时长(分)"};
        //文件名
        Date d = new Date();
        String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
        String fileName = "设备价格表" + time + ".xls";
        //sheet 名
        String sheetName = "设备价格表";
        String[][] content = new String[list.size()][0];
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            String obj = list.get(i);
            content[i][0] = obj;
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "iso-8859-1");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName+"\"");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @GetMapping(value = "/price/export1")

    public void exportReport(HttpServletResponse response) {
        //获取数据

        //System.out.println(id);
        List<PriceEntity> list = this.priceService.findAllPagePrice();

        //标题
        String[] title = {"价格名称", "价格", "时长", "创建人", "生效时间", "失效时间", "按摩椅型号", "类型"};
        //文件名
        Date d = new Date();
        String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
        String fileName = "价格管理" + time + ".xls";
        //sheet 名
        String sheetName = "价格管理";
        String[][] content = new String[list.size()][0];
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            PriceEntity priceEntity = (PriceEntity) list.get(i);
            String obj = priceEntity.getPriceName();//价格名称
            String obj1 = priceEntity.getPrice().toString();//价格
            int a = priceEntity.getUseTime();
            String obj2 = String.valueOf(a / 60);//使用时长
            String obj3 = priceEntity.getUser().getName();
            String obj4 = null;
            if (priceEntity.getStartDateTime() == null) {
                obj4 = "";
            } else {
                obj4 = priceEntity.getStartDateTime().toString();
            }
            String obj5 = null;
            if (priceEntity.getEndDateTime() == null) {
                obj5 = "";
            } else {
                obj5 = priceEntity.getEndDateTime().toString();
            }
            String obj6 = priceEntity.getDeviceModelEntity().getName();
            String obj7 = priceEntity.getDeviceModelEntity().getModel();
            content[i][0] = obj;
            content[i][1] = obj1;
            content[i][2] = obj2;
            content[i][3] = obj3;
            content[i][4] = obj4;
            content[i][5] = obj5;
            content[i][6] = obj6;
            content[i][7] = obj7;
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/price/setexcel")
    public Set setExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return this.priceService.getExcel(file);

    }

    ;


}

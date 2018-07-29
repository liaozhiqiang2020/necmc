package com.sv.mc.controller;

import com.sv.mc.pojo.DeviceEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
public class PlaceController {
    //注入
    @Autowired
    private PlaceService placeService;

    /**
     * 全部查询
     * @return 返回所有场地内容
     */
    @GetMapping(value = "/placeMgr/allPlace")
    public @ResponseBody
    List<PlaceEntity> getAll() {
        return this.placeService.findAllEntities();
    }
    /**
     * 根据场地id查询单个场地内容
     * @param id
     * @return 单个分公司内容
     */
    @RequestMapping(value = "/placeMgr/place",method=RequestMethod.GET)
    public @ResponseBody
    PlaceEntity getPlaceById(@PathParam("id") int id ) {
            return this.placeService.findPlaceById(id);
    }

//    /**
//     * 插入一条场地数据
//     * @param place
//     * @return
//     */
//    @RequestMapping(value = "/place/insert",method = RequestMethod.POST)
//    public @ResponseBody
//    PlaceEntity insertPlace(@RequestBody PlaceEntity place){
//        return  placeService.insertPlace(place);
//    }
    /**
     * 更改分场地id更改数据
     * @param id
     * @param place
     * @return
     */
    @RequestMapping(value = "/placeMgr/place/update",method = RequestMethod.POST)
    public @ResponseBody
    PlaceEntity updatePlace(@PathParam("id") int id,@RequestBody PlaceEntity place){
        return placeService.updatePlaceById(id,place);

    }

    /**
     * 跳转到场地树状图页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToPlaceMgr")
    public ModelAndView turnToPlaceMgr(){
        return new ModelAndView("./baseInfo/placeMgr");
    }

    /**
     * 跳转到场地方管理页面
     * @return
     * @auther liaozhiqiang
     * @date 2018/7/11
     */
    @GetMapping(value="/turnToPlaceDetailMgr")
    public ModelAndView turnToPlaceDetailMgr(){
        return new ModelAndView("./baseInfo/placeDetailMgr");
    }


    /**
     * 全部查询
     * @return 返回所有场地内容
     */
    @GetMapping(value = "/placeMgr/getAllPlace")
    public @ResponseBody
    String getAllPlace(@Param("page") String page, @Param("pageSize") String pageSize) {
        return this.placeService.findAllPlaceByPage(Integer.parseInt(page),Integer.parseInt(pageSize));
    }

//    /**
//     * 全部查询
//     * @return 返回所有场地内容
//     */
//    @GetMapping(value = "/placeMgr/getAllPlace")
//    public @ResponseBody
//    String getAllPlace() {
//        return this.placeService.findAllPlace();
//    }

    /**
     * 全部查询
     * @return 返回所有场地内容
     */
    @GetMapping(value = "/placeMgr/getAllPlaceForTreelist")
    public @ResponseBody
    List<PlaceEntity> getAllPlaceForTreelist() {
        System.out.println(this.placeService.findAllPlaces());
        return this.placeService.findAllPlaces();
    }




    /**
     * 插入一条场地数据
     * @param
     * @return
     */
    @RequestMapping(value = "/placeMgr/insertPlace",method = RequestMethod.POST)
    public @ResponseBody
    PlaceEntity insertPlace(@RequestBody Map<String,Object> map){
        return  this.placeService.insertPlace(map);
    }

    /**
     * 更改场地数据
     * @param
     * @return
     */
    @RequestMapping(value = "/placeMgr/updatePlace",method = RequestMethod.POST)
    public @ResponseBody
    PlaceEntity updatePlace(@RequestBody Map<String,Object> map){
        return this.placeService.updatePlace(map);
    }

    /**
     * 插入一条场地数据
     * @param
     * @return
     */
    @RequestMapping(value = "/placeMgr/insertPlaceChild",method = RequestMethod.POST)
    public @ResponseBody
    PlaceEntity insertPlaceChild(@RequestBody Map<String,Object> map){
        return  this.placeService.insertPlaceChild(map);
    }

    /**
     * 更改场地数据
     * @param
     * @return
     */
    @RequestMapping(value = "/placeMgr/updatePlaceChild",method = RequestMethod.POST)
    public @ResponseBody
    PlaceEntity updatePlaceChild(@RequestBody Map<String,Object> map){
        return this.placeService.updatePlaceChild(map);
    }

    /**
     * 逻辑删除场地数据
     */
    @RequestMapping(value = "/placeMgr/deletePlace",method = RequestMethod.POST)
    public @ResponseBody
    void deletePlace(@RequestBody Map<String,Object> map){
        this.placeService.deletePlace(Integer.parseInt(map.get("placeId").toString()));
    }


    /**
     * 根据场地id查询他的字节点
     */
    @GetMapping(value = "/placeMgr/findPlaceByParentId")
    public @ResponseBody
    String findPlaceByParentId(@RequestParam(name = "placeId")int placeId){
        return this.placeService.findPlaceByParentId(placeId);
    }


    @GetMapping("/place/findDeviceByPlace")
    public List<DeviceEntity> findDeviceByPlace1(@RequestParam("placeId") int placeId){
        return this.placeService.findDeviceByPlaceId(placeId);
    }

    /**
     * 根据场地id查询所有设备
     * @param placeId
     * @return
     */
    @GetMapping("/place/findDeviceByPlaceId")
    public List<DeviceEntity> findDeviceByPlace(@RequestParam("placeId") int placeId){
        return this.placeService.findDeviceByPlace(placeId);
    }

    /**
     * 不分页查询第一级场地数据
     */
    @GetMapping("/place/findAllPlaceFirst")
    public @ResponseBody
    List<PlaceEntity> findAllPlaceFirst(){
        return this.placeService.findAllPlaceFirst();
    }

    /**
     * 根据场地id查询所有设备(设备控制)
     * @param placeId
     * @return
     */
    @GetMapping("/place/findDevicesByPlaceId")
    public @ResponseBody
    List<DeviceEntity> findDevicesByPlaceId(@RequestParam("placeId") int placeId){
        return this.placeService.findDeviceByPlace(placeId);
    }
}

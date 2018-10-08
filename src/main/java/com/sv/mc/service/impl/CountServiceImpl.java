package com.sv.mc.service.impl;

import com.sv.mc.pojo.CityEntity;
import com.sv.mc.pojo.PlaceEntity;
import com.sv.mc.pojo.ProvinceEntity;
import com.sv.mc.pojo.qo.ProvinceQo;
import com.sv.mc.repository.CityRepository;
import com.sv.mc.repository.CountRepository;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.repository.ProvinceRepository;
import com.sv.mc.service.CityService;
import com.sv.mc.service.CountService;
import com.sv.mc.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售报表
 */
@Service
public class CountServiceImpl implements CountService {

    @Resource
    private CountRepository repository;


    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PlaceRepository placeRepository;




    /**
     * 一个省报表数据
     * @param pId 省Id
     * @param start 起始时间
     * @param end 截止时间
     * @return 报表信息
     */
    @Override
    public ProvinceQo findProvinceById(int pId, String start , String end) {
        ProvinceQo ProvinceQo = new ProvinceQo();
        List<Object[]> list = this.repository.findCountById(pId,start,end);
        Object[] array = list.get(0);
        String name = (String)array[0];
        int orderCount = Integer.valueOf(array[1].toString());

        int userCount = Integer.valueOf(array[2].toString());

        BigDecimal income =null;
        if(array[3]!=null){
            income= new BigDecimal(Double.valueOf(array[3].toString()));
        }else{
            income=new BigDecimal(0.0000);
        }



        ProvinceQo.setName(name);
        ProvinceQo.setIncome(income);
        ProvinceQo.setOrderCount(orderCount);
        ProvinceQo.setUserCount(userCount);
        return ProvinceQo;
    }

    /**
     * 省下所有市区
     * @param id 省Id
     * @param start 起始时间
     * @param end 截止时间
     * @return 剩下所有市报表
     */
    @Override
    public List<ProvinceQo> findCityByProvinceID(int id, String start, String end) {
            List<Object[]>list =this.repository.findCityByPid(id,start,end);
           // ProvinceQo ProvinceQo = new ProvinceQo();
            List<ProvinceQo>plist=new ArrayList<>();
            for (int i=0;i<list.size();i++){
                ProvinceQo ProvinceQo = new ProvinceQo();
                Object[] array = list.get(i);

                String name = (String)array[0];

                int orderCount = Integer.valueOf(array[1].toString());
                int userCount = Integer.valueOf(array[2].toString());
                BigDecimal income =null;
                if(array[3]!=null){
                    income= new BigDecimal(Double.valueOf(array[3].toString()));
                }else {
                    income = new BigDecimal(0.0000);
                }
                ProvinceQo.setName(name);
                ProvinceQo.setIncome(income);
                ProvinceQo.setOrderCount(orderCount);
                ProvinceQo.setUserCount(userCount);
                plist.add(ProvinceQo);
            }
            return plist;
    }

    /**
     * 市区下所有场地
     * @param id 市Id
     * @param start 起始时间
     * @param end 截至时间
     * @return 市下所有场地报表
     */
    @Override
    public List<ProvinceQo> findPlaceByCityID(int id, String start, String end) {
        List<Object[]>list =this.repository.findPlaceByCityID(id,start,end);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }


    /**
     * 一个市报表
     * @param cId 市id
     * @param start 起始时间
     * @param end 截止时间
     * @return 市报表数据
     */
    @Override
    public ProvinceQo findCityById(int cId, String start, String end) {
        ProvinceQo ProvinceQo = new ProvinceQo();
        List<Object[]> list = this.repository.findOneCTByCityID(cId, start, end);
        Object[] array = list.get(0);
        String name = (String)array[0];
        int orderCount = Integer.valueOf(array[1].toString());

        int userCount = Integer.valueOf(array[2].toString());

        BigDecimal income =null;
        if(array[3]!=null){
            income= new BigDecimal(Double.valueOf(array[3].toString()));
        }else{
            income=new BigDecimal(0.0000);
        }
        ProvinceQo.setName(name);
        ProvinceQo.setIncome(income);
        ProvinceQo.setOrderCount(orderCount);
        ProvinceQo.setUserCount(userCount);
        return ProvinceQo;
    }


    /**
     * 省总数据
     * @param start 起始时间
     * @param end 截止时间
     * @return 省数据
     */
    @Override
    public ProvinceQo findprovince(String start, String end) {
        ProvinceQo ProvinceQo = new ProvinceQo();
        List<Object[]> list = this.repository.findProvince(start, end);
        Object[] array = list.get(0);
        String name = (String)array[0];
        int orderCount = Integer.valueOf(array[1].toString());

        int userCount = Integer.valueOf(array[2].toString());

        BigDecimal income =null;
        if(array[3]!=null){
            income= new BigDecimal(Double.valueOf(array[3].toString()));
        }else{
            income=new BigDecimal(0.0000);
        }
        ProvinceQo.setName(name);
        ProvinceQo.setIncome(income);
        ProvinceQo.setOrderCount(orderCount);
        ProvinceQo.setUserCount(userCount);
        return ProvinceQo;
    }

    /**
     * 查一个场地
     * @param pId 场地Id
     * @param start 起始时间
     * @param end 截止时间
     * @return 一个场地的信息
     */

    @Override
    public ProvinceQo getONEPlaceById(int pId, String start, String end) {
        ProvinceQo ProvinceQo = new ProvinceQo();
        List<Object[]> list = this.repository.findOnePlaceByPlaceID(pId, start, end);
        Object[] array = list.get(0);
        String name = (String)array[0];
        int orderCount = Integer.valueOf(array[1].toString());

        int userCount = Integer.valueOf(array[2].toString());

        BigDecimal income =null;
        if(array[3]!=null){
            income= new BigDecimal(Double.valueOf(array[3].toString()));
        }else{
            income=new BigDecimal(0.0000);
        }
        ProvinceQo.setName(name);
        ProvinceQo.setIncome(income);
        ProvinceQo.setOrderCount(orderCount);
        ProvinceQo.setUserCount(userCount);
        return ProvinceQo;
    }


    /**
     * 查询所有省
     * @param start 起始时间
     * @param end 截止时间
     * @return 所有省的数据
     */
    @Override
    public List<ProvinceQo> getALLProvince(String start, String end) {
        List<Object[]>list =this.repository.findProvince(start,end);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }





    /**
     * 根据 p_id 查询省
     * @param pid 隶属单位Id
     * @return 省信息
     */
    @Override
    public List<ProvinceEntity> getProvinceByP_ID(int pid) {
        return this.provinceRepository.getProvinceByP_ID(pid);
    }

    /**
     * 根据 p_id 查询市
     * @param pid 隶属单位
     * @return 市信息
     */
    @Override
    public List<CityEntity> getCityByP_ID(int pid) {
        return this.cityRepository.getCityByP_ID(pid);
    }

    /**
     * 根据 p_id 查询场地
     * @param pid 隶属单位Id
     * @return 场地信息
     */
    @Override
    public List<PlaceEntity> getPlaceByP_ID(int pid) {
        return this.placeRepository.getPlaceByP_ID(pid);
    }











    /**
     * 无条件查询所有所在省数据
     * @param level
     * @param pid
     * @param start
     * @param end
     */
    @Override
    public List<ProvinceQo> getProvinceBypId(int level, int pid, String start, String end) {
        List<Object[]>list =this.repository.getProvinceBypId(level, pid, start, end);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }

    /**
     * 根据省ID查询所在省
     * @param level
     * @param pid
     * @return
     */
    @Override
    public List<ProvinceQo> getProvinceBypIdANDprovinceID(int level, int pid, String start, String end, int provinceId) {
        List<Object[]>list =this.repository.getProvinceBypIdANDprovinceID(level, pid, start, end, provinceId);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }
    /**
     * 根据省ID查询所有所在市数据
     */

    @Override
    public List<ProvinceQo> getCBypIdANDprovinceID(int level, int pid, String start, String end, int provinceId) {
        List<Object[]> list = this.repository.getCBypIdANDprovinceID(level, pid, start, end, provinceId);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo> plist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String) array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income = null;
            if (array[3] != null) {
                income = new BigDecimal(Double.valueOf(array[3].toString()));
            } else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }
    /**
     * 根据市ID查询所有所在市数据
     */


    @Override
    public List<ProvinceQo> getCityBypIdANDcityID(int level, int pid, String start, String end, int cityId) {
        List<Object[]>list =this.repository.getCityBypIdANDcityID(level, pid, start, end, cityId);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }


    /**
     * 根据市Id 查询所有所在场地数据
     */

    @Override
    public List<ProvinceQo> getPlacyBypIdANDcityID(int level, int pid, String start, String end, int cityId) {
        List<Object[]>list =this.repository.getPlacyBypIdANDcityID(level, pid, start, end, cityId);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }

    /**
     * 根据场地Id 查询所有所在场地数据
     */
    @Override
    public List<ProvinceQo> getPlacyBypIdANDplaceID(int level, int pid, String start, String end, int placeId) {
        List<Object[]>list =this.repository.getPlacyBypIdANDplaceID(level, pid, start, end, placeId);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        return plist;
    }


    /**
     * 最小权限查询场地
     * @param start
     * @param end
     * @param placeId
     * @return
     */
    @Override
    public List<ProvinceQo> getPlacyByANDplaceID(String start, String end, int placeId) {
        List<Object[]>list =this.repository.getPlacyByANDplaceID(start, end, placeId);
        System.out.println(list);
        // ProvinceQo ProvinceQo = new ProvinceQo();
        List<ProvinceQo>plist=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ProvinceQo ProvinceQo = new ProvinceQo();
            Object[] array = list.get(i);

            String name = (String)array[0];

            int orderCount = Integer.valueOf(array[1].toString());
            int userCount = Integer.valueOf(array[2].toString());
            BigDecimal income =null;
            if(array[3]!=null){
                income= new BigDecimal(Double.valueOf(array[3].toString()));
            }else {
                income = new BigDecimal(0.0000);
            }
            ProvinceQo.setName(name);
            ProvinceQo.setIncome(income);
            ProvinceQo.setOrderCount(orderCount);
            ProvinceQo.setUserCount(userCount);
            plist.add(ProvinceQo);
        }
        System.out.println(plist);
        return plist;




    }


}

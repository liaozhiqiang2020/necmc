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


//一个省
    @Override
    public ProvinceQo findProvinceById(int pId, Date start , Date end) {
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
//省下所有市区
    @Override
    public List<ProvinceQo> findCityByProvinceID(int id, Date start, Date end) {
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
//市区下所有场地
    @Override
    public List<ProvinceQo> findPlaceByCityID(int id, Date start, Date end) {
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
//一个市
    @Override
    public ProvinceQo findCityById(int cId, Date start, Date end) {
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


    //省总数据
    @Override
    public ProvinceQo findprovince(Date start, Date end) {
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
     *
     * @return
     */

    @Override
    public ProvinceQo getONEPlaceById(int pId, Date start, Date end) {
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
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<ProvinceQo> getALLProvince(Date start, Date end) {
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




    //根据 p_id 查询省
    @Override
    public List<ProvinceEntity> getProvinceByP_ID(int pid) {
        return this.provinceRepository.getProvinceByP_ID(pid);
    }

    //根据 p_id 查询市
    @Override
    public List<CityEntity> getCityByP_ID(int pid) {
        return this.cityRepository.getCityByP_ID(pid);
    }
    //根据 p_id 查询场地
    @Override
    public List<PlaceEntity> getPlaceByP_ID(int pid) {
        return this.placeRepository.getPlaceByP_ID(pid);
    }


}

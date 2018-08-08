package com.sv.mc.service;

import com.sv.mc.pojo.AreaEntity;
import com.sv.mc.pojo.ProvinceEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface ProvinceService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param province 省
     * @return       ProvinceEntity
     */

    ProvinceEntity save(ProvinceEntity province);

    /**
     * 根据id查询省
     * @param  id
     * @return ProvinceEntity
     */
    ProvinceEntity findProvinceById(int id);

    /**
     * 删除省
     * @param provinceEntity
     */
    void removeProvince(ProvinceEntity provinceEntity);

    /**
     * 增加省份
     * @param provinceEntity
     * @return provinceEntity
     */
      ProvinceEntity  createProvince(ProvinceEntity provinceEntity);

    /**
     * 查询所有的省份
     * @return
     */
      List<ProvinceEntity> selectProvince();


    /**
     * 四级权限查询省
     */

    List<ProvinceEntity>getProvinceByID(int pid);


}

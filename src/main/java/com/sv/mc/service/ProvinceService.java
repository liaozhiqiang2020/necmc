package com.sv.mc.service;

import com.sv.mc.pojo.ProvinceEntity;

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
    ProvinceEntity findProvinceById (int id);


}

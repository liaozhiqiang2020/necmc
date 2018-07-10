package com.sv.mc.service;

import com.sv.mc.pojo.AreaEntity;

/**
 * 接口
 * author:赵政博
 */
public interface AreaService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param branch 大区域
     * @return       AreaEntity
     */
    AreaEntity save(AreaEntity branch);



}

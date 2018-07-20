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


    /**
     * 删除大区 将状态修改为0
     * @param areaEntity
     */

    void removeArea(AreaEntity areaEntity);

    /**
     *
     * @param areaEntity
     * @return 修改大区
     */
    AreaEntity updateArea(AreaEntity areaEntity);


    /***
     * 增加新的大区
     * @param areaEntity
     * @return  返回新的大区
     */
    AreaEntity createArea(AreaEntity areaEntity);


}

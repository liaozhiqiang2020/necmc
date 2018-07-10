package com.sv.mc.service;

import com.sv.mc.pojo.HeadQuartersEntity;

/**
 * 接口
 * author:赵政博
 */
public interface HeadQuartersService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param headQuarters 总公司数据
     * @return       HeadQuartersEntity
     */
    HeadQuartersEntity save(HeadQuartersEntity headQuarters);

    /**2
     * 根据总公司id查询总公司数据
     * @param id  总公司id
     * @return allHeadQuarters
     */
    HeadQuartersEntity findHeadQuartersById(int id);


}

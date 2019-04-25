package com.sv.mc.service;

import com.sv.mc.pojo.ContractEntity;

import java.util.Map;

/**
 * 合同dao
 * Author:赵政博
 * service
 */
public interface ContractService<T> extends BaseService<T> {
    /**
     * 保存数据
     * @param contract 新增的合同
     * @return
     */
    ContractEntity save(ContractEntity contract);

    /**
     * 根据id查询合同数据
     * @param id 合同id
     * @return 合同信息
     */
    ContractEntity findSignById(int id);

    /**
     * 插入一条合同数据
     * @param contract 要增加的合同信息
     * @return 合同数据
     */
    ContractEntity insertSign(ContractEntity contract);

    /**
     * 修改签约信息
     * @param map 签约信息
     *
     */
    void updateContract(Map<String, Object> map);

}

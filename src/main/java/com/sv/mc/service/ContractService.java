package com.sv.mc.service;

import com.sv.mc.pojo.ContractEntity;

/**
 * Author:赵政博
 * service
 */
public interface ContractService<T> extends BaseService<T> {
    /**
     * 保存数据
     * @param contract
     * @return
     */
    ContractEntity save(ContractEntity contract);

    /**
     * 根据id查询合同数据
     * @param id
     * @return
     */
    ContractEntity findSignById(int id);

    /**
     * 插入一条合同数据
     * @param contract
     * @return
     */
    ContractEntity insertSign(ContractEntity contract);


}

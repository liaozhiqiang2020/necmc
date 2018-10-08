package com.sv.mc.service.impl;

import com.sv.mc.pojo.ContractEntity;
import com.sv.mc.repository.ContractRepository;
import com.sv.mc.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 合同逻辑处理层
 */
@Service
public class ContractSeviceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    /**
     * 保存数据
     * @param contractEntity 要增加的合同
     * @return 合同信息
     */
    @Override
    @Transactional
    public ContractEntity save(ContractEntity contractEntity) {
        return contractRepository.save(contractEntity);
    }

    /**
     * 根据id查询合同
     * @param id
     * @return 合同信息
     */
    @Override
    @Transactional
    public ContractEntity findSignById(int id) {
        return contractRepository.findSignById(id);
    }

    /**
     * 插入新一条合同
     * @param contractEntity 合同信息
     * @return 合同信息
     */
    @Override
    @Transactional
    public ContractEntity insertSign(ContractEntity contractEntity) {
        return contractRepository.save(contractEntity);
    }

    /**
     * 查询所有合同
     * @return 合同信息
     */
    @Override
    @Transactional
    public List<ContractEntity> findAllEntities() {
        return contractRepository.findAll();
    }


    /**
     * 修改签约信息
     * @param map 签约信息
     */
    @Override
    @Transactional
    public void  updateContract(Map map) {
        ContractEntity contractEntity = this.contractRepository.findSignById(Integer.parseInt(map.get("id").toString()));
        Object startDateTime = map.get("startDateTime");
        Object endDateTime = map.get("endDateTime");
        Object effectDateTime = map.get("effectDateTime");
        Object contractCode = map.get("contractCode");
        if(startDateTime==null){
            contractEntity.setStartDateTime(null);
        }else{
            contractEntity.setStartDateTime(Timestamp.valueOf(startDateTime.toString()));
        }

        if(endDateTime==null){
            contractEntity.setEndDeteTime(null);
        }else {
             contractEntity.setEndDeteTime(Timestamp.valueOf(endDateTime.toString()));
        }

        if(effectDateTime==null){
            contractEntity.setEffectDateTime(null);
        }else{
            contractEntity.setEffectDateTime(Timestamp.valueOf(effectDateTime.toString()));
        }

        if(contractCode==null){
            contractEntity.setContractCode(null);
        }else{
            contractEntity.setContractCode(contractCode.toString());
        }

         this.contractRepository.save(contractEntity);
    }
}

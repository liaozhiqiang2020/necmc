package com.sv.mc.service.impl;

import com.sv.mc.pojo.ContractEntity;
import com.sv.mc.repository.ContractRepository;
import com.sv.mc.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class ContractSeviceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    /**
     * 保存数据
     * @param contractEntity 合同
     * @return
     */
    @Override
    public ContractEntity save(ContractEntity contractEntity) {
        return contractRepository.save(contractEntity);
    }

    /**
     * 根据id查询合同
     * @param id
     * @return
     */
    @Override
    public ContractEntity findSignById(int id) {
        return contractRepository.findSignById(id);
    }

    /**
     * 插入新一条合同
     * @param contractEntity
     * @return
     */
    @Override
    public ContractEntity insertSign(ContractEntity contractEntity) {
        return contractRepository.save(contractEntity);
    }

    /**
     * 查询所有合同
     * @return
     */
    @Override
    public List<ContractEntity> findAllEntities() {
        return contractRepository.findAll();
    }

    /**
     * 修改签约信息
     * @param map
     * @return
     */
    @Override
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

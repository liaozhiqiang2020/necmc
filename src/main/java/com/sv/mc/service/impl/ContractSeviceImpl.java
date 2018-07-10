package com.sv.mc.service.impl;

import com.sv.mc.pojo.ContractEntity;
import com.sv.mc.repository.ContractRepository;
import com.sv.mc.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractSeviceImpl implements ContractService {

    @Autowired
    private ContractRepository signRepository;

    /**
     * 保存数据
     * @param contractEntity 合同
     * @return
     */
    @Override
    public ContractEntity save(ContractEntity contractEntity) {
        return signRepository.save(contractEntity);
    }

    /**
     * 根据id查询合同
     * @param id
     * @return
     */
    @Override
    public ContractEntity findSignById(int id) {
        return signRepository.findSignById(id);
    }

    /**
     * 插入新一条合同
     * @param contractEntity
     * @return
     */
    @Override
    public ContractEntity insertSign(ContractEntity contractEntity) {
        return signRepository.save(contractEntity);
    }

    /**
     * 查询所有合同
     * @return
     */
    @Override
    public List<ContractEntity> findAllEntities() {
        return signRepository.findAll();
    }
}

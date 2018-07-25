package com.sv.mc.service.impl;

import com.sv.mc.pojo.AccountDetailEntity;
import com.sv.mc.pojo.AccountEntity;
import com.sv.mc.repository.AccountDetailRepository;
import com.sv.mc.repository.AccountRepository;
import com.sv.mc.service.AccountDetailService;
import com.sv.mc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailServiceImpl implements AccountDetailService {
    @Autowired
    private AccountDetailRepository accountDetailRepository;

    /**
     * 新增一条账单详细数据
     * @param accountDetailEntity
     * @return
     */
    @Override
    public AccountDetailEntity createAccountDetail(AccountDetailEntity accountDetailEntity) {
        return this.accountDetailRepository.save(accountDetailEntity);
    }

    /**
     * 查询全部
     * @return
     */
    @Override
    public List findAllEntities() {
        return null;
    }
}

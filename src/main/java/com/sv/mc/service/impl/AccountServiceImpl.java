package com.sv.mc.service.impl;

import com.sv.mc.pojo.AccountEntity;
import com.sv.mc.repository.AccountRepository;
import com.sv.mc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * 新增一条账单数据
     * @param accountEntity
     * @return
     */
    @Override
    public AccountEntity createAccount(AccountEntity accountEntity) {
        return this.accountRepository.save(accountEntity);
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

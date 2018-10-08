package com.sv.mc.service.impl;

import com.sv.mc.pojo.AccountEntity;
import com.sv.mc.repository.AccountRepository;
import com.sv.mc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * 账目实现类
 */
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * 新增一条账单数据
     * @param accountEntity 新增的账单信息
     * @return 账单信息
     */
    @Override
    public AccountEntity createAccount(AccountEntity accountEntity) {
        return this.accountRepository.save(accountEntity);
    }

    /**
     * 查询全部
     * @return 全部的账单
     */
    @Override
    public List findAllEntities() {
        return null;
    }
}

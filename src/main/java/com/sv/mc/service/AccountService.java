package com.sv.mc.service;

import com.sv.mc.pojo.AccountEntity;

public interface AccountService<T> extends BaseService<T> {
    /**
     * 创建一条账单信息
     * @return
     */
    AccountEntity createAccount(AccountEntity accountEntity);
}

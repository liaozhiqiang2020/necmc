package com.sv.mc.service;

import com.sv.mc.pojo.AccountDetailEntity;
public interface AccountDetailService<T> extends BaseService<T> {
    /**
     * 创建一条账单详细信息
     * @return
     */
    AccountDetailEntity createAccountDetail(AccountDetailEntity accountDetailEntity);
}

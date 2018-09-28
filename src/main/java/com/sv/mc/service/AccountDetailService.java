package com.sv.mc.service;

import com.sv.mc.pojo.AccountDetailEntity;

/**
 * 账单
 * @param <T>
 */
public interface AccountDetailService<T> extends BaseService<T> {
    /**
     * 创建一条账单详细信息
     * @return 创建的账单信息
     */
    AccountDetailEntity createAccountDetail(AccountDetailEntity accountDetailEntity);
}

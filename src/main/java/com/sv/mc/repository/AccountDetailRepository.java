package com.sv.mc.repository;

import com.sv.mc.pojo.AccountDetailEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountDetailRepository extends BaseRepository<AccountDetailEntity, Long>, PagingAndSortingRepository<AccountDetailEntity, Long> {

}

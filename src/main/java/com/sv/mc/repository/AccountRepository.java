package com.sv.mc.repository;

import com.sv.mc.pojo.AccountEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.management.Query;

@Repository
public interface AccountRepository extends BaseRepository<AccountEntity, Long>, PagingAndSortingRepository<AccountEntity, Long> {

}

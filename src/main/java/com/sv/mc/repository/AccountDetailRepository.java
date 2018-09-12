package com.sv.mc.repository;

import com.sv.mc.pojo.AccountDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountDetailRepository extends BaseRepository<AccountDetailEntity, Long>, PagingAndSortingRepository<AccountDetailEntity, Long> {

   // 查询昨天的收入
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and TO_DAYS(NOW()) - TO_DAYS(detail_date_time) = 1",nativeQuery = true)
    BigDecimal getInc();

   //3级权限查询昨天的收入
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and TO_DAYS(NOW()) - TO_DAYS(detail_date_time) = 1 " +
            " and mc_account_detail.account_id in( " +
            " select mc_account.id from mc_account where mc_account.place_id in " +
            " (select mc_place.id from mc_place where discard_status=1 and level_flag=3 and superior_id=:pid))",nativeQuery = true)
    BigDecimal getIncThree(@Param("pid")int pid);

    //2级权限查询昨天的收入
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and TO_DAYS(NOW()) - TO_DAYS(detail_date_time) = 1 " +
            " and mc_account_detail.account_id in( " +
            " select mc_account.id from mc_account where mc_account.place_id in " +
            " (select mc_place.id from mc_place where discard_status=1 and level_flag=2 and superior_id=:pid))",nativeQuery = true)
    BigDecimal getIncTwo(@Param("pid")int pid);


    //4级权限查询昨天的收入
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and TO_DAYS(NOW()) - TO_DAYS(detail_date_time) = 1 " +
            " and mc_account_detail.account_id in( " +
            " select mc_account.id from mc_account where mc_account.place_id=:pid " +
            " )",nativeQuery = true)
    BigDecimal getIncFour(@Param("pid")int pid);




}

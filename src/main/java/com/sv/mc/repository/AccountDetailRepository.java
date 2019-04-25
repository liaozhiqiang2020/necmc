package com.sv.mc.repository;

import com.sv.mc.pojo.AccountDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 现金账
 */
@Repository
public interface AccountDetailRepository extends BaseRepository<AccountDetailEntity, Long>, PagingAndSortingRepository<AccountDetailEntity, Long> {


    /**
     *  查询昨天的收入
     * @return  昨天的收入钱数
     */
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and capital_flag=1 and to_days(detail_date_time) = to_days(now())",nativeQuery = true)
    BigDecimal getInc();



    /**
     * 代理商查询昨天收入
     * @param pid 代理商Id
     * @return 昨天收入钱数
     */
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and to_days(detail_date_time) = to_days(now()) " +
            " and mc_account_detail.account_id in( " +
            " select mc_account.id from mc_account where mc_account.place_id in " +
            " (select mc_place.id from mc_place where discard_status=1 and level_flag=3 and superior_id=:pid))",nativeQuery = true)
    BigDecimal getIncThree(@Param("pid") int pid);

    /**
     * 分公司查询昨天收入
     * @param pid 分公司Id
     * @return 昨天收入钱数
     */
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and to_days(detail_date_time) = to_days(now()) " +
            " and mc_account_detail.account_id in( " +
            " select mc_account.id from mc_account where mc_account.place_id in " +
            " (select mc_place.id from mc_place where discard_status=1 and level_flag=2 and superior_id=:pid))",nativeQuery = true)
    BigDecimal getIncTwo(@Param("pid") int pid);


    /**
     * 场地管理查询昨天收入
     * @param pid 场地管理员
     * @return 昨天收入钱数
     */
    @Query(value = "select sum(mc_account_detail.capital) from mc_account_detail where capital_flag=1 and to_days(detail_date_time) = to_days(now()) " +
            " and mc_account_detail.account_id in( " +
            " select mc_account.id from mc_account where mc_account.place_id in(select id from mc_place where FIND_IN_SET(id,getChildrenOrg(:pid))) " +
            " )",nativeQuery = true)
    BigDecimal getIncFour(@Param("pid") int pid);


    /**
     * 根据订单Id 查询收入钱数
     * @param orderId 订单id
     * @return 钱数
     */
    @Query(value="select * from mc_account_detail where from_id=:orderId",nativeQuery = true)
   AccountDetailEntity findAccountDetailEntityByOrderId(@Param("orderId") int orderId);
}

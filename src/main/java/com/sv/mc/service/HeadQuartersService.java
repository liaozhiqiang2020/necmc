package com.sv.mc.service;

import com.sv.mc.pojo.BranchEntity;
import com.sv.mc.pojo.HeadQuartersEntity;
import com.sv.mc.pojo.PlaceEntity;
import org.springframework.data.repository.query.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 接口
 * author:赵政博
 */
public interface HeadQuartersService<T> extends BaseService<T>{
    /**1
     * 保存数据
     * @param headQuarters 总公司数据
     * @return       HeadQuartersEntity 增加的总公司数据
     */
    HeadQuartersEntity save(HeadQuartersEntity headQuarters);

    /**2
     * 根据总公司id查询总公司数据
     * @param id  总公司id
     * @return allHeadQuarters 总公司数据
     */
    HeadQuartersEntity findHeadQuartersById(int id);


    /**
     * 分页查询总公司数据
     * @param page 起始个数
     * @param pageSize 截至个数
     * @param session 用户信息
     * @return  用户所在总公司信息
     */
    String findAllHeadByPage(int page, int pageSize,HttpSession session);

    /**
     * 分页查询总公司数据
     * @return  总公司数据
     */
    List<HeadQuartersEntity> findAllHead();


    /**
     * 根据总公司id更改对应的总公司数据
     * @param headQuartersEntity 修改的信息
     * @return  修改后的总公司数据
     */
    HeadQuartersEntity updateHeadDataById(HeadQuartersEntity headQuartersEntity);

    /**
     * 插入一条总公司数据
     * @param headQuartersEntity  要插入总公司数据
     * @return BranchEntity 总公司数据
     */
    HeadQuartersEntity insertHead(HeadQuartersEntity headQuartersEntity);

    /**
     * 根据id修改状态
     * @param headId 总部Id
     */
    void deleteHead(int headId);

    /**
     * 根据分公司id查询总公司信息
     * @param branchId 分公司Id
     * @return 总公司信息
     */
    HeadQuartersEntity findHeadByBranchId(int branchId);



    /**
     *  根据总公司id查询下面的场地
     * @param headId 总公司Id
     * @return 查询的场地信息
     */
    List<PlaceEntity> findAllPlaceByHeadId(int headId);

    /**
     * 查询所有未绑定的场地
     * @return 未绑定的场地对象
     */
    List<PlaceEntity> findAllUnboundPlace();

    /**
     * 总公司绑定场地
     * @param headId  总公司Id
     * @param placeId  场地Id
     */
    void headBoundPlace(int headId,int placeId);

    /**
     * 解绑场地
     * @param placeId  场地Id
     * @param flagId   删除标识
     * @param supId 上级Id
     */
    void unboundPlace(int placeId,int supId,int flagId);

    /**
     * 根据总公司id查询下面的合同
     * @param headId 总公司id
     * @return  合同信息
     */
    String findContractByHeadId(int headId);


    /**
     * 根据总公司id查询合同历史
     * @param headId  总公司Id
     * @return  合同历史
     */
    String findHistoryContractByHeadId(int headId);
}

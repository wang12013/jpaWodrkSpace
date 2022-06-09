package com.example.myjpawork.repository;

import com.example.myjpawork.entity.Equip;
import com.example.myjpawork.entity.MaintainRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wangzy
 * @date 2022/5/30 16:15
 */
public interface EquipRepository extends JpaRepository<Equip,Integer>, JpaSpecificationExecutor<Equip> {
    /*
    筛选设备，他的维护记录 状态是02
     */
}

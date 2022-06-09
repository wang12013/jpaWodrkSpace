package com.example.myjpawork.repository;

import com.example.myjpawork.entity.Equip;
import com.example.myjpawork.entity.MaintainRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author wangzy
 * @date 2022/5/30 16:20
 */
public interface MaintainRecordRepository extends JpaRepository<MaintainRecord,Integer>, JpaSpecificationExecutor<MaintainRecord> {

    @Query(value = "update maintain_record set last_update_time=? where maintain_id =?" ,nativeQuery = true)
    @Modifying
    @Transactional
    public void updateInsertTimeById(LocalDateTime dateTime,Integer id);

}

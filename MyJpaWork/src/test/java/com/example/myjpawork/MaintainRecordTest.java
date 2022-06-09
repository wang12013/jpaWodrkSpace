package com.example.myjpawork;

import com.example.myjpawork.entity.MaintainRecord;
import com.example.myjpawork.repository.MaintainRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author wangzy
 * @date 2022/6/9 13:48
 */
@SpringBootTest
public class MaintainRecordTest {

    @Autowired
    private MaintainRecordRepository repository;
    @Test
    public void testSave(){
        MaintainRecord maintainRecord = new MaintainRecord();
        //maintainRecord.setMaintainId();主键不设置让他自增长
        maintainRecord.setEquId(31);
        maintainRecord.setFailureReason("摔坏了");
        //maintainRecord.setMaintainBeginTime(LocalDate.now());维护开始时间和结束时间可为空
        maintainRecord.setMaintainStatus("01");

        //损坏时间,和插入时间的区别就是，不能数据库自动生成
        maintainRecord.setFailureTime(LocalDateTime.now());

        //记录创建时间和最新跟新时间，保持为空让数据库自动生成
       // maintainRecord.setInsertTime();

        //保存
        repository.save(maintainRecord);
    }


    @Test

    public void testUpdate(){
        Optional<MaintainRecord> byId = repository.findById(4);
        MaintainRecord maintainRecord = byId.get();
        maintainRecord.setLastUpdateTime(LocalDateTime.now());

        //直接save就是不能编辑这个时间
        repository.save(maintainRecord);

        repository.updateInsertTimeById(LocalDateTime.now(),4);


    }

}

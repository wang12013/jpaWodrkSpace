package com.example.myjpawork;

import com.example.myjpawork.entity.Equip;
import com.example.myjpawork.repository.EquipRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class MyJpaWorkApplicationTests {

    @Autowired
    private EquipRepository equipRepository;

    @Test
    void testSaveEquip() {

        //new LocalDateTime(LocalDate.now(), LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now();//2022-05-30T16:30:20.991
        System.out.println(localDateTime);
        for (int i=0;i<15 ;i++){
            Equip 设备 = new Equip(null, "设备"+i, "01", "01",localDateTime, null,null);
            equipRepository.save(设备);
        }

        /**
         * @Column(columnDefinition = "date")
         *     @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
         *     private LocalDateTime equStartTime;
         *  存到数据库就变成了2022-05-30，要存包含时分秒的日期数据，要使用columnDefinition = "datetime"
         */



    }

}

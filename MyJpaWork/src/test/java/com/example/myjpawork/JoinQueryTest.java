package com.example.myjpawork;

import com.example.myjpawork.entity.Equip;
import com.example.myjpawork.service.EquipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wangzy
 * @date 2022/5/31 9:43
 */
@SpringBootTest
public class JoinQueryTest {

    @Autowired
    private EquipService service;

    @Test
    public void test(){
        List<Equip> list = service.joinMeasureEquip("设备", "01", 5.0);

        for (Equip e : list){
            System.out.println(e);
        }
    }

}

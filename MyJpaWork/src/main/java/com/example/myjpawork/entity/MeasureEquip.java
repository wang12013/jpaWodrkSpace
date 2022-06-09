package com.example.myjpawork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author wangzy
 * @date 2022/5/31 9:47
 */
@Data
@Entity
public class MeasureEquip {
    @Id
    @Column(nullable = false)
    private String equId;

    @Column(nullable = false,length = 2)
    private String measureType;

    //整数位数和小数位数
    @Column(nullable = true,precision = 6,scale = 5,columnDefinition = "decimal(6,5)")
    private Double ifBand;

    @OneToOne
    @JoinColumn(name = "equId")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Equip equip;
}

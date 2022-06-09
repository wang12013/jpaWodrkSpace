package com.example.myjpawork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author wangzy
 * @date 2022/5/31 15:02
 */
@Data
@Entity
public class DirectionFindEquip {

    @Id
    @Column
    private String equId;

    @Column(nullable = true,precision = 14,scale = 7,columnDefinition = "decimal(14,7)")
    private double dfBand;

    @OneToOne
    @JoinColumn(name = "equId")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Equip equip;
}

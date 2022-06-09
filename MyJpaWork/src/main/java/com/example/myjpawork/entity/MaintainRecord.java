package com.example.myjpawork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wangzy
 * @date 2022/5/30 15:31
 */
@Data
@Entity
public class MaintainRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//主键自增
    private Integer maintainId;

    @Column(nullable = false)
    private Integer equId;

    @Column(nullable = false)
    private String failureReason;

    @Column(nullable = false,length = 2,columnDefinition = "char(2) default '01'")
    private String maintainStatus;

    @Column(nullable = true,columnDefinition = "date")
    //不写@JsonFormat
    private LocalDate maintainBeginTime;

    @Column(nullable = true,columnDefinition = "date")
    //不写@JsonFormat
    private LocalDateTime maintainEndTime;


    //todo 改为datetime，注意神通数据库只能用timestamp存时分秒数据
    @Column(nullable = true,columnDefinition = "date")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime failureTime;


    /**
     * 记录创建时间,神通数据库没有datetime，要存时分秒数据只能用timestamp
     * columnDefinition这样写是不是有问题TIME NOT NULL DEFAULT CURRENT_TIMESTAMP,会报错
     */
    @Column(nullable = false,columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @Generated(GenerationTime.ALWAYS)
    private LocalDateTime insertTime;

    /**
     * 记录更新时间,神通数据库没有datetime，要存时分秒数据只能用timestamp
     * columnDefinition这样是不是才对
     *
     * 还是不能编辑这个值
     */
    @Column(nullable = false,columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    //@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @Generated(GenerationTime.ALWAYS)
    private LocalDateTime lastUpdateTime;

    /**
     * 设备信息,只在MaintainRecord表里建立了外键,@ForeignKey(ConstraintMode.CONSTRAINT)就是有外键
     * 没有外键，--@ForeignKey(ConstraintMode.NO_CONSTRAINT)
     */
   /* @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "equId",updatable = false,insertable = false,foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Equip equip;*/

}

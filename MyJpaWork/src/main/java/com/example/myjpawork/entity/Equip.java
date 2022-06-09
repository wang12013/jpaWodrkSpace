package com.example.myjpawork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangzy
 * @date 2022/5/30 15:24
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Equip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//主键自增
    private Integer equId;

    @Column(nullable = false)
    private String equName;

    @Column(nullable = false,length = 2,columnDefinition = "char(2) default '01' ")
    private String equStatus;

    /**
     * 设备类型，01测量设备，02测向设备
     */
    @Column(nullable = false,length = 2,columnDefinition = "char(2) default '01'")
    private String equType;

    @Column(columnDefinition = "datetime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime equStartTime;
    /*

    数据库的时间类型
Mysql中经常用来存储日期的数据类型有三种：Date、Datetime、Timestamp。
【1】Date数据类型：用来存储没有时间的日期。Mysql获取和显示这个类型的格式为“YYYY-MM-DD”。支持的时间范围为“1000-00-00”到“9999-12-31”。
【2】Datetime类型：存储既有日期又有时间的数据。存储和显示的格式为 “YYYY-MM-DD HH:MM:SS”。支持的时间范围是“1000-00-00 00:00:00”到“9999-12-31 23:59:59”。
【3】Timestamp类型：也是存储既有日期又有时间的数据。存储和显示的格式跟Datetime一样。支持的时间范围是“1970-01-01 00:00:01”到“2038-01-19 03:14:07”。

对于神通数据库是没有datetime的，要存日期带时分秒的就只能用timestamp
    所以包含时间时分秒的 应该用 datetime

     */

    /**
     * 维护记录，在这没有在foreignKey里面指定外键，所以Equip表中没有外键。只有维护记录表中有外键
     */
    /*@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="equId",updatable = false,insertable = false)
    @JsonIgnore
    private List<MaintainRecord> maintainRecords;*/


    /**
     * 测量设备拓展信息
     */
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "equId",updatable = false,insertable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MeasureEquip measureEquip;

    /**
     * 测向设备拓展信息
     */
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "equId",updatable = false,insertable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private DirectionFindEquip directionFindEquip;



}

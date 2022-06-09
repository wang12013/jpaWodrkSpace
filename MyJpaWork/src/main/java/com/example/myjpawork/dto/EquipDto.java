package com.example.myjpawork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * @author wangzy
 * @date 2022/6/1 14:23
 */
public class EquipDto {

    private Integer equId;

    private String equName;

    private String equStatus;
    /**
     * 设备类型，01测量设备，02测向设备
     */
    private String equType;

    private LocalDateTime equStartTime;
}

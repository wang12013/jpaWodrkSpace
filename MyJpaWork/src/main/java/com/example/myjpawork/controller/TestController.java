package com.example.myjpawork.controller;

import com.example.myjpawork.dto.EquipDto;
import com.example.myjpawork.dtocheck.BeanValidators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wangzy
 * @date 2022/6/1 14:36
 *
 *
 *valid参数校验不止可以加在dto的属性上，还可以加在这些方法参数上
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private Validator validator;

    @GetMapping("/getOne")
    public String getOne(@PathVariable("equId") @NotBlank(message = "equId不能为空") String equId){

        return null;
    }

    @PostMapping
    public String update(@RequestBody EquipDto equipDto){

        String[] validateStr = beanValidator(equipDto);
        if (! ObjectUtils.isEmpty(validateStr)){
            log.info("接收到请求，但请求参数未通过dto规则校验");
            return validateStr[0];
        }

        return equipDto.toString();

    }


    protected String[] beanValidator(Object object,Class<?>...groups){
        try {
            Set<ConstraintViolation<Object>> constraintViolations = BeanValidators.validateWithException(validator, object, groups);
            if (null != constraintViolations){
                List<String> list = BeanValidators.extractPropertyAndMessageAsList(constraintViolations, "");
                if (list == null){
                    list = new ArrayList<>();
                }
                if (list.size() < 1){
                    list.add(0,"数据验证失败: ");
                }
                return list.toArray(new String[]{});
            }

        }catch (ConstraintViolationException ex){
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            if (list == null){
                list = new ArrayList<>();
            }
            if (list.size() < 1){
                list.add(0,"数据验证失败: ");
            }
            return list.toArray(new String[]{});
        }
        return null;
    }

}

package com.example.myjpawork.service;

import com.example.myjpawork.entity.Equip;
import com.example.myjpawork.entity.MeasureEquip;
import com.example.myjpawork.repository.EquipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzy
 * @date 2022/5/31 9:58
 */
@Service
public class EquipService {

    @Autowired
    private EquipRepository repository;

    public List<Equip> joinMeasureEquip(String equName,String equType,Double ifBand){

        //动态筛选条件，设备名称模糊查询，设备类型01测量设备，测量设备拓展从表信息筛选ifBand>6
        Specification<Equip> query = new Specification<Equip>() {

            @Override
            public Predicate toPredicate(Root<Equip> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();

                //先就这样简单判断，实际开发中要StringUtils.isNtoBlank(equName) && equName != ""
                //前段不传值的参数，可能就是 "" ,
                if (equName != null){
                    //参数记住是属性名，用这个属性名获取这个字段生成筛选条件
                    predicates.add(criteriaBuilder.like(root.get("equName"),"%"+equName+"%"));
                }
                if (equType != null){
                    predicates.add(criteriaBuilder.equal(root.get("equType"),equType));
                }

                if (ifBand != null){
                    //创建连接关系<主表entity,从表entity>,主表中的属性变量名，连接方式
                    Join<Equip, MeasureEquip> join =root.join("measureEquip",JoinType.LEFT);
                    //连接后的--entity中的属性名
                    //这样就能用从表中的字段筛选了
                  /* predicates.add(criteriaBuilder.le(join.get("ifBand"),ifBand));

                   报错:org.hibernate.TypeMismatchException: Provided id of the wrong type for class …

                    */
                    predicates.add(criteriaBuilder.equal(join.get("ifBand"),ifBand));
                }

                //返回每个筛选条件，and连接
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return repository.findAll(query);

    }
}

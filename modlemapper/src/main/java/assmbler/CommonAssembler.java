package assmbler;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

/**
 * @author wangzy
 * @date 2022/5/23 15:25
 *
 * 这个应该只使用一些简单的情形
 */
//@Component 交给spring管理，哪里需要就在哪里注入
@Data
@RequiredArgsConstructor
public class CommonAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * 将DTO对象转换为实体对象
     * @param dto-DTO对象
     * @param entityClass-实体类型信息
     * @param <TEntity>-实体类
     * @param <TDto>-DTO类
     * @return 实体对象
     */
    public <TEntity,TDto> TEntity fromDtoToEntity(TDto dto, Class<TEntity> entityClass){

        return modelMapper.map(dto,entityClass);
    }

    public <TEntity,TDto> TDto fromEntityToDto(TEntity entity,Class<TDto> dtoClass){

        return modelMapper.map(entity,dtoClass);
    }
}

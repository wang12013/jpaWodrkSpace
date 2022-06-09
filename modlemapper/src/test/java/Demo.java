import dto.AppleDto;
import entity.Apple;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;

/*

使用modelMapper，最好是 entity和dto的属性名相同
 */
public class Demo {
 
    public static Apple apple=new Apple("1", "red", "21", "25");;
    ModelMapper modelMapper = new ModelMapper();
 
    @Test
    public void test1(){
        //直接使用modelMapper要求两个对象的属性命名必须完全一致
        AppleDto appleDto = modelMapper.map(apple, AppleDto.class);
        System.out.println(appleDto.toString());//AppleDto(name=red, create_age=null, birth=null)
    }
 
    @Test
    public void test2(){
        //修改modelMapper的匹配策略：松散匹配源属性和目标属性
        //将AppleDto字段改成createage也是可以正确赋值
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AppleDto appleDto = modelMapper.map(apple, AppleDto.class);
        System.out.println(appleDto.toString());//AppleDto(name=red, create_age=21, birth=null)
    }
 
    @Test
    public void test3(){
        //modelMapper采用自定义源属性和目标属性映射规则
        modelMapper.addMappings(customField());
        AppleDto appleDto = modelMapper.map(apple, new TypeToken<AppleDto>(){}.getType());
        System.out.println(appleDto);//AppleDto(name=red, create_age=null, birth=25)

    }
 
    /**
     * 自定义源（Apple）属性和目标（destination）属性映射规则
     */
    private static PropertyMap customField() {
        return new PropertyMap<Apple, AppleDto>() {
            @Override
            protected void configure() {
                map(source.getAge(), destination.getBirth());
            }
        };
    }
}
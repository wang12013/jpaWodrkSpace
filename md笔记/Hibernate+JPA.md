教程：https://www.w3cschool.cn/java/jpa-entitymanager.html 不好

教程：https://blog.csdn.net/qq_43548590/article/details/118944394 还行，简单

总体使用思想：

* 普通基本的增删改查，使用继承自接口中的方法直接调用

* 自定义一些查询时使用，类似之前我用MyBatisPlus时要自己写sql的场景

  * 用@Query注解在方法名上指定sql语句或者jpql语句来查询，在语句中使用？占位符与方法参数匹配

  * 动态的查询条件，可能一个可能两个可能三个筛选查询条件？？？、--有解决方案

  * 对于连接查询，需要使用@oneToOne,@ManyToMany之类的几个注解配置表的外键关联关系。

    * 一对一，一对多，多对一，都很好解决。本质就是一个表有一个外键关联到另一个表的主键id
    * 多对多是怎么配置的呢？ 多对多一般是使用中间表？，JPA怎么配置这个中间表？？

  * 连接查询通过注解指定好表的关联关系后，还是可以使用@Query注解来自定义查询语句

    
    ​	



第二 hibernate框架介绍
	Hibernate是一个开放源代码的对象关系映射框架，
		它对JDBC进行了非常轻量级的对象封装，
		它将POJO与数据库表建立映射关系，是一个全自动的orm框架

第三 JPA规范
	jpa规范，实现jpa规范，内部是由接口和抽象类组成

第四 jpa的基本操作
	案例：是客户的相关操作（增删改查）
		客户：就是一家公司
	客户表：
	

	jpa操作的操作步骤
		1.加载配置文件创建实体管理器工厂
			Persisitence：静态方法（根据持久化单元名称创建实体管理器工厂）
				createEntityMnagerFactory（持久化单元名称）
			作用：创建实体管理器工厂
			
		2.根据实体管理器工厂，创建实体管理器
			EntityManagerFactory ：获取EntityManager对象
			方法：createEntityManager
			* 内部维护的很多的内容
				内部维护了数据库信息，
				维护了缓存信息
				维护了所有的实体管理器对象
				再创建EntityManagerFactory的过程中会根据配置创建数据库表
			* EntityManagerFactory的创建过程比较浪费资源
			特点：线程安全的对象
				多个线程访问同一个EntityManagerFactory不会有线程安全问题
			* 如何解决EntityManagerFactory的创建过程浪费资源（耗时）的问题？
			思路：创建一个公共的EntityManagerFactory的对象
			* 静态代码块的形式创建EntityManagerFactory
			
		3.创建事务对象，开启事务
			EntityManager对象：实体类管理器
				beginTransaction : 创建事务对象
				presist ： 保存
				merge  ： 更新
				remove ： 删除
				find/getRefrence ： 根据id查询
				
			Transaction 对象 ： 事务
				begin：开启事务
				commit：提交事务
				rollback：回滚
		4.增删改查操作
		5.提交事务
		6.释放资源
	
	i.搭建环境的过程
		1.创建maven工程导入坐标
		2.需要配置jpa的核心配置文件
			*位置：配置到类路径下的一个叫做 META-INF 的文件夹下
			*命名：persistence.xml
		3.编写客户的实体类
		4.配置实体类和表，类中属性和表中字段的映射关系
		5.保存客户到数据库中
	ii.完成基本CRUD案例
		persist ： 保存
		merge ： 更新
		remove ： 删除
		find/getRefrence ： 根据id查询
		
	iii.jpql查询
		sql：查询的是表和表中的字段
		jpql：查询的是实体类和类中的属性
		* jpql和sql语句的语法相似
		
		1.查询全部
		2.分页查询
		3.统计查询
		4.条件查询
		5.排序

#### JPA核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/persistence  
    http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
	version="2.0">
	<!--配置持久化单元 
		name：持久化单元名称 
		transaction-type：事务类型
		 	RESOURCE_LOCAL：本地事务管理 
		 	JTA：分布式事务管理 -->
	<persistence-unit name="myJpa" transaction-type="RESOURCE_LOCAL">
		<!--配置JPA规范的服务提供商 -->
		<provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
		<properties>
			<!-- 数据库驱动 -->
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
			<!-- 数据库地址 -->
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/ssh" />
			<!-- 数据库用户名 -->
			<property name="javax.persistence.jdbc.user" value="root" />
			<!-- 数据库密码 -->
			<property name="javax.persistence.jdbc.password" value="111111" />

			<!--jpa提供者的可选配置：我们的JPA规范的提供者为hibernate，所以jpa的核心配置中兼容hibernate的配 -->
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true" />
			<property name="hibernate.hbm2ddl.auto" value="create" />
		</properties>
	</persistence-unit>
</persistence>

```

#### SpringBoot基础配置

```yml
spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/shy
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
  jpa:
    hibernate:
      #定义数据库表的生成策略 create 创建一个表 update 更新或者创建数据表
      ddl-auto: update
      #控制台显示sql语句
    show-sql: true
server:
  port: 80
```

其中，`spring.jpa.hibernate.ddl-auto `参数用来配置是否开启自动更新数据库表结构，可取`create、create-drop、update、validate、none`五个值。

- create 每次加载hibernate时，先删除已存在的数据库表结构再重新生成；
- create-drop 每次加载hibernate时，先删除已存在的数据库表结构再重新生成，并且当 sessionFactory关闭时自动删除生成的数据库表结构；
- update 只在第一次加载hibernate时自动生成数据库表结构，以后再次加载hibernate时根据model类自动更新表结构；
- validate 每次加载hibernate时，验证数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
- none 关闭自动更新



### Entity与表映射

jpa与Mybatsi不同的就是，jpa没有映射配置文件。他依靠于entity类里面的注解将这个类与数据库中的表和字段相映射。

就是一个entity对应一个表

```java
/**
*		* 所有的注解都是使用JPA的规范提供的注解，
 *		* 所以在导入注解包的时候，一定要导入javax.persistence下的
 */
/**
     * @Id：声明主键的配置
     * @GeneratedValue:配置主键的生成策略
     *      strategy
     *          GenerationType.IDENTITY ：自增，mysql
     *                 * 底层数据库必须支持自动增长（底层数据库支持的自动增长方式，对id自增）
     *          GenerationType.SEQUENCE : 序列，oracle
     *                  * 底层数据库必须支持序列
     *          GenerationType.TABLE : jpa提供的一种机制，通过一张数据库表的形式帮助我们完成主键自增
     *          GenerationType.AUTO ： 由程序自动的帮助我们选择主键生成策略
     * @Column:配置属性和字段的映射关系
     *      name：数据库表中字段的名称
     */
@Entity //声明实体类
@Table(name="cst_customer") //建立实体类和表的映射关系
public class Customer {
	
	@Id//声明当前私有属性为主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) //配置主键的生成策略
	@Column(name="cust_id") //指定和表中cust_id字段的映射关系
	private Long custId;
	
	@Column(name="cust_name") //指定和表中cust_name字段的映射关系
	private String custName;
	
	@Column(name="cust_source")//指定和表中cust_source字段的映射关系
	private String custSource;
	
	@Column(name="cust_industry")//指定和表中cust_industry字段的映射关系
	private String custIndustry;
	
	@Column(name="cust_level")//指定和表中cust_level字段的映射关系
	private String custLevel;
	
	@Column(name="cust_address")//指定和表中cust_address字段的映射关系
	private String custAddress;
	
	@Column(name="cust_phone")//指定和表中cust_phone字段的映射关系
	private String custPhone;
	
	public Long getCustId() {
        ----
            
@Entity
        	作用：指定当前类是实体类。
        @Table
        	作用：指定实体类和表之间的对应关系。
        	属性：
        		name：指定数据库表的名称
        @Id
        	作用：指定当前字段是主键。
        @GeneratedValue
        	作用：指定主键的生成方式。。
        	属性：
        		strategy ：指定主键生成策略。
        @Column
        	作用：指定实体类属性和数据库表之间的对应关系
        	属性：
        		name：指定数据库表的列名称。  列名
        		unique：是否唯一  
        		nullable：是否可以为空  
        		inserttable：是否可以插入  
        		updateable：是否可以更新  
        		columnDefinition: 定义建表时创建此列的DDL  列定义
                length 设置字段的列长度 当将字符串字段映射到VARCHAR时我们可以在@Column 注释中设置VARCHAR长度
        		secondaryTable: 从表名。如果此列不建在主表上（默认建在主表），该属性定义该列所在从表的名字搭建开发环境[重点]
           
```

当将Java bean映射到实体时，我们可以在映射注释中设置数据库表列定义

##### @Column的属性

```java
	String name() default "";
    boolean unique() default false;
    boolean nullable() default true;
    boolean insertable() default true;
    boolean updatable() default true;
    String columnDefinition() default "";
    String table() default "";
    int length() default 255;
    int precision() default 0;
    int scale() default 0;
```

```java
@Column(name = "cust_name" ,columnDefinition = "VARCHAR(40)")
private String name;
@Column(columnDefinition="VARCHAR",length=40)
private String address
    
//=================还可以在columnDefinition中指定很多信息，默认值，备注
@Column(name="jobname",nullable=false,columnDefinition=("VARCHA(6) comment '任务名称'"))
private String jobname;

@Column(name="status",nullable=false,columnDefinition=("smallint default 0 comment '任务状态'"))
private Integer status;
```

当将Java float或double值映射到数据库表列时，我们可以设置数字类型列的精度尺度。

以下代码将浮点值映射到具有精度8和尺度2的数据库表列。

```java
@Column(precision=8, scale=2) 
private float hourlyRate;
```

时间类型是可以在持久状态映射中使用的基于时间的类型集合。

支持的时间类型的列表包括三个java.sql类型，java.sql.Date, java.sql.Time和java.sql.Timestamp，以及两个java.util类型，java.util.Date和java.util.Calendar。在JPA中，我们可以创建数据类型列并设置其默认值。

```java
@Column(name = "START_DATE", columnDefinition = "DATE DEFAULT CURRENT_DATE")
private java.sql.Date startDate;
```



如果我们不想将属性保存到数据库，我们可以使用@Transient注释标记该字段。



现在使用的是springDataJPA

他对数据库的简单增删改查操作就类似于mybatis-plus的继承basemapper

**测试方法**

```java
@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(4l);
        System.out.println(customer);
    }

    /**
     * save : 保存或者更新
     *      根据传递的对象是否存在主键id，
     *      如果没有id主键属性：保存
     *      存在id主键属性，根据id查询数据，更新数据
     */
    @Test
    public void testSave() {
        Customer customer  = new Customer();
        customer.setCustName("黑马程序员");
        customer.setCustLevel("vip");
        customer.setCustIndustry("it教育");
        customerDao.save(customer);
    }

    @Test
    public void testUpdate() {
        Customer customer  = new Customer();
        customer.setCustId(4l);
        customer.setCustName("黑马程序员很厉害");
        customerDao.save(customer);
    }

    @Test
    public void testDelete () {
        customerDao.delete(3l);
    }


    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        List<Customer> list = customerDao.findAll();
        for(Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 测试统计查询：查询客户的总数量
     *      count:统计总条数
     */
    @Test
    public void testCount() {
        long count = customerDao.count();//查询全部的客户数量
        System.out.println(count);
    }

    /**
     * 测试：判断id为4的客户是否存在
     *      1. 可以查询以下id为4的客户
     *          如果值为空，代表不存在，如果不为空，代表存在
     *      2. 判断数据库中id为4的客户的数量
     *          如果数量为0，代表不存在，如果大于0，代表存在
     */
    @Test
    public void  testExists() {
        boolean exists = customerDao.exists(4l);
        System.out.println("id为4的客户 是否存在："+exists);
    }


    /**
     * 根据id从数据库查询
     *      @Transactional : 保证getOne正常运行
     *
     *  findOne：
     *      em.find()           :立即加载
     *  getOne：
     *      em.getReference     :延迟加载
     *      * 返回的是一个客户的动态代理对象
     *      * 什么时候用，什么时候查询
     */
    @Test
    @Transactional
    public void  testGetOne() {
        Customer customer = customerDao.getOne(4l);
        System.out.println(customer);
    }

}

```



第一 springDataJpa的概述

第二 springDataJpa的入门操作
	案例：客户的基本CRUD
	i.搭建环境
		创建工程导入坐标
		配置spring的配置文件（配置spring Data jpa的整合）
		编写实体类（Customer），使用jpa注解配置映射关系
	ii.编写一个符合springDataJpa的dao层接口
		* 只需要编写dao层接口，不需要编写dao层接口的实现类
		* dao层接口规范
			1.需要继承两个接口（JpaRepository，JpaSpecificationExecutor）
			2.需要提供响应的泛型
	
	* 
		findOne（id） ：根据id查询
		save(customer):保存或者更新（依据：传递的实体类对象中，是否包含id属性）
		delete（id） ：根据id删除
		findAll() : 查询全部

第三 springDataJpa的运行过程和原理剖析
	1.通过JdkDynamicAopProxy的invoke方法创建了一个动态代理对象
	2.SimpleJpaRepository当中封装了JPA的操作（借助JPA的api完成数据库的CRUD）
	3.通过hibernate完成数据库操作（封装了jdbc）


第四 复杂查询
	i.借助接口中的定义好的方法完成查询
		findOne(id):根据id查询
	ii.jpql的查询方式
		jpql ： jpa query language  （jpq查询语言）
		特点：语法或关键字和sql语句类似
			查询的是类和类中的属性
			
		* 需要将JPQL语句配置到接口方法上
			1.特有的查询：需要在dao接口上配置方法
			2.在新添加的方法上，使用注解的形式配置jpql查询语句
			3.注解 ： @Query
	
	iii.sql语句的查询
			1.特有的查询：需要在dao接口上配置方法
			2.在新添加的方法上，使用注解的形式配置sql查询语句
			3.注解 ： @Query
				value ：jpql语句 | sql语句
				nativeQuery ：false（使用jpql查询） | true（使用本地查询：sql查询）
					是否使用本地查询
					
	iiii.方法名称规则查询
		*是对jpql查询，更加深入一层的封装
		*我们只需要按照SpringDataJpa提供的方法名称规则定义方法，不需要再配置jpql语句，完成查询
		*
			findBy开头：	代表查询
				对象中属性名称（首字母大写）
			*含义：根据属性名称进行查询



### JPQL 语句的使用

他里面是有基础的方法的，同时我们也可以自定义方法，使用@Query注解配置jpql的方式来指定查询的语句，他就类似于sql语句。

jpql语句中的参数可以用占位符来代替 ？

#### 语句参数的使用

##### 占位符传参

**在调用方法时，默认按照参数列表的顺序匹配到占位符上。**

非要作死将参数顺序与占位符顺序不一致。也可以 用在占位符后面加参数的索引(索引从1开始) 如 ----- like ?2 and  name=?1  调用的方法参数(参数1，参数2)

```java
/**
 * 符合SpringDataJpa的dao层接口规范
 *      JpaRepository<操作的实体类类型，实体类中主键属性的类型>
 *          * 封装了基本CRUD操作
 *      JpaSpecificationExecutor<操作的实体类类型>
 *          * 封装了复杂查询（分页）
 */
public interface CustomerDao extends JpaRepository<Customer,Long> ,JpaSpecificationExecutor<Customer> {

    /**
     * 案例：根据客户名称查询客户
     *      使用jpql的形式查询
     *  jpql：from Customer where custName = ?
     *
     *  配置jpql语句，使用的@Query注解
     */
    @Query(value="from Customer where custName = ?")
    public Customer findJpql(String custName);


    /**
     * 案例：根据客户名称和客户id查询客户
     *      jpql： from Customer where custName = ? and custId = ?
     *
     *  对于多个占位符参数
     *      赋值的时候，默认的情况下，占位符的位置需要和方法参数中的位置保持一致
     *
     *  可以指定占位符参数的位置
     *      ? 索引的方式，指定此占位的取值来源
     */
    @Query(value = "from Customer where custName = ?2 and custId = ?1")
    public Customer findCustNameAndId(Long id,String name);

    /**
     * 使用jpql完成更新操作
     *      案例 ： 根据id更新，客户的名称
     *          更新4号客户的名称，将名称改为“黑马程序员”
     *
     *  sql  ：update cst_customer set cust_name = ? where cust_id = ?
     *  jpql : update Customer set custName = ? where custId = ?
     *
     *  @Query : 代表的是进行查询
     *      * 声明此方法是用来进行更新操作
     *  @Modifying
     *      * 当前执行的是一个更新操作
     *
     */
    @Query(value = " update Customer set custName = ?2 where custId = ?1 ")
    @Modifying
    public void updateCustomer(long custId,String custName);

```

##### 使用@Param注解 参数名传参

`:参数名` 就相当于一个占位符  不写:只写个参数名是会报错的

```java
// 传参的第二种形式  :参数名  就能与 @Param("参数名")将方法参数与sql语句参数匹配上
    // :username% 右模糊查询    %:username% 全模糊查询
    @Query(value = "select * from customer where cust_name like :username%", nativeQuery=true)
    public List<Customer> findByArgs(@Param("username") String name);

    @Query(value = "select * from customer where cust_name like :username", nativeQuery=true)
    public List<Customer> findByArgs2(@Param("username") String name);
```

```java
		System.out.println("******使用参数名匹配sql语句参数的方式，语句模糊*****");
        //select * from customer where cust_name like :name%
        List<Customer> byArgs = customerDao.findByArgs("name");
        for (Customer c : byArgs){
            System.out.println(c);
        }

        System.out.println("******使用参数名匹配sql语句参数的方式，参数模糊*****");
        //@Query(value = "select * from customer where cust_name like :username", nativeQuery=true)
        //在语句中不指定模糊，但是在参数中指定模糊, 通用的效果
        List<Customer> byArgs2 = customerDao.findByArgs2("name%");
        for (Customer c : byArgs2){
            System.out.println(c);
        } System.out.println("******使用参数名匹配sql语句参数的方式*****");
        //select * from customer where cust_name like :name%
        List<Customer> byArgs = customerDao.findByArgs("name");
        
```

### 修改操作

https://www.freesion.com/article/90771157078/

### 使用sql 查询

使用sql语句查询，同样使用@Query来配置语句。但是要设置一个属性nativeQuery = true 代表这是使用的sql语句查询。 因为默认的时nativeQuery=false 代表使用的是JPQL语句

```java
 /**
     * 使用sql的形式查询：
     *     查询全部的客户
     *  sql ： select * from cst_customer;
     *  Query : 配置sql查询
     *      value ： sql语句
     *      nativeQuery ： 查询方式
     *          true ： sql查询
     *          false：jpql查询
     *
     */
    //@Query(value = " select * from cst_customer" ,nativeQuery = true)
    @Query(value="select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Object [] > findSql(String name);


```

### 基于方法名的约定

基于方法名的约定，我们就只需要在dao的接口中写一个方法名即可。不需要写其他东西，调用时他能自动根据方法名生成对应的sql语句

**基于方法名的约定，只支持查询？**

```java
/**
     * 方法名的约定：
     *      findBy : 查询
     *            对象中的属性名（首字母大写） ： 查询的条件
     *            CustName
     *            * 默认情况 ： 使用 等于的方式查询
     *                  特殊的查询方式
     *
     *  findByCustName   --   根据客户名称查询
     *
     *  再springdataJpa的运行阶段
     *          会根据方法名称进行解析  findBy    from  xxx(实体类)
     *                                      属性名称      where  custName =
     *
     *      1.findBy  + 属性名称 （根据属性名称进行完成匹配的查询=）
     *      2.findBy  + 属性名称 + “查询方式（Like | isnull）”
     *          findByCustNameLike
     *      3.多条件查询
     *          findBy + 属性名 + “查询方式”   + “多条件的连接符（and|or）”  + 属性名 + “查询方式”
     */
    public Customer findByCustName(String custName);


    public List<Customer> findByCustNameLike(String custName);

    //使用客户名称模糊匹配和客户所属行业精准匹配的查询
    public Customer findByCustNameLikeAndCustIndustry(String custName,String custIndustry);
```

**JPA语法名约定大全**

https://blog.csdn.net/whk_15502266662/article/details/107981898

| 关键词             | SQL符号               | 样列                                                    | 对应JPQL语句片段                       |
| ------------------ | --------------------- | ------------------------------------------------------- | -------------------------------------- |
| And                | and                   | findByLastnameAndFirstname 两个参数                     | where x.lastname=?1 and x.firstname=?2 |
| Or                 | or                    | findByLastnameOrFirstName 两个参数                      | where x.lastname=?1 or x.lastname=?2   |
| Is, Equals         | =                     | findbyFirstName,FindByFirstnameIs,findByFirstnameEquals | where x.firstname=?1                   |
| Between            | between  xx and xx    | findByAgeBetween 两个方法参数                           | where                                  |
| LessThan           | <                     | findByAgelessThan   一个参数                            |                                        |
| LessThanEqual      | <=                    | findByAgeLessThanEqual 一个参数                         |                                        |
| GreaterThan        | >                     | findByAgeGreaterThan                                    |                                        |
| GreaterThanEqual   | >=                    | findByAgeGreaterThanEqual                               |                                        |
| After              | >                     | findByStartDateAfter                                    |                                        |
| Before             | <                     | findByStartDateBefore                                   |                                        |
| IsNull             | is null               | findByAgeIsNull                                         |                                        |
| IsNotNull, NotNull | is not null           | findByAge(Is)NotNull                                    |                                        |
| Like               | like                  | findByFirstNameLike                                     |                                        |
| NotLike            | not like              | findByFirstNameNotLike                                  |                                        |
| StartingWith       | like 'xxx%'           | findByFirstNameStaringWith                              |                                        |
| EndingWith         | like 'xxx%'           | findByFirstNameEndingWith                               |                                        |
| Containing         | like '%xxx%'          | findByFirstNameContaininig                              |                                        |
| OrderBy            | order by              | findByAgeOrderByLastnameDesc (也可Asc)                  |                                        |
| Not                | <>                    | findByLastnameNot                                       | where x.lastname <> ?                  |
| In                 | int()                 | findByAgeIn(Collection<Age> ages)  参数是一个集合       |                                        |
| NotIn              | not in()              | findByAgeNotIn(ColleCtion<Age> ages) 参数是一个集合     |                                        |
| TRUE               | =true                 | findByActiveTrue()                                      |                                        |
| FALSE              | =false                | findByActiveFalse()                                     |                                        |
| IgnoreCase         | upper(xxx)=upper(yyy) | findByFirstnameIgnoreCase                               | where UPPER(x.firstname)=UPPER(?)      |
|                    |                       |                                                         |                                        |

#### 排序查询

```java
 
	@Test
    public void queryTest(){
        //讲义上的写法已经不支持
        /**
         * 创建PageRequest的过程中，需要调用他的构造方法传入两个参数
         *      第一个参数：当前查询的页数（从0开始）
         *      第二个参数：每页查询的数量
         *      第三个参数：一个sort 排序方式？
         *  这个参数与原生的 limit ?,?语句的两个参数是不是一样的
         *
         *  分页参数就是正常的当前页码，每页条数
         */

        // No property cust found for type Customer! Did you mean 'custId'?
        //注意点，写Sort.Order.desc("cust_id") 是数据库的字段名就会报错，这里要写entity的属性名
        Sort sort = Sort.by(Sort.Order.asc("custId"));

        //参数0,3   数据id 1-3
        //参数1,3   数据id 4-6
        //参数2,3   数据id 7-9
        //参数3，3  数据id 10-12
        Pageable pageable =PageRequest.of(2, 3, sort);

        //分页查询
        Page<Customer> page = customerDao.findAll(pageable);
        System.out.println("*******************************");
        System.out.println("*******************************");
        System.out.println(page.getContent()); //得到数据集合列表
        System.out.println(page.getTotalElements());//得到总条数 12
        System.out.println(page.getTotalPages());//得到总页数   4
        System.out.println("*******************************");
        System.out.println("*******************************");

    }

	@Test
    public  void testSort(){

        Sort sort = Sort.by("custId").descending();

        Iterable<Customer> all = repository.findAll(sort);

        System.out.println(all);

    }
```



## 复杂查询

**连表查询？ 分组查询？**

https://www.jb51.net/article/215720.htm

https://www.jb51.net/article/198695.htm



	第一 Specifications动态查询
	
		JpaSpecificationExecutor 方法列表
		
			T findOne(Specification<T> spec);  //查询单个对象
	
			List<T> findAll(Specification<T> spec);  //查询列表
	
			//查询全部，分页
			//pageable：分页参数
			//返回值：分页pageBean（page：是springdatajpa提供的）
			Page<T> findAll(Specification<T> spec, Pageable pageable);
	
			//查询列表
			//Sort：排序参数
			List<T> findAll(Specification<T> spec, Sort sort);
	
			long count(Specification<T> spec);//统计查询
			
		* Specification ：查询条件
			自定义我们自己的Specification实现类
				实现
					//root：查询的根对象（查询的任何属性都可以从根对象中获取）
					//CriteriaQuery：顶层查询对象，自定义查询方式（了解：一般不用）
					//CriteriaBuilder：查询的构造器，封装了很多的查询条件
					Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb); //封装查询条件
			
	第二 多表之间的关系和操作多表的操作步骤
	
		表关系
			一对一
			一对多：
				一的一方：主表
				多的一方：从表
				外键：需要再从表上新建一列作为外键，他的取值来源于主表的主键
			多对多：
				中间表：中间表中最少应该由两个字段组成，这两个字段做为外键指向两张表的主键，又组成了联合主键
	
		讲师对学员：一对多关系
				
		实体类中的关系
			包含关系：可以通过实体类中的包含关系描述表关系
			继承关系
		
		分析步骤
			1.明确表关系
			2.确定表关系（描述 外键|中间表）
			3.编写实体类，再实体类中描述表关系（包含关系）
			4.配置映射关系
	
	第三 完成多表操作
	
		i.一对多操作
			案例：客户和联系人的案例（一对多关系）
				客户：一家公司
				联系人：这家公司的员工
			
				一个客户可以具有多个联系人
				一个联系人从属于一家公司
				
			分析步骤
				1.明确表关系
					一对多关系
				2.确定表关系（描述 外键|中间表）
					主表：客户表
					从表：联系人表
						* 再从表上添加外键
				3.编写实体类，再实体类中描述表关系（包含关系）
					客户：再客户的实体类中包含一个联系人的集合
					联系人：在联系人的实体类中包含一个客户的对象
				4.配置映射关系
					* 使用jpa注解配置一对多映射关系
		
			级联：
				操作一个对象的同时操作他的关联对象
				
				级联操作：
					1.需要区分操作主体
					2.需要在操作主体的实体类上，添加级联属性（需要添加到多表映射关系的注解上）
					3.cascade（配置级联）
				
				级联添加，
					案例：当我保存一个客户的同时保存联系人
				级联删除
					案例：当我删除一个客户的同时删除此客户的所有联系人
					
		ii.多对多操作
			案例：用户和角色（多对多关系）
				用户：
				角色：
		
			分析步骤
				1.明确表关系
					多对多关系
				2.确定表关系（描述 外键|中间表）
					中间间表
				3.编写实体类，再实体类中描述表关系（包含关系）
					用户：包含角色的集合
					角色：包含用户的集合
				4.配置映射关系
				
		iii.多表的查询
			1.对象导航查询
				查询一个对象的同时，通过此对象查询他的关联对象
				
				案例：客户和联系人
				
				从一方查询多方
					* 默认：使用延迟加载（****）
					
				从多方查询一方
					* 默认：使用立即加载



常用注解

一、常用的注解
**1.@Entity**
@Entity 用于定义对象将会成为被 JPA 管理的实体，将字段映射到指定的数据库表中

**2.@Table**
@Table 用于指定数据库的表名

**3.@Id**
@Id 定义属性为数据库的主键，一个实体里面必须有一个，并且必须和@GeneratedValue 配合使用和成对出现

**4.@GeneratedValue**
@GeneratedValue 主键生成策略

public enum GenerationType {undefined
//通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
TABLE,
//通过序列产生主键，通过 @SequenceGenerator 注解指定序列名， MySql 不支持这种方式；
SEQUENCE,
//采用数据库ID自增长， 一般用于mysql数据库
IDENTITY,
//JPA 自动选择合适的策略，是默认选项；
AUTO
}

**5.@IdClass**
@IdClass 利用外部类的联合主键

作为复合主键类，要满足以下几点要求。
必须实现 Serializable 接口。
必须有默认的 public 无参数的构造方法。
必须覆盖 equals 和 hashCode 方法。equals 方法用于判断两个对象是否相同，EntityManger 通过 find 方法来查找 Entity
时，是根据 equals 的返回值来判断的。本例中，只有对象的 name 和 email 值完全相同时或同一个对象时则返回 true，否
则返回 false。hashCode 方法返回当前对象的哈希码，生成 hashCode 相同的概率越小越好，算法可以进行优化。

**6.@Basic**
@Basic 表示属性是到数据库表的字段的映射。如果实体的字段上没有任何注解，默认即为 @Basic。

**7.@Transient**
@Transient 表示该属性并非一个到数据库表的字段的映射，表示非持久化属性。JPA 映射数据库的时候忽略它，与 @Basic 相反的
作用

**8.@Column**
@Column 定义该属性对应数据库中的列名
不写则默认数据库字段采用下划线命名方式与属性名对应。
例：private String userName; 数据库中则生成user_name

**9.@Temporal**
@Temporal 用来设置 Date 类型的属性映射到对应精度的字段。

@Temporal(TemporalType.DATE)映射为日期 // date （只有日期）
@Temporal(TemporalType.TIME)映射为日期 // time （是有时间）
@Temporal(TemporalType.TIMESTAMP)映射为日期 // date time （日期+时间）



**多表关联关系注解**
@OneToOne、@JoinColumn、@ManyToOne、@ManyToMany、@JoinTable、@OrderBy

1）@JoinColumn 定义外键关联的字段名称
用法：@JoinColumn 主要配合 @OneToOne、@ManyToOne、@OneToMany 一起使用，单独使用没有意义。

2）@OneToOne 一对一关联关系
用法 @OneToOne 需要配合 @JoinColumn 一起使用。注意：可以双向关联，也可以只配置一方

假设一个学生对应一个班级，添加学生的同时添加班级，Student类：

@OneToOne(cascade = CascadeType.PERSIST)
//关联的外键字段
@JoinColumn(name = “grade_id”)
private Grade grade;

grade_id 指的是t_student表中的字段，cascade属性设置级联操作



### 连接查询

**连接查询的级联更新？Update?**

#### 一对一操作

`在每个关系中，双方中的一方在其表中拥有连接列。那么一方称为所有方(owning side) 或者关系的所有者。`

`不具有连接列的一方称之为非所有方(non-owning)或者反方`

`所有权对于映射很重要，因为用于定义映射到数据库序列的物理注解(例如，@JoinColumn总是在关系的所有方定义)`

**什么是外键？**

**指的是从表中有一列，取值参照主表的主键，这一列就是外键。**

JPA分单项和双向一对一映射？

```java
/*
* 有一个客户表customer表， 有一个customer_helper表。一个customer对应一个helper
 *
 * customer_helper表的c_id字段与customer表的cust_id关联
 
 在customer类中包含一个helper对象，就配置了一对一关系
*/
@Entity //声明实体类
@Table(name="customer") //建立实体类和表的映射关系
public class Customer {
    
    *****其他属性
        
    *****配置一对一    
    @OneToOne
    @JoinColumn(name = "cust_id",insertable=false,updatable=false)
    //name属性指定被关联的customer表中的外键字段 ，表里我设置的外键级联更新插入删除等都是false
    //在JoinColumn里默认的insertable和updatable属性默认值都是true，所以要设置
    private CustomerHelper customerHelper;
}
```

##### @JoinColumn 注解的属性

@JoinColumn用于定义主键字段和外键字段的对应关系。

里面的属性

```java
	String name() default "";  //指定外键字段的名称
    String referencedColumnName() default ""; //指定引用主表的主键字段名称
    boolean unique() default false; //是否唯一。默认值不唯一
    boolean nullable() default true;  //是否允许为空。默认值允许
    boolean insertable() default true;  //是否允许插入。默认值允许。
    boolean updatable() default true; //是否允许更新。默认值允许。
    String columnDefinition() default "";  //列的定义信息。
    String table() default "";  //
    ForeignKey foreignKey() default @ForeignKey(ConstraintMode.PROVIDER_DEFAULT); //
```



```java
@Test
    public void customerOntoOneTest(){
        Optional<Customer> daoById = customerDao.findById(5L);
        System.out.println("============根据id查询一个的结果================");

        // System.out.println(daoById);  //这一行报栈溢出？？？
        //使用hibernate时，在对象的多对一或一对多关系中，两个实体类中都添加了toString方法，
        //原因是toString 方法递归造成的，两个对象的同toString中都包含对方，
        // 会发生循环调用，造成栈内存溢出，从以下的报错中也可得出印证
        //为了能够进行接下来的测试我就把customerHelper李米娜的包含customer注释掉了

        /*
        这个连接查询可以查到customer表中的所有数据12个customer，但是customerHelper中是只有7个的
        一个customer对应一个helper。目前有一些customer是没有对应的helper的。
        但是查询结果显示
       
        这个连接查询是一个外连接查询，并且因为是customer包含helper，所以customer表是主表。能查出customer的所有数据
        与之匹配的helper就显示在其后面。没有匹配的helper就用null填充
         */
        List<Customer> list = customerDao.findAll();
        System.out.println("================findAll查询结果=================");
        for (Customer customer : list){
            System.out.println(customer);
        }
```





#### 多对一

多个学生在一个班级

```java
@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "c_id")
    private Integer cId;

    @ManyToOne(targetEntity=StudentClass.class)
    @JoinColumn(name = "c_id",insertable=false,updatable = false) //表示与student表的外键列是c_id，与对应的entity的主键映射
    private StudentClass studentClass;
}
```

Dao

```java
public interface ManyToOneDao extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student> {

    //多个学生对应一个班级

    //可以根据学生的id查询，相关信息

    //我怎么查1班的学生的信息呢？
    //用班级做连接查询的筛选条件
    /*
    SELECT * FROM student st INNER JOIN student_class  c
    WHERE st.`c_id`=c.`id`
    AND c.`id`=3;
     */

    //nativeQuery属性表示使用ssql语句查询
    @Query(value = "SELECT * FROM student st INNER JOIN student_class  c WHERE st.c_id=c.id AND c.id=?",nativeQuery = true)
    List<Student> findByClassId(Integer id);
    //这个查询测试是正常运行的，注解配置了多对一的对应关系，那么只要SQL预计正确那就能查询了

    @Query(value = "SELECT * FROM student st INNER JOIN student_class c WHERE st.c_id=c.id AND st.`age`> ? AND NAME NOT LIKE ? ORDER BY c.`cls_name`",nativeQuery = true)
    List<Student> findByAgeAndName(Integer age,String name);

}
```

test

```java
 /* 多对一查询，多个学生对应一个班级 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ManyToOneTest {

    @Autowired
    private ManyToOneDao manyToOneDao;

    @Test
    public void findAllTest(){
        List<Student> list = manyToOneDao.findAll();
        System.out.println("=============findAll结果===========");
        for (Student stu : list){
            System.out.println(stu);
        }
    }

    /**
     * 只查找三班的学生
     */
    @Test
    public void findByClassId(){
        List<Student> byClassId = manyToOneDao.findByClassId(3);
        System.out.println("=============findByClassId结果===========");
        for (Student stu : byClassId){
            System.out.println(stu);
        }
    }

    /*
    SELECT * FROM student st INNER JOIN student_class c
    WHERE st.c_id=c.id AND st.`age`>15 AND NAME NOT LIKE '%文文%' ORDER BY c.`cls_name`
     */

    @Test
    public void findByAgeAndName(){
        List<Student> byAgeAndName = manyToOneDao.findByAgeAndName(15, "%文文%");
        System.out.println("=============findByNameAndAge结果===========");
        for (Student stu : byAgeAndName){
            System.out.println(stu);
        }
    }

}
```





#### 多对多 与关联中间表

@JoinTable 是指如果对象与对象之间有个关联关系表的时候，就会用到这个，一般和 @ManyToMany 一起使用

用法：假设 Blog 和 Tag 是多对多的关系，有个关联关系表 blog_tag_relation ，表中有两个属性 blog_id 和 tag_id ，那么 Blog 实
体里面的写法如下：

```java
@Entity
public class Blog{
	@ManyToMany
	@JoinTable(
		name="blog_tag_relation",
		joinColumns=@JoinColumn(name="blog_id",referencedColumnName="id"),
		inverseJoinColumn=@JoinColumn(name="tag_id",referencedColumnName="id")
		private List<Tag> tags = new ArrayList<Tag>();
	)
}
```

@ManyToMany 表示多对多，和 @OneToOne、@ManyToOne 一样也有单向双向之分，单项双向和注解没有关系，只看实体类之间是否相互引用。 主要注意的是当用到 @ManyToMany 的时候一定是三张表，不要想着偷懒，否则会发现有很多麻烦





### 动态查询语句

**在查询某些数据的过程中，有可能只需要某些字段，而不是全部字段，这就需要我们使用动态sql，在使用的过程
中需要我们的`Repository`接口继承`JpaSpecificationExecutor`接口，然后查询的时候，传入动态查询参数，分页参
数等即可**

编写查询`CatRepository`，使其继承`JpaSpecificationExecutor`接口就可以使用
`Specification`进行`动态SQL查询`了,举例代码如下

#### 动态查询 1.Query by Example

注意：不支持嵌套或分组的属性约束，如firstname=? or (age=? and level=?) 并且只支持字符串start/contains/dens/reges匹配和其他属性的精准匹配

```java
public interface CustomerQBERepository
        extends PagingAndSortingRepository<Customer,Long>
          , QueryByExampleExecutor<Customer> {    
}
```

```java
 /**
     * 简单实例  客户名称  客户地址动态查询
     */
    @Test
    public  void test01(){

        // 查询条件，从前端传过来的，
        Customer customer=new Customer();
        customer.setCustName("徐庶");
        customer.setCustAddress("BEIJING");

        // 通过Example构建查询条件
        Example<Customer> example = Example.of(customer);//要选springdata包下的，不是hibernate包下的

        List<Customer> list = (List<Customer>) repository.findAll(example);
        System.out.println(list);
    }


    /**
     *  通过匹配器 进行条件的限制
     * 简单实例  客户名称  客户地址动态查询
     */
    @Test
    public  void test02(){

        // 查询条件
        Customer customer=new Customer();
        customer.setCustName("庶");
        customer.setCustAddress("JING");

        // 通过匹配器 对条件行为进行设置
        ExampleMatcher matcher = ExampleMatcher.matching()
                //.withIgnorePaths("custName")       // 设置忽略的属性
                //.withIgnoreCase("custAddress")      // 设置忽略大小写
                //.withStringMatcher(ExampleMatcher.StringMatcher.ENDING);    // 对所有条件字符串进行了结尾匹配
                .withMatcher("custAddress",m -> m.endsWith().ignoreCase());      // 针对单个条件进行限制, 会使withIgnoreCase失效，需要单独设置
                //.withMatcher("custAddress", ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase());

        // 通过Example构建查询条件
        Example<Customer> example = Example.of(customer,matcher);

        List<Customer> list = (List<Customer>) repository.findAll(example);
        System.out.println(list);
    }
```

#### 动态查询 2.通过Specifications

```java
//继承接口JpaSpecificationExecutor
public interface CustomerSpecificationsRepository
        extends PagingAndSortingRepository<Customer,Long>,
        JpaSpecificationExecutor<Customer> {
}
```

**1.用root指定查询的条件字段**

**2.用cb设置条件字段与值的关系，equal,greaterThan(>)等**

**3. 用cb.and() 表示将多个条件用and连接，也可以用or连接。如果需要排序的话就要用query来进行组合**

因为同样是用的findall方法，通用可以给方法一个分页参数，进行分页查询

```java
    //Predicate 
    public  void testR(){

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                return null;
            }
        });

        System.out.println(customer);
    }


    /**
     * 查询客户范围 (in)
     * id  >大于
     * 地址  精确
     */
    @Test
    public  void testR2(){

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                Path<Object> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");

                // 参数1 ：为哪个字段设置条件   参数2：值
                ////这里也就只设置了一个查询条件，所以实际开发中，就要挨个判断前端传过来的条件是否为null，不为null就设置这个查询条件
                Predicate predicate = cb.equal(custAddress, "BEIJING");

                return predicate;
            }
        });

        System.out.println(customer);
    }


    /**
     * 查询客户范围 (in)
     * id  >大于
     * 地址  精确
     */
    @Test
    public  void testR3(){

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // 参数1 ：为哪个字段设置条件   参数2：值
                Predicate custAddressP = cb.equal(custAddress, "BEIJING");
                Predicate custIdP = cb.greaterThan(custId, 0L);
                CriteriaBuilder.In<String> in = cb.in(custName);
                in.value("徐庶").value("王五");
                Predicate and = cb.and(custAddressP, custIdP,in);

                return and;
            }
        });

        System.out.println(customer);
    }


    /**
     * 查询客户范围 (in)
     * id  >大于
     * 地址  精确
     */
    @Test
    public  void testR4(){

        Customer params=new Customer();
        //params.setCustAddress("BEIJING");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {


                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)

                // 1. 通过root拿到需要设置条件的字段
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // 2. 通过CriteriaBuilder设置不同类型条件
                List<Predicate> list=new ArrayList<>();
                if(!StringUtils.isEmpty(params.getCustAddress())) {
                    // 参数1 ：为哪个字段设置条件   参数2：值
                    list.add(cb.equal(custAddress, "BEIJING")) ;
                }
                if(params.getCustId()>-1){
                    list.add(cb.greaterThan(custId, 0L));
                }

                if(!StringUtils.isEmpty(params.getCustName())) {
                    CriteriaBuilder.In<String> in = cb.in(custName);
                    in.value("徐庶").value("王五");
                    list.add(in);
                }


                // 组合条件
                Predicate and = cb.and(list.toArray(new Predicate[list.size()]));

                return and;
            }
        });

        System.out.println(customer);
    }


    @Test
    public  void testR5(){

        Customer params=new Customer();
        //params.setCustAddress("BEIJING");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // 参数1 ：为哪个字段设置条件   参数2：值
                List<Predicate> list=new ArrayList<>();
                if(!StringUtils.isEmpty(params.getCustAddress())) {
                    list.add(cb.equal(custAddress, "BEIJING")) ;
                }
                if(params.getCustId()>-1){
                    list.add(cb.greaterThan(custId, 0L));
                }

                if(!StringUtils.isEmpty(params.getCustName())) {
                    CriteriaBuilder.In<String> in = cb.in(custName);
                    in.value("徐庶").value("王五");
                    list.add(in);
                }


                Predicate and = cb.and(list.toArray(new Predicate[list.size()]));

                Order desc = cb.desc(custId);

                return query.where(and).orderBy(desc).getRestriction();
            }
        });

        System.out.println(customer);
    }
```

#### 动态查询3.Querydsl

QueryDSL是基于ORM框架或SQL平台上的一个通用查询框架。借助QueryDSL可以在任何支持的ORM框架或SQL平台
上以通用API方式构建查询。
JPA是QueryDSL的主要集成技术，是JPQL和Criteria查询的代替方法。目前QueryDSL支持的平台包括
JPA,JDO,SQL,Mongodb 等等。。。

Querydsl扩展能让我们以链式方式代码编写查询方法。该扩展需要一个接口QueryDslPredicateExecutor，它定义了很
多查询方法。

```java
//继承接口
interface UserRepository extends CrudRepository<User, Long>, QuerydslPredicateExecutor<User> {
 }
```

2.引入依赖

```xml
 <querydsl.version>4.4.0</querydsl.version>
 <apt.version>1.1.3</apt.version>

 <!‐‐ querydsl ‐‐>
 <dependency>
	 <groupId>com.querydsl</groupId>
 	<artifactId>querydsl‐jpa</artifactId>
	 <version>${querydsl.version}</version>
 </dependency>
```

3.用插件来创建Q类 mvn compile之后就会生成。 然后设置，把生成的哪个q类所在的文件夹在projectStructure中设置为代码文件夹

```xml
 <plugins>
            <plugin>
                <groupId>com.mysema.maven</groupId>
                <artifactId>apt-maven-plugin</artifactId>
                <version>${apt.version}</version>
                <dependencies>
                    <dependency>
                        <groupId>com.querydsl</groupId>
                        <artifactId>querydsl-apt</artifactId>
                        <version>${querydsl.version}</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>process</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>target/generated-sources/queries</outputDirectory>
                            <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                            <logOnlyOnError>true</logOnlyOnError>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
```



```java
 /**
     * 查询客户名称范围 (in)
     * id  >大于
     * 地址  精确
     *
     * 动态的条件
     */
    @Test
    public  void test03() {

        //模拟从前端拿到的查询参数
        Customer params=new Customer();
        params.setCustAddress("BEIJING");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

		//插件生成的Q类
        QCustomer customer = QCustomer.customer;

        // 初始条件 类似于1=1 永远都成立的条件
        BooleanExpression expression = customer.isNotNull().or(customer.isNull());

        // 三目运算符，判断是否有值,有值就将其设为查询条件，没有就不添加查询条件
        expression=params.getCustId()>-1?
                expression.and(customer.custId.gt(params.getCustId())):expression;
        expression=!StringUtils.isEmpty( params.getCustName())?
                expression.and(customer.custName.in(params.getCustName().split(","))):expression;
        expression=!StringUtils.isEmpty( params.getCustAddress())?
                expression.and(customer.custAddress.eq(params.getCustAddress())):expression;


        System.out.println(repository.findAll(expression));

    }

```











### 分页查询

`Pageable `是 Spring 封装的分页实现类，使用的时候需要传入页数、每页条数和排序规则。

`Spring Data JPA` 已经帮我们内置了分页功能，在查询的方法中，需要传入参数 `Pageable`，当查询中有多个参数的时候 `Pageable` 建议作为最后一个参数传入,

`Pageable`使用的时候需要传入页数、每页条数和排序规则，排序规则可省略

```java
 /**
     * 分页查询
     *      Specification: 查询条件
     *      Pageable：分页参数
     *          分页参数：查询的页码，每页查询的条数
     *          findAll(Specification,Pageable)：带有条件的分页
     *          findAll(Pageable)：没有条件的分页
     *  返回：Page（springDataJpa为我们封装好的pageBean对象，数据列表，共条数）
     */
    @Test
    public void testSpec4() {

        Specification spec = null;
        //PageRequest对象是Pageable接口的实现类
        /**
         * 创建PageRequest的过程中，需要调用他的构造方法传入两个参数
         *      第一个参数：当前查询的页数（从0开始）
         *      第二个参数：每页查询的数量
         *  这个参数与原生的 limit ?,?语句的两个参数是不是一样的 
         */
        Pageable pageable = new PageRequest(0,2);
        //分页查询
        Page<Customer> page = customerDao.findAll(null, pageable);
        System.out.println(page.getContent()); //得到数据集合列表
        System.out.println(page.getTotalElements());//得到总条数
        System.out.println(page.getTotalPages());//得到总页数
    }


//==========================service=========================？？？？
public Page<Shop> pageShop(Integer pageNo, Integer pageSize, Pageable pageable) {
    //注意排序这找的是实体类中的字段，不是数据库里的
    //分页页码从0开始
    pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC, "shopId");
    return shopRepository.findAll(pageable);
}

//=========================controller======================
@GetMapping("/pageShop/{pageNo}/{pageSize}")
public String pageShop(@PathVariable Integer pageNo,
                       @PathVariable Integer pageSize,Pageable pageable){
    
    return JSON.toJSONString(shopService.pageShop(pageNo, pageSize, pageable));
}

//===============由于版本更新上面两种写法已经有问题=================
新写法
https://blog.csdn.net/weixin_44900565/article/details/105614972

 Sort sort = Sort.by(Sort.Order.desc("cust_id"));
        Pageable pageable =PageRequest.of(0, 2, sort);

        //分页查询
        Page<Customer> page = customerDao.findAll(pageable);
        System.out.println(page.getContent()); //得到数据集合列表
        System.out.println(page.getTotalElements());//得到总条数
        System.out.println(page.getTotalPages());//得到总页数
```









##### 总体示例

```java
ublic interface CatRepository extends JpaRepository<Cat, Integer>, JpaSpecificationExecutor<Cat> {

	// ----------------------------
	// 只要继承了JpaRepository,就获得了所有的增删改查技能.你见或者不见,基本增删改查都在这里
	// ---------------------------

	// 示例内容在 com.cyj.springboot.ServiceImpl.CatServiceImpl
	// ----------------------------以上是JpaRepository已经实现好的基本增删改查------------------

	// ----------------------------以下是自定义条件查询--------------------------------------

	// Cat实体的属性: id, name, sex, age, birthday, updateTime,catParam

	// And --- 等价于 SQL 中的 and 关键字，比如 findByAgeAndSex(int Age,char sex)；
	public List<Cat> findByNameAndSex(String name, String sex);

	// Or --- 等价于 SQL 中的 or 关键字，比如 findByAgeOrSex(int Age,char sex)；
	public List<Cat> findByNameOrSex(String name, String sex);

	// Between --- 等价于 SQL 中的 between 关键字，比如 findByAgeBetween(int min, int max)；
	public List<Cat> findByAgeBetween(int min, int max);

	// LessThan --- 等价于 SQL 中的 "<"，比如 findByAgeLessThan(int max)；
	public List<Cat> findByAgeLessThan(int max);

	// GreaterThan --- 等价于 SQL 中的">"，比如 findByAgeGreaterThan(int min)；
	public List<Cat> findByAgeGreaterThan(int min);

	// IsNull --- 等价于 SQL 中的 "is null"，比如 findByNameIsNull()；
	public List<Cat> findByNameIsNull();

	// IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByNameIsNotNull()；
	public List<Cat> findByNameIsNotNull();

	// NotNull --- 与 IsNotNull 等价；
	public List<Cat> findByNameNotNull();

	// Like --- 等价于 SQL 中的 "like"，比如 findByNameLike(String name);
	public List<Cat> findByNameLike(String name);

	// NotLike --- 等价于 SQL 中的 "not like"，比如 findByNameNotLike(String name)；
	public List<Cat> findByNameNotLike(String name);

	// OrderBy --- 等价于 SQL 中的 "OrderBy"，比如 findByNameNotNullCatByAgeAsc()；
	public List<Cat> findByNameNotNullOrderByAgeAsc();

	// Not --- 等价于 SQL 中的 "！ ="，比如 findByNameNot(String name)；
	public List<Cat> findByNameNot(String name);

	// In --- 等价于 SQL 中的 "in"，比如 findByNameIN(String name);
	public List<Cat> findByNameIn(String name);

	// NotIn --- 等价于 SQL 中的 "not in"，比如 findByNameNotIN(String name);
	public List<Cat> findByNameNotIn(String name);

	// And --- 等价于 SQL 中的 and 关键字，比如 findByAgeAndSex(int Age,char sex)；
	public List<Cat> findByAgeAndSex(int Age, char sex);

	// Or --- 等价于 SQL 中的 or 关键字，比如 findByAgeOrSex(int Age,char sex)；
	public List<Cat> findByAgeOrSex(int Age, char sex);

	// StartingWith findByNameStartingWith ... where x.name like ?1(parameter bound
	// with appended %)
	public List<Cat> findByNameStartingWith(String name);

	// EndingWith findByNameEndingWith ... where x.name like ?1(parameter bound with
	// prepended %)
	public List<Cat> findByNameEndingWith(String name);

	// Containing findByNameContaining ... where x.name like ?1(parameter bound
	// wrapped in %)
	public List<Cat> findByNameContaining(String name);

	// OrderBy findByAgeOrderByName ... where x.age = ?1 order by x.name desc
	public List<Cat> findByNameOrderByAge(String name, Integer age);
	// True findByActiveTrue ... where x.avtive = true
	// public List<Cat> findByActiveTrue();
	// Flase findByActiveFalse ... where x.active = false
	// public List<Cat> findByActiveFalse();

	// Like --- 等价于 SQL 中的 "like"，比如 findByNameLike(String name);
	public List<Cat> findByNameLike(String name, Sort sort);

	public Page<Cat> findByNameLike(String name, Pageable pageable);

	// ******************** []HQL 方式 ] 序号参数*******************

	// Cat实体的属性: id, name, sex, age, birthday, updateTime

	// 以HQL方式获取数据

	// 前面介绍的获取数据的方式都没有使用到任何的HQL语句，那些方法已经可以满足很多需求，也有时候会觉得方法名太长不太方便，下面介绍一下使用Hql方式获取数据：

	// 在ICatService中加入 ：

	@Query("FROM Cat c WHERE c.name=?1 AND c.sex IS NOT NULL")
	List<Cat> findAll(String name);

	/*
	 * 测试方法
	 * 
	 * @Test public void testQuery() { List<Cat> list = CatService.findAll("Cat3");
	 * System.out.println(list.size()); }
	 */

	// 修改数据

	// 在ICatService接口中写一个修改的方法，只要涉及修改或删除数据的操作都需要加上注释@Modifying和@Transcational（Transcational是org.springframework.transaction.annotation包中的不要导错了）

	@Query("UPDATE Cat c SET c.age=?2 WHERE c.id=?1")
	@Modifying
	@Transactional
	void updatePwd(Integer id, Integer age);

	/*
	 * 测试方法
	 * 
	 * @Test public void testUpdate() { CatService.updatePwd(1, 100); }
	 */

	// 删除数据

	// 在ICatService接口中的方法：

	@Query("DELETE FROM Cat c WHERE c.name=?1")
	@Modifying
	@Transactional
	void deleteByCatName(String name);

	/*
	 * 测试方法
	 * 
	 * @Test public void testDelete() { CatService.deleteByCatName("Cat4"); }
	 */

	// 在上面的操作方式中参数传递都是以一种有序的方式传递的，另外还有一种更为直观的[命名参数]方式来传递参数，下面举个例子说明：
	// ******************** []HQL 方式 ] 序号参数*******************
	// 注意： 在参数传中参数前加注释@Param并指定名称，在@Query中使用:名称的方式来传递参数。

	// 在接口ICatService中添加方法：

	@Query("UPDATE Cat c SET c.sex= :sex WHERE c.id = :id")
	@Modifying
	@Transactional
	void updateEmail(@Param("id") Integer id, @Param("sex") String sex);

	/*
	 * 测试方法
	 * 
	 * @Test public void testUpdate2() { CatService.updateCat(1, "女"); }
	 */
	@Query(" SELECT MAX(c.age) FROM Cat c ")
	public Long maxAge();

	@Query("select c from Cat c where  c.name like %:name% ")
	List<Cat> queryByname(@Param(value = "name") String name);

	// **************************一些复杂查询[原生的SQl]***********************
	// 一些比较复杂的关联查询要怎么实现呢，JPA的处理方法是：利用[原生的SQl]命令来实现那些复杂的关联查询，下面就来看下案例。

	// 通过设置 nativeQuery = true 来设置开启使用数据库原生SQL语句

	// 利用原生的SQL进行查询操作
	@Query(value = "select c.* from ordertb o ,cattb u where o.uid=u.id and u.name=?1", nativeQuery = true)
	@Modifying
	public List<Cat> findCatByName(String name);

	// 利用原生的SQL进行删除操作
	@Query(value = "delete from cattb where id=?1 ", nativeQuery = true)
	@Modifying
	public void deleteCatById(int id);

	// 利用原生的SQL进行删除操作
	@Query(value = "delete from cattb where uid=?1 ", nativeQuery = true)
	@Modifying
	public void deleteCatByUId(int uid);

	// 利用原生的SQL进行修改操作
	@Query(value = "update cattb set name=?1 where id=?2 ", nativeQuery = true)
	@Modifying
	public void updateCatName(String name, int id);

	// 利用原生的SQL进行插入操作
	@Query(value = "insert into cattb(name,uid) value(?1,?2)", nativeQuery = true)
	@Modifying
	public void insertCat(String name, int uid);

	@Query(value = " SELECT * FROM cattb WHERE NAME LIKE %:name% ", nativeQuery = true)
	List<Cat> queryBynameSQL(@Param(value = "name") String name);

	// *******************JPA分页*******************************
	// JPA是怎么实现分页的效果，其实JPA脱胎于hibernate，所以本身就对分页功能有很好的支持。

	// 实现分页功能
	Page<Cat> findByNameNot(String name, Pageable pageable);

	// @RequestMapping(value = "/params")
	// @ResponseBody
	/*
	 * public String getEntryByParams(String name, Integer page,Integer size) { Sort
	 * sort = new Sort(Sort.Direction.DESC, "id"); Pageable pageable = new
	 * PageRequest(page, size, sort); Page<Cat>
	 * pages=CatDao.findByNameNot(name,pageable); Iterator<Cat> it=pages.iterator();
	 * while(it.hasNext()){ System.out.println("value:"+((Cat)it.next()).getId()); }
	 * return "success...login...."; }
	 */

	/*
	 * 上面的代码一个是在dao层中的，一个是在controller中的。
	 * 
	 * dao层中添加一个返回值为Page，参数值为Pageable。controller层中通过实例化Pageable这个类，然后调用dao层这个分页方法。
	 * 
	 * 通过这些步骤就可以轻轻松松的实现分页的效果啦，看起来是不是特别方便。
	 */

	// 最后在给大家介绍一下JPA是如何实现事务操作的。其实因为SpringBoot中已经对事务做了很好的封装了，使用起来特别方便。下面看一下案例：

	/*
	 * @RequestMapping("/saveCat")
	 * 
	 * @ResponseBody
	 * 
	 * @Transactional() public String saveCat(){ Cat o1=new Cat("11",2); Cat o2=new
	 * Cat("22",2); Cat o3=new Cat("33",2); Cat o4=new Cat("44",2); CatDao.save(o1);
	 * CatDao.save(o2); CatDao.save(o3); CatDao.save(o4); return
	 * "successfull....saveCat......"; }
	 */

	/*
	 * 只要在方法的上面加上@Transaction 这个注解就可以轻轻松松的实现事务的操作了，是不是特别方便啊。 不过这里有几点需要注意的是：
	 * 
	 * 1.这个注解实现的事务管理器是默认的，如果不想要默认是事务管理器，可以自己进行添加，我这里就不多介绍了。
	 * 
	 * 2.事务的隔离级别也是可以自己设置的。
	 * 
	 * 3.事务的传播行为也是可以自己设置的。
	 */

}
```



#### Entity映射详细介绍

```
JPA教程 - JPA ElementCollection字符串映射示例
JPA(Java Persistence API)是 Sun 官方提出的 Java 持久化规范。它为Java开发人员提供了一种对象/关系映射工具来管理Java应用中的关系数据
JPA规范要求在类路径的 META-INF 目录下放置 persistence.xml
JPA 中将一个类注解成实体类 (entity class) 有两种不同的注解方式：基于属性 (property-based) 和基于字段 (field-based) 的注解
基于字段的注解, 就是直接将注解放置在实体类的字段的前面
基于属性的注解, 就是直接将注解放置在实体类相应的getter方法前面(这一点和 Spring 正好相反),但是同一个实体类中必须并且只能使用其中一种注解方式
Entity、Table、Id、GeneratedValue、Basic、Column、Temporal、Transient、Lob、Transient、SecondaryTable、Embeddable、Embedded
JPA注解
(1)Entity
@javax.persistence.Entity(name="xxx") 
name 指定实体Bean的名称,默认值为 bean class 的非限定类名,select o from xxx o where o.id=?1
(2)Table
@javax.persistence.Table(catalog="xx",name="xx",schema="xx",uniqueConstraints={ @UniqueConstraint(columnNames={"xx","xx"})})
name:指定表的名称
catalog:指定数据库名称
schema:指定数据库的用户名
uniqueConstraints:指定唯一性字段约束,如为personid 和name 字段指定唯一性约束
uniqueConstraints={ @UniqueConstraint(columnNames={"personid", "name"})}
(3)Id
@javax.persistence.Id()
映射到数据库表的主键的属性,一个实体只能有一个属性被映射为主键.
(4)GeneratedValue
@javax.persistence.GeneratedValue(generator="xxx",strategy=GenerationType.AUTO)
strategy:表示主键生成策略,有 AUTO,INDENTITY,SEQUENCE 和 TABLE 4种
分别表示让 ORM 框架自动选择,根据数据库的Identity字段生成,根据数据库表的 Sequence 字段生成,以有根据一个额外的表生成主键,默认为 AUTO 
generator:表示主键生成器的名称,这个属性通常和 ORM 框架相关,例如,Hibernate 可以指定 uuid 等主键生成方式. 
Hibernate UUID
@Id @GeneratedValue(generator="system-uuid")
@GenericGenerator(name="system-uuid",strategy = "uuid")
(5)Basic
@javax.persistence.Basic(fetch=FetchType.LAZY,optional=true)
fetch:抓取策略,延时加载与立即加载
optional:指定在生成数据库结构时字段是否允许为 null
(6)Column
@javax.persistence.Column(length=15,nullable=false,columnDefinition="",insertable=true,scale=10,table="",updatable=true)
@Column 注解指定字段的详细定义
name:字段的名称,默认与属性名称一致 
nullable:是否允许为 null,默认为 true
unique:是否唯一,默认为 false 
length:字段的长度,仅对 String 类型的字段有效 
columnDefinition:表示该字段在数据库中的实际类型
通常 ORM 框架可以根据属性类型自动判断数据库中字段的类型,
但是对于 Date 类型仍无法确定数据库中字段类型究竟是 DATE,TIME 还是 TIMESTAMP,
此外,String 的默认映射类型为 VARCHAR,如果要将 String 类型映射到特定数据库的 BLOB 或 TEXT 字段类型,该属性非常有用
如: @Column(name="BIRTH",nullable="false",columnDefinition="DATE") 
insertable:默认情况下,JPA 持续性提供程序假设所有列始终包含在 SQL INSERT 语句中。
如果该列不应包含在这些语句中，请将 insertable 设置为 false 
updatable：列始终包含在 SQL UPDATE 语句中。如果该列不应包含在这些语句中，请将 updatable 设置为 false 
table:实体的所有持久字段都存储到一个其名称为实体名称的数据库表中,如果该列与 @SecondaryTable 表关联
需将 name 设置为相应辅助表名称的String名称
(7)Temporal
@javax.persistence.Temporal(TemporalType.DATE)
value:TemporalType.DATE,TemporalType.TIME,TemporalType.TIMESTAMP时间类型格式
(8)Enumerated
@javax.persistence.Enumerated(EnumType.STRING)
value:EnumType.STRING,EnumType.ORDINAL
枚举类型成员属性映射,EnumType.STRING 指定属性映射为字符串,EnumType.ORDINAL 指定属性映射为数据序
(9)Lob
@javax.persistence.Lob
用于标注字段类型为 Clob 和 Blob 类型
Clob(Character Large Ojects) 类型是长字符串类型,实体的类型可为 char[]、Character[]、或者String 类型
Blob(Binary Large Objects) 类型是字节类型,实体的类型可为 byte[]、Byte[] 或者实现了 Serializable 接口的类。
通常使用惰性加载的方式, @Basic(fetch=FetchType.LAZY)
(10)Transient
@javax.persistence.Transient
@Transient 表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性
(11)SecondaryTable 
@javax.persistence.SecondaryTable
将一个实体映射到多个数据库表中
如：
@Entity
@SecondaryTables({ 
@SecondaryTable(name = "Address"), 
@SecondaryTable(name = "Comments") 
})
public class Forum implements Serializable {
@Column(table = "Address", length = 100) 
private String street; 
@Column(table = "Address", nullable = false) 
private String city; 
@Column(table = "Address") 
private String conutry; 
@Column(table = "Comments") 
private String title; 
@Column(table = "Comments") 
private String Comments; 
@Column(table = "Comments") 
}
table 属性的值指定字段存储的表名称
没有用 @Column 注解改变属性默认的字段将会存在于 Forum 表
(12)@Embeddable
@javax.persistence.Embeddable
嵌套映射,在被嵌套的类中使用 Embeddable 注解,说明这个就是一个可被嵌套的类,使用 @Embedded
当同一个类被不同的注解方式的类嵌套时，可能会出现一些错误，使用 @Access(AccessType. FIELD)设定被嵌套类的注解方式 

================================================================================================
(1)
@Entity注解定义
@Target(TYPE) @Retention(RUNTIME)
public @interface Entity{
String name() default ""; //实体bean的名称
}


(2)
@Table注解定义
@Target(value = {ElementType.TYPE}) 
@Retention(value = RetentionPolicy.RUNTIME) 
public @interface Table { 
public String name() default ""; //表的名称
public String catalog() default ""; //数据库名称
public String schema() default ""; //数据库用户名
public UniqueConstraint[] uniqueConstraints() default {}; //指定多个字段唯一性约束 
}


(3)
@UniqueConstraint注解定义
public @interface UniqueConstraint{
String[] columnNames( ); //唯一字段属性名称
}


(4)
@Id注解定义
@Target({METHOD, FIELD}) @Retention(RUNTIME) 
public @interface Id{ }


(5)
@注解GeneratedValue定义
@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface GeneratedValue{
GenerationType strategy() default AUTO; //主键生成策略
String generator() default "";
}


(6)
@Column注解定义
@Target(value = {ElementType.METHOD, ElementType.FIELD}) 
@Retention(value = RetentionPolicy.RUNTIME) 
public @interface Column { 
public String name() default ""; //数据库中的列名
public boolean unique() default false; //该列是否唯一
public boolean nullable() default true; //是否可以为空
public boolean insertable() default true; 
public boolean updatable() default true; 
public String columnDefinition() default ""; 
public String table() default ""; 
public int length() default 255; //该列的最大长度
public int precision() default 0; 
public int scale() default 0; 
}


(7)
@Temporal注解定义
public enum TemporalType{
DATE, //代表 date类型 java.sql.Date 2008-08-08 
TIME, //代表时间类型 java.sql.Time 20:00:00
TIMESTAMP //代表时间 java.sql.Timestamp 2008-08-08 20:00:00.000000001
}
public enum TemporalType{
DATE, //代表 date类型 //java.sql.Date 2008-08-08 
TIME, //代表时间类型 //java.sql.Time 20:00:00
TIMESTAMP //代表时间 //java.sql.Timestamp 2008-08-08 20:00:00.000000001
}
以下部分显示如何将Java java.util.Map映射到数据库表。
```

如何将Java集合映射到数据库。它使用`@ElementCollection`注释来标记集合中的元素类型。



在JPA中，我们可以将通用类型的Map映射到数据库。

以下代码定义了一个通用映射，其键值为Employee，值类型为Integer。

```java
@ElementCollection
@CollectionTable(name="EMP_SENIORITY")
@MapKeyJoinColumn(name="EMP_ID")
@Column(name="SENIORITY")
private Map<Employee, Integer> seniorities;
```

JPA ElementCollection枚举映射示例，`PhoneType `是枚举类型。

```java
 @ElementCollection
    @CollectionTable(name="EMP_PHONE")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="PHONE_TYPE")
    @Column(name="PHONE_NUM")
    private Map<PhoneType, String> phoneNumbers ;
```



##### 公司代码

```java
@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "equId",updatable = false,insertable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private DirectionFindEquip directionFindEquip;


fetch是指定的
cascade是指定的
@ForeignKey是指定外键，   效果上ConstraintMode.NO_CONSTRAINT不会在表里生成外键，ConstraintMode.CONSTRAINT这里就会有一个外键
```


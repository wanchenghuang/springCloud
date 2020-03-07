##mybatis-plus入门(1)：mybatis的概念与基础使用

###mybatis-plus的概念
    简称 MP是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
###mybatis-plus的特性
    ①无侵入：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
    ②损耗小：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
    ③强大的 CRUD 操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
    ④支持 Lambda 形式调用：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
    ⑤支持主键自动生成：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
    ⑥支持 ActiveRecord 模式：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
    ⑦支持自定义全局通用操作：支持全局通用方法注入（ Write once, use anywhere ）
    ⑧内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
    ⑨内置分页插件：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
    ⑩分页插件支持多种数据库：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer2005、SQLServer 等多种数据库
###快速使用mybatis-plus
#####现在有一张user表，表结构如下所示：
<table>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>age</td>
        <td>email</td>
    </tr>
    <tr>
        <td>1</td>
        <td>Jone</td>
        <td>18</td>
        <td>Jone@126.com</td>
    </tr>
    <tr>
        <td>2</td>
        <td>Jack</td>
        <td>20</td>
        <td>Jack@126.com</td>
    </tr>
    <tr>
        <td>3</td>
        <td>Tom</td>
        <td>28</td>
        <td>Tom@126.com</td>
    </tr>
    <tr>
        <td>4</td>
        <td>Sandy</td>
        <td>21</td>
        <td>Sandy@126.com</td>
    </tr>
</table>

#####建表语句如下
    CREATE TABLE student
    (
        id BIGINT(20) NOT NULL COMMENT '主键ID',
        name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
        age INT(11) NULL DEFAULT NULL COMMENT '年龄',
        email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
        PRIMARY KEY (id)
    );    
#####现在回到springboot中，引入下面的依赖
        <dependency>
        	groupId>org.projectlombok</groupId>
        	<artifactId>lombok</artifactId>
        	<version>1.16.20</version>
        	<scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
            <scope>runtime</scope>
        </dependency>
#####上面的依赖分辨是：①lombok ：用于简化实体类中get/set 构造方法的插件 ②mybatis-plus-boot-starter：mybatis-plus启动器 ③mysql-connector-java java连接mysql客户端 
#####导入完相关依赖之后，需要进行配置文件的编写：
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/mp?useSSL=false&serverTimezone=GMT%2B8
        username: root
        password: 123456
    logging:
      level:
        root: warn
        com.mybatis.mybatis.dao: trace
      pattern:
        console: '%p%m%n'
#####编写与数据库中的表相对应的实体类，在上述例子中有四个属性，所以实体类应该写成如下形式。实体类放在entity下
    @Data
    public class Student {
        //主键
        private Integer id;
        //姓名
        private String name;
        //年龄
        private Integer age;
        //邮箱
        private String email;
    }
######在类上加入了@Data注解，在编译之后实现了get/set等常用的方法
#####编写完实体类后，我们再编写一个与User对应的接口，放在dao包下，代码如下
    public interface StudentMapper extends BaseMapper<Student> {
    
    }
#####此时，我们需要让springboot扫描到该接口，所以主函数代码更改如下：
    @SpringBootApplication
    @MapperScan("com.mybatis.mybatis.dao")
    public class MybatisApplication {
        public static void main(String[] args) {
            SpringApplication.run(MybatisApplication.class, args);
        }
    }
#####加入了@MapperScan("com.mybatis.mybatis.dao")，指定扫描mapper所在的包路径
#####最后，我们在测试类中注入StudentMapper接口，并实现查询全部学生的功能
    @Autowired
    private StudentMapper studentMapper;
    @Test
    public void test1(){
        List<Student> students = studentMapper.selectList(null);
        students.forEach(System.out::println);
    }
#####这样，就完成了查询所有学生的功能
   
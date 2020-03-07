##mybatis-plus进阶(1)
###1.逻辑删除
####概念;计算机中的资料等都以文件的方式进行存储，删除文件有两种情况，一种是逻辑删除，一种是物理删除
####逻辑删除：文件并没有被真正的删除，只是在被删除的数据上打上一个删除标记，在逻辑上数据是被删除了，但是数据本身依然存在。
####实现步骤：①修改配置文件
    mybatis-plus:
      global-config:
        db-config:
          id-type: auto
          #逻辑未删除 设置为 0
          logic-not-delete-value: 0
          logic-delete-value: 1
####②在实体类字段中添加Integer类型的deleted，并在该字段上添加注解@TableLogic
    @Data
    @TableName("mp_student")
    @EqualsAndHashCode(callSuper = false)
    public class Student extends Model<Student> {
        @TableId(type = IdType.AUTO)
        private Long id;
        @TableField("name")
        private String name;
        private Integer age;
        private String email;
        @TableLogic
        private Integer deleted;
    }
####③在测试类中尝试进行删除操作
    @Test
    public void delete(){
        studentService.removeById(7);
    }
####此时，再次去查询Id=7的学生信息，就会发现 其中deleted属性的值已经变为1
####被逻辑删除的数据，使用mp进行查询和更新功能是无法查到相应的数据的
###2.自动填充功能
####我们在有些字段未被初始化赋值的时候，可以通过自己配置信息进行初始化
####比如说我想对日期进行初始化，先对student表添加字段 time datetime
####在实体类的指定字段添加注解 @TableField(fill = FieldFill.INSERT_UPDATE),指定需要自动填充的字段
    @Data
    @TableName("mp_student")
    @EqualsAndHashCode(callSuper = false)
    public class Student extends Model<Student> {
        @TableId(type = IdType.AUTO)
        private Long id;
        @TableField("name")
        private String name;
        private Integer age;
        private String email;
        @TableLogic
        private Integer deleted;
        @TableField(fill = FieldFill.INSERT_UPDATE)
        private Date time;
    }
####FieldFill里可以进行更新、新增指定，也可以同时指定更新和新增
####创建component包，在component包下创建MyMetaObjectHandler类，并实现接口MetaObjectHandler，代码如下：
    @Component
    public class MyMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            setInsertFieldValByName("time",new Date(),metaObject);
        }
        @Override
        public void updateFill(MetaObject metaObject) {
            setUpdateFieldValByName("time",new Date(),metaObject);
        }
    }
####也可以在两种方法里使用一个通用的方法setFieldValByName(),在insertFill和updateFill都适用
####此时，特意不给time进行赋值，直接进行插入，代码如下：
    @Test
    public void insert(){
        Student student = new Student();
        student.setName("测试自动");
        student.setAge(13);
        student.setEmail("dasd");
        studentService.save(student);
    }
####此时time会自动为我们进行赋值
####但此时问题又来了，如果我们此时的time参数需要多次调用，比较消耗性能，并且我们不是每个实体都要进行自动赋值，所以代码需要进行改动：
    @Component
    public class MyMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            if (metaObject.hasSetter("time")) {
                setInsertFieldValByName("time", new Date(), metaObject);
            }
        }
        @Override
        public void updateFill(MetaObject metaObject) {
            if (metaObject.hasSetter("time")) {
                setUpdateFieldValByName("time",new Date(),metaObject);
            }
        }
    }
####使用mataObject调用hasSetter()方法进行判断，这个实体类是否有指定的参数，如果没有就不进行自动填充
####此时需求又来了，如果我们已经对该填充的值已经进行了初始赋值，即使用setTime()方法进行了初始化，此时为了让我们设置的能够生效，代码更改如下：
    @Component
    public class MyMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            if (getFieldValByName("time",metaObject) == null) {
                setInsertFieldValByName("time", new Date(), metaObject);
            }
        }
        @Override
        public void updateFill(MetaObject metaObject) {
            if (getFieldValByName("time",metaObject) == null) {
                setUpdateFieldValByName("time",new Date(),metaObject);
            }
        }
    }
###3.乐观锁
####乐观锁的执行流程：
#####①取出记录时，获取当前version
#####②更新时，带上这个version
#####③版本正确更新成功，错误则更新失败
#####在mp中的使用步骤：
#####①前往配置类进行相关配置组件的注入，代码如下：
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }
#####②给version字段添加注解 @Version
    @Data
    @TableName("mp_student")
    @EqualsAndHashCode(callSuper = false)
    public class Student extends Model<Student> {
        @TableId(type = IdType.AUTO)
        private Long id;
        @TableField("name")
        private String name;
        private Integer age;
        private String email;
        @TableLogic
        private Integer deleted;
        @TableField(fill = FieldFill.INSERT_UPDATE)
        private Date time;
        @Version
        private Integer version;
    }
#####进行代码的测试，首先要获得指定更新表的version信息，再将version传入对象内，直接进行update操作
    @Test
    public void versionTest(){
        Student student = new Student();
        student.setId(8l);
        student.setName("乐观锁测试");
        student.setEmail("乐观锁测试");
        student.setAge(1);
        //查询出当前的版本
        int version = student.selectById().getVersion();
        //将当前版本赋给当前的student，并更新
        student.setVersion(version);
        studentService.updateById(student);
    }
#####此时，更新完成后，可以发现数据库中的Version信息自增了 
#####特别说明：①支持的数据类型只有int，integer，long，date，timestamp，localdatetime；②整数类型下：newVersion = oldVersion + 1;③仅支持updateById()和update(entity,Wrapper)方法
#####注意！！！不能复用Wrapper！
###4.性能分析
####使用p6spy组件可以实现在屏幕中输出SQL语句的执行时间和执行语句，首先在poom文件中导入p6spy的相关组件
        <dependency>
            <groupId>p6spy</groupId>
            <artifactId>p6spy</artifactId>
            <version>3.8.6</version>
        </dependency>
####将原有配置文件中的driver-class与url进行更改：
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:mysql://10.194.186.230:3306/data_center?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true
####添加配置文件spy.properties,默认内容如下：
    module.log=com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory
    # 自定义日志打印
    logMessageFormat=com.baomidou.mybatisplus.extension.p6spy.P6SpyLogger
    #日志输出到控制台
    appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
    # 使用日志系统记录 sql
    #appender=com.p6spy.engine.spy.appender.Slf4JLogger
    # 设置 p6spy driver 代理
    deregisterdrivers=true
    # 取消JDBC URL前缀
    useprefix=true
    # 配置记录 Log 例外,可去掉的结果集有error,info,batch,debug,statement,commit,rollback,result,resultset.
    excludecategories=info,debug,result,batch,resultset
    # 日期格式
    dateformat=yyyy-MM-dd HH:mm:ss
    # 实际驱动可多个
    #driverlist=org.h2.Driver
    # 是否开启慢SQL记录
    outagedetection=true
    # 慢SQL记录标准 2 秒
    outagedetectioninterval=2
####此时，我们再次回到测试类中，执行sql，就会得到相应的执行时长和执行语句
####此时我们所获得的执行时长和执行语句是打印在屏幕中的，我们想让这些信息放在日志文件中，所以我们需要修改spy.properties,将下面的语句注释掉
    appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
####并添加相关内容：
    logfile=log.log
####此时，就会在项目根目录下，生成log.log文件，里面记录了执行时长和执行语句
    
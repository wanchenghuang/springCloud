##mybatis-plus入门(2)
#####插入数据，我们可以先进行对象的初始化，再调用mapper中相关的方法进行插入，示例代码如下：
      @Test
      public void insert(){
         Student student = new Student();
         student.setId(5);
         student.setName("caozifu");
         student.setEmail("caozifu@126.com");
         student.setAge(18);
         studentMapper.insert(student);
      }
####问题1：如果将student表更改为mp_student表，上述插入的操作能否成功？
#####很明显，如果表名不同，mybatis-plus扫描到的实体类会与对应的表无法映射，从而插入失败，所以我们使用mp提供的注解：@TableName来解决问题，使用方式如下
    @Data
    @TableName("mp_student")
    public class Student {
        private Long id;
        private String name;
        private Integer age;
        private String email;
    }
#####可以看到，只要在注解后填入表名，就可以进行表与实体类之间的关联。如果不进行关联，mybatis-plus会依据默认规则进行关联。
####问题2：如果实体类中，id属性名进行了更改，改为userId，student表中的id也进行更改，改为studentId，那么能否插入成功？
#####很明显，如果不进行指定，则id无法与主键进行映射，所以我们使用mp提供的注解：@TableId来解决问题
    @Data
    @TableName("mp_student")
    public class Student {
        @TableId
        private Long id;
        private String name;
        private Integer age;
        private String email;
    }
####问题3：如果实体类中的某一个字段名进行了更改，看起来与数据库中相对应的字段毫无关联，此时进行插入能否成功？
#####很明显，这个问题也肯定无法进行插入。因为无法识别相应的属性值，所以插入必然失败。所以，我们使用mp提供的注解：@TableField进行指定
    @Data
    @TableName("mp_student")
    public class Student {
        @TableId
        private Long id;
        @TableField("name")
        private String name;
        private Integer age;
        private String email;
    }
####问题：如果实体中有一个字段，不对应数据库表中的任何字段，那该怎么办？
#####比如说，我现在给Student类中，加入一个字段，叫做remark。并且在插入表的时候，对remark也进行了赋值。此时肯定会报错。此时mp提供了以下三种解决方法
####解决方案①：实体类中额外的字段，额外加入关键词transient进行修饰，意思是该字段不参与序列化过程。
#####但是如果我们有序列化的需求，就无法使用上述方式了，所以有了第二种方式<br>
####解决方案②：将实体类中的额外的字段，加入static关键字进行修饰，并额外给该字段加入get/set方法
#####但是，静态变量只能有一份，无法满足多对象的需求。此时建议使用第三种方式<br>
####解决方案③：在实体类中的额外字段上，加入注解@TableField(exist=false)
#####这样，就完美解决了实体类存在额外字段的情况了<br>
####基本查询
#####①selectById：根据Id进行查询，代码如下：
     @Test
     public void selectById(){
        Student student = studentMapper.selectById(1);
        System.out.println(student);
     }
#####只要在方法中填入指定的id，即可进行查询
#####②selectBatchIds:同时指定多个id进行查询，代码示例如下：
    @Test
    public void selectBatchIds(){
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Student> students = studentMapper.selectBatchIds(list);
        students.forEach(System.out::println);
    }
#####③selectByMap:使用指定条件进行查询，代码示例如下：
    @Test
    public void selectMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","caozifu");
        map.put("age",18);
        List<Student> students = studentMapper.selectByMap(map);
        students.forEach(System.out::println);
    }
#####上述示例代码翻译为sql语句为：select * from student where name = caozifu AND age = 18
####以条件构造器为参数的查询方法，条件构造器有很多种，下面将使用需求一一介绍
#####需求①:搜索出名字包含“cao”并且年龄小于40的记录
#####翻译为sql语句，则为：select * from student where name like '%%雨%' AND age<40。代码如下
    @Test
    public void selectByWrap(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","cao").lt("age",40);
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####其中，创建QueryWrapper对象的方式有两种，第一种就是上述的方式，直接进行创建
    QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
#####第二种就是使用工具类Wrapper.<T>query()进行创建
#####需求②：搜索出名字包含“cao”并且年龄大于等于16小于等于20，并且email不为空
#####翻译为sql语句为：select * from student where name like '%cao%' AND age between 20 and 40 AND email is not null 代码如下所示：
    @Test
    public void selectByWrap2(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","cao").between("age",17,40).isNotNull("email");
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####名字为曹姓或者年龄大于等于25岁，按照年龄降序排列，年龄相同按照id升序排列
#####翻译为sql语句为：select * from student where name like 'cao%' or age >= 15 order by age desc,id asc 代码如下所示:<br>
    @Test
    public void selectByWrap3(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","cao").or().ge("age",25).orderByDesc("age").orderByAsc("id");
        studentMapper.selectList(queryWrapper);
    }
#####需求③：名字为曹姓，并且（年龄小于40或者邮箱不为空）
#####翻译为sql语句为：select * from student where name like 'cao%' And (age <40 or email is not null)
    @Test
    public void selectByWrap4(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Student> student = queryWrapper.likeRight("name", "cao").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        List<Student> students = studentMapper.selectList(student);
        students.forEach(System.out::println);
    }
#####这里使用的是lambada表达式，括号内的值全部都塞入and()中,我们再看一个例子
#####需求④：姓名为曹姓或者（年龄小于40并且年龄大于20 并且邮箱不为空）
#####翻译为sql语句如下：select * from student where name like 'cao%' or (age between 20 and 40 And email is not null) 代码如下
    @Test
    public void selectByWrapper5(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","cao").or(wq->wq.lt("age",40).gt("age",20).isNotNull("email"));
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####需求⑤：（年龄小于40或邮箱不为空）并且年龄为曹姓
#####翻译为sql语句为：select * from student where name like 'cao%' AND (age < 40 or email is not null) 代码如下
    @Test
    public void selectByWrapper(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(caozifu->caozifu.lt("age",40).or().isNotNull("email")).likeRight("name","cao");
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
    
#####需求⑥：年龄为30，18，21的信息
#####翻译为sql语句为：select * from student where age in (30,18,21);代码如下：
    @Test
    public void selectByWrapper7(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age",Arrays.asList(30,18,21));
        List<Student> students = studentMapper.selectList(queryWrapper);
        System.out.println(students);
    }
    
#####需求⑦：只显示满足需求的一条信息
#####以查询所有信息为例，sql语句为：select * from student limit 1
    @Test
    public void selectByWrapper8(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        List<Student> students = studentMapper.selectList(queryWrapper);
        System.out.println(students);
    }


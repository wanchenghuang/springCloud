##mybatis-plus入门(3)
####select查询语句中，字段不全出现的方式
#####需求①：只查询出id和name的相关信息
#####sql语句翻译为：select id,name from student 
     @Test
     public void selectByWrapperSuper(){
         QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
         queryWrapper.select("id","name");
         List<Student> students = studentMapper.selectList(queryWrapper);
         System.out.println(students);
     }
     
#####需求②：查询不包含age这一字段值的所有结果
#####代码如下：
     @Test
     public void selectByWrapperSuper2(){
         QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
         queryWrapper.select(Student.class,wq->!wq.getColumn().equals("age")&&!wq.getColumn().equals("email"));
         List<Student> students = studentMapper.selectList(queryWrapper);
         students.forEach(System.out::println);
     }
####条件构造器中condition的作用
#####当我们想要动态的去指定该条件构造器中的模糊查询的某个条件成立与否时，可以在like后添加boolean参数进行控制
#####这个例子后续会补充

####实体作为条件构造器构造方法的参数
#####我们可以向条件构造器中插入一个实体对象，通过给对象的属性赋值来确定查询条件
#####比如说我想查询name为caozifu，age为30的学生信息，sql应该写为 select * from student where name = caozifu AND age = 30,代码如下
    @Test
    public void selectByWrapperEntity(){
        Student student = new Student();
        student.setName("caozifu");
        student.setAge(30);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>(student);
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####上述的操作只允许等值，但如果说我既想要传递对象，又想实现一些非等值的功能。这个时候，我们需要对实体类进行一些处理
#####在非等值的字段上添加注解@Condition，如下所示：
    @Data
    @TableName("mp_student")
    public class Student {
        @TableId
        private Long id;
        @TableField("name")
        private String name;
        @TableField(condition = SqlCondition.LIKE)
        private Integer age;
        private String email;
    }
#####其中，SqlCondition这个类中存有一些指定的常量，比如说LIKE，NOT LIKE，LIKE RIGHT等等。可以指定字段需要被处理的类型
#####但是其中并没有大于、小于、等于这样的类型，这些需要我们自己自定义，比如说我们想让条件变成age<? ,举一个如下所示:
    @Data
    @TableName("mp_student")
    public class Student {
        @TableId
        private Long id;
        @TableField("name")
        private String name;
        @TableField(condition = "%s&lt;#{%s}")
        private Integer age;
        private String email;
    }
#####与selectByMap(Map map)方法类似，使用AllEq(Map map)也能达到相同的效果,比如说我想查询年龄为30，名字为caozifu的信息，代码如下所示：
    @Test
    public void selectByWrapperAllEq(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Map <String,Object> map = new HashMap();
        map.put("name","caozifu");
        map.put("age",30);
        queryWrapper.allEq(map);
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####如果说我们想让语句变为select * from student where name = 'caozifu' AND age = null;则代码可以改为：
    @Test
    public void selectByWrapperAllEq(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Map <String,Object> map = new HashMap();
        map.put("name","caozifu");
        map.put("age",null);
        queryWrapper.allEq(map);
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####但此时，如果我们想实现age对应的value如果是努力了，则不希望age = null出现在sql语句中，让sql语句只有对name的判断，即是否忽略null值的功能。可以将代码改为如下形式：
    @Test
    public void selectByWrapperAllEq(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Map <String,Object> map = new HashMap();
        map.put("name","caozifu");
        map.put("age",null);
        queryWrapper.allEq(map,false);
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####此时如果我们不通过null的形式，想直接屏蔽掉某个字段，在allEq中可以直接添加函数表达式，如下所示
    @Test
    public void selectByWrapperAllEq(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Map <String,Object> map = new HashMap();
        map.put("name","caozifu");
        map.put("age",null);
        queryWrapper.allEq((k,v)->!k.equals("name"),map);
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);
    }
#####如果此时，我们希望查询结果是以map的形式返回结果，代码如下所示：
    @Test
    public void selectByWrapperMap(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","cao").lt("age",40);
        List<Map<String, Object>> list = studentMapper.selectMaps(queryWrapper);
        list.forEach(System.out::println);
    }
###<br>
####lambda条件构造器
#####先要构造lambda条件构造器有三种方法，如下代码所示：
    @Test
    public void selectByLambda(){
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Student> lambda = new QueryWrapper<Student>().lambda();
        LambdaQueryWrapper<Student> objectLambdaQueryWrapper = Wrappers.<Student>lambdaQuery();
    }
#####上述三种方法都能获得lambda条件构造器对象,此时如果还想实现功能：select * from student where name like 'cao' AND age<40,代码如下
    @Test
    public void selectByLambda(){
        LambdaQueryWrapper<Student> lambdaQueryWrapper = Wrappers.<Student>lambdaQuery();
        lambdaQueryWrapper.like(Student::getName,"cao").lt(Student::getAge,40);
        List<Student> students = studentMapper.selectList(lambdaQueryWrapper);
        students.forEach(System.out::println);
    }
#####使用lambda表达式的好处是杜绝了列名的误写
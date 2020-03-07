##mybatis-plus入门(4)
####update更新操作
#####根据ID进行修改更新操作，代码如下所示：
    @Test
    public void updateById(){
        Student student = new Student();
        student.setId(3l);
        student.setAge(26);
        student.setEmail("caozifu2@126.com");
        int i = studentMapper.updateById(student);
    }
#####也可以使用updateWrapper进行更新操作，代码如下
    @Test
    public void updateByWrapper(){
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","caozifu").eq("age",30);
        Student student  = new Student();
        student.setName("测试曹");
        student.setEmail("2019@qq.com");
        studentMapper.update(student,updateWrapper);
    }
#####但是，我只想更新一两个参数，但每次都还要去new一个对象，就非常麻烦。所以我们此时可以使用set方法直接进行指定
    @Test
    public void updateByWrapper3(){
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","测试曹").eq("age",30).set("name","测试曹2").set("email","你猜");
        studentMapper.update(null,updateWrapper);
    }
#####更新方法还包含lambda表达式，这里不再赘述，和selectLambda差不多
####删除delete
#####根据id删除，代码如下
     @Test
     public void deleteById(){
         int i = studentMapper.deleteById(1181765484589199362l);
         System.out.println("影像记录数:"+i);
     }
#####删除指定的条件deleteByMap
    @Test
    public void deleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","caozifu");
        map.put("age",31);
        int i = studentMapper.deleteByMap(map);
        System.out.println("影像记录数:"+i);
    }
#####也可以同时指定多个ID进行删除，代码如下，就像Delete from student where id in 1，2，3   代码如下所示
    @Test
    public void test(){
        int i = studentMapper.deleteBatchIds(Arrays.asList(5, 6));
        System.out.println("影像记录数"+i);
    }
#####同样的，也可以使用条件构造器来进行删除操作，这里只演示lambda条件构造器，非lambda的条件构造器可以参考之前的章节
#####例如，我想实现功能：DELETE from student where age = 27 , 代码如下：
    @Test
    public void test2(){
        LambdaQueryWrapper<Student> lambdaQueryWrapper = Wrappers.<Student>lambdaQuery();
        lambdaQueryWrapper.eq(Student::getAge,21);
        int delete = studentMapper.delete(lambdaQueryWrapper);
        System.out.println("影像记录数:"+delete);
    }
###ActiveRecord模式
####AR模式的使用步骤
#####①使实体类继承Model<xxxx>,代码如下：
    @Data
    @TableName("mp_student")
    @EqualsAndHashCode(callSuper = false)
    public class Student extends Model<Student> {
        @TableId
        private Long id;
        @TableField("name")
        private String name;
        private Integer age;
        private String email;
    }
#####②编写与实体类相关的mapper：这里之前创建好了，就不再演示了
#####③创建实体类，并根据需求编写代码
#####光用文字解释会有一些模糊，直接上代码，比如说我们要实现功能：使用AR模式随意向表内添加一条记录。代码如下：
        @Test
        public void test(){
            Student student = new Student();
            student.setEmail("caozifu@126.com");
            student.setName("liuming");
            student.setAge(30);
            boolean insert = student.insert();
            System.out.println(insert?"插入成功":"插入失败");
        }
#####由上面的代码可以看到，只需要通过对对象进行初始化赋值，并调用对象内部的相关方法，就可以完成相应的功能了
#####比如，我还想实现一个功能：select * from student where id = 4 ,代码如下：
    @Test
    public void test2(){
        Student student  = new Student();
        Student studentSelect = student.selectById(4);
        System.out.println(studentSelect);
    }
######其实，我们还可以使用条件构造器来构造条件进入sql语句。比如说我想实现功能：update student set age=13,email='玩玩而已' where name = 'liuming'',可以使用如下代码：
    @Test
    public void test3(){
        Student student = new Student();
        LambdaQueryWrapper<Student> lambdaQueryWrapper = Wrappers.<Student>lambdaQuery();
        lambdaQueryWrapper.eq(Student::getName,"liuming");
        student.setAge(13);
        student.setEmail("玩玩而已");
        boolean update = student.update(lambdaQueryWrapper);
        System.out.println(update?"插入成功":"插入失败");
    }
#####删除、查询功能都是如此，具体的示例都差不多
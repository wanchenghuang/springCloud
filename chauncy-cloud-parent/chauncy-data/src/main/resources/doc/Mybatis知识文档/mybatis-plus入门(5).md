##mybatis-plus入门(4)
###通用Service
####在实际开发中，我们需要自己去写Service层，mp提供了更加优雅的方式去编写Service层代码
#####步骤①：创建接口并继承IService<XXXX>
    public interface StudentService extends IService<Student> {
   
    }
#####步骤②：创建这个接口的实现类，并继承ServiceImpl<XxxMapper,Xxx>，并实现接口StudentService
    @Service
    public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService{
    
    }
#####步骤③：调用接口的方法即可
#####此时，需求来了：使用service的方法，实现select * from where id = 1; 代码如下：
    @Test
    public  void test2(){
        Student byId = studentService.getById(1);
        System.out.println(byId);
    }
#####此时，又来了一个需求：使用service的方法，实现 insert into student values('caozifu',12,'测试')；代码如下：
    @Test
    public void test3(){
        Student student = new Student();
        student.setName("caozifu");
        student.setEmail("测试而已");
        student.setAge(12);
        boolean save = studentService.save(student);
        System.out.println(save?"插入成功":"插入失败");
    }
#####我们也可以批量向表中插入数据：
    @Test
    public void test4(){
        Student student = new Student();
        student.setAge(14);
        student.setName("caozifu2");
        student.setEmail("测试罢了");
        Student student1 = new Student();
        student1.setEmail("同样是测试");
        student1.setName("caozifu3");
        student1.setAge(14);
        boolean b = studentService.saveOrUpdateBatch(Arrays.asList(student, student1));
        System.out.println(b?"插入成功":"插入失败");
    }
#####如上述代码所示，一次性完成了两个对象的插入
#####如果我们此时想使用lambda条件构造器，完成功能:select  * from student where name = "caozifu"; 代码如下：
    @Test
    public void test5(){
       List<Student> caozifu = studentService.lambdaQuery().eq(Student::getName, "caozifu").list();
       caozifu.forEach(System.out::println);
    }
#####可以看到，我们在使用service层中的lambda方法时，最后直接使用.list()，就可以得到最终结果。这被称为链式调用
#####再举一个update的例子，比如说我想实现 update student set email='测试更新是否成功' where name = 'caozifu1';代码如下：
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
#####可以看到，update的链式调用，只要最后使用.update()就可以完成更新操作



    
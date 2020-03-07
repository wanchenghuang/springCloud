##mybatis-plus进阶(2)
###1.多租户实现
####首先实现多租户，我们需要引入分页插件配置，在MybatisConfiguration中注入以下功能：
     @Bean
        public PaginationInterceptor paginationInterceptor() {
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
            ArrayList<ISqlParser> iSqlParsers = new ArrayList<>();
            TenantSqlParser tenantSqlParser = new TenantSqlParser();
            tenantSqlParser.setTenantHandler(new TenantHandler() {
                @Override
                public Expression getTenantId(boolean where) {
                    return new LongValue(1);
                }
    
                @Override
                public String getTenantIdColumn() {
                    return "id";
                }
    
                @Override
                public boolean doTableFilter(String tableName) {
                    if ("role".equals(tableName)){
                        return true;
                    }
                    return false;
                }
            });
            iSqlParsers.add(tenantSqlParser);
            paginationInterceptor.setSqlParserList(iSqlParsers);
            return paginationInterceptor;
        }
####其中，getTenantId(boolean where)中可以指定相应的指定值，在getTenantIdColumn()中可以指定字段，doTableFilter(String tableName)可以过滤表名，即指定表实现多租户
####此时，按照上述代码，再次运行查询全部的SQL，得到的SQL语句如下
    SELECT id, deleted, name, time, version, email, age FROM mp_student WHERE deleted = 0 AND mp_student.id = 1
####如果此时，我想指定某一个方法不进行租户隔离，此时在上述方法后追加代码：
    paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
        @Overrid
        public boolean doFilter(MetaObject metaObject) {
                MappedStatement mappedStatement = SqlParserHelper.getMappedStatement(metaObject);
                String methodName = "com.bmsoft.dc.dodp.mapper.StudentMapper.selectById";
                if(methodName.equals(mappedStatement.getId())){
                    return true;
                }
                return false;
            }
        });
#####上述代码的意思就是，指定selectById这个方法不会进行租户隔离，此时再执行selectById方法，查询id=2的用户信息，SQL变为如下：
     Consume Time：10 ms 2019-10-18 11:09:31
     Execute SQL：SELECT id,deleted,name,time,version,email,age FROM mp_student WHERE id=2 AND deleted=0
####由上图所示，租户隔离失效
###2.自定义SQL注入 
####在使用自定义SQL注入可以实现自定义sql，实现步骤如下所示：
####首先，创建method包，并创建DeleteAllMethod类，并使其集成AbstractMethod类，代码如下
    public class DeleteAllMethod extends AbstractMethod {
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            String sql = "delete from " + tableInfo.getTableName();
            //mapper接口方法名
            String method = "deleteAll";
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return addDeleteMappedStatement(mapperClass,method,sqlSource);
        }
    }
####此时我们要实现的SQL语句是删除数据库中所有的记录，然后，我们继续创建injector包，并创建MySqlInJector类，并在类上标记@Component，代码如下：
    @Component
    public class MySqlInjector extends DefaultSqlInjector {
        @Override
            public List<AbstractMethod> getMethodList(Class<?> mapperClass){
            List<AbstractMethod> methodList = super.getMethodList(mapperClass);
            methodList.add(new DeleteAllMethod());
            return methodList;
        }
    }
####然后，在对应的mapper中，创建与接口方法名相同的方法，在test中调用即可

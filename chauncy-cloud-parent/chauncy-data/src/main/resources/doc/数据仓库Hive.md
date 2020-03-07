# 数据仓库Hive

## 产生背景

MapReduce编程门槛高，无法及时应对需求的变更

传统RDBMS（关系型数据库）人员的需求，HDFS上的文件并没有schema的概念

可通过Hive进行大数据的处理

## Hive概述

有Facebook开源，用于解决**海量结构化日志**的**数据统计**问题

构建在Hadoop之上的数据仓库（计算能力，存储能力，可应付数据的暴增）

Hive提供的SQL查询语言：HQL

底层支撑多种不同的执行引擎

 Hive底层执行引擎支持：MR/Tez/Spark（用户不感知）

###为什么要使用Hive

* 简单，容易上手

* 为超大数据集设计的计算/扩展能力
* 同一的元数据管理：Hive数据是存放在HDFS上，元数据信息（记录数据(HDFS数据)的数据）是存放在MySQL中
  * SQL  on Hadoop:Hive,Spark,SQL,impala...

## Hive体系架构

 ### Hive 在Hadoop生态中的位置

![Hive 在Hadoop生态中的位置](/img/06_first.PNG)

体系架构图：

![](/img/06_second.PNG)

client：shell、**thrift/jdbc(server/jdbc)**、webUI(HUE/Zeppelin)

metastore:==>MySQL

​              database:name/location/owner...

​              table:name、location、owner、column、name/type...

Hive：写SQL翻译成MapReduce，放入到Hadoop



## Hive部署架构

* 测试环境

  客户端把SQL提交Hive引擎，*元数据信息可存放在Derby中（只能进行单客户端的操作，就是单session）即使是测试环境也不推荐使用*，因此只能选择MySQL

* 生产环境：为了防止MySQL错误导致无法进行获取元数据信息，因此得有主备MySQL，主备MySQL会进行切换。（解决MySQL单点问题）

  Hadoop集群：DN、NM、NN、RM

  Hive提交到RM上，Hadpoop集群有很多节点，Hive是一个客户端，并不涉及集群的概念

  ![](/img/06_three.PNG)

## Hive与RDBMS的区别

* 支持的：都支持分布式
* 区别的：Hive不适合立马进行查询



## Hive部署及快速入门

包：hive-1.*.0cdh.5 *. *tar-gz

*bin目录：脚本

*conf目录：配置

1 下载

2解压的 ~/app

3添加Hive-HOME到系统环境变量

4修改配置

5拷贝MySQL驱动包

6预先下载MySQL数据库

## Hive DDL详解

*可以创建一张表，把数据加入到表中，对数据的各种维度的分析*

create/delete/alter...

Hive数据抽象/结构

* database   HDFS一个目录

* table   HDFS一个目录
  * data文件
  *  partition分区表 HDFS一个目录
  * bucket分桶   HDFS一个文件

---

创建数据库：

```mysql
      CREATE  (DATABASE|SCHEMA)  [IF NOT EXISTS] database_name
       [COMMENT database_comment]
       [LOCATIO hdfs_path]
       [WITH DBPROPERTIES  (property_name=property_value,...)];
```

----

创建表：

```
CREATE [EXTERNAL] TABLE [IF NOT EXISTS] table_name
  [(col_name data_type[COMMENT col_comment], ... [constraint_specification])]
  [COMMENT table_comment]
  [PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]
  [CLUSTERED BY (col_name, col_name, ...) [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
   [ROW FORMAT row_format] 	
   [STORED AS file_format]
  [LOCATION hdfs_path]
  [TBLPROPERTIES (property_name=property_value, ...)] 
  [AS select_statement];
CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name
  LIKE existing_table_or_view_name
  [LOCATION hdfs_path];
```

各项参数说明：

* 1**CREATE TABLE 创建一个指定名字的表。如果相同名字的表已经存在，则抛出异常；用户可以用 IF NOT EXISTS 选项来忽略这个异常，一般也可以不加这个IF NOT EXISTS语句，最多抛出错误。**
* 2 [constraint_specification]可选项： [ PRIMARY KEY|UNIQUE|NOT NULL|DEFAULT [default_value]
* 3 [COMMENT table_comment] 可选项：COMMENT 后面跟的字符串是给表字段或者表内容添加注释说明的
* 4  [PARTITIONED BY ] 可选项：**PARTITIONED BY其实是给表做分区，决定了表是否是分区表。（Hive中所谓分区表就是将表里新增加一个字段，就是分区的名字，这样你在操作表中的数据时，可以按分区字段进行过滤）**
* 5 [CLUSTERED BY ]可选项：**CLUSTERED BY对于每一个表（table）或者分区， Hive可以进一步组织成桶，也就是说桶是更为细粒度的数据范围划分。Hive也针对某一列进行桶的组织。Hive采用对列值哈希，然后除以桶的个数求余的方式决定该条记录存放在哪个桶当中**
* 6  [ROW FORMAT]可选项：存储表格式
* 7   [STORED AS] 可选项：hive存储的三种文件格式
* 8  [LOCATION AS] 可选项：LOCATION 其实是定义hive表的数据在hdfs上的存储路径，一般管理表（内部表不不要自定义），但是如果定义的是外部表，则需要直接指定一个路径。实际上不指定也没事，会使用默认路径

部分详解：

* 使用PARTITIONED BY子句创建分区表。一个表可以具有一个或多个分区列，并为分区列中的每个不同值组合创建一个单独的数据目录。此外，可以使用CLUSTERED BY列对表或分区进行存储，并且可以通过SORT BY列在该存储区中对数据进行排序。这样可以提高某些查询的性能。

  eg:

  ```mysql
  CREATE TABLE page_view(viewTime INT, userid BIGINT,
       page_url STRING, referrer_url STRING,
       ip STRING COMMENT 'IP Address of the User')
   COMMENT 'This is the page view table'
   PARTITIONED BY(dt STRING, country STRING)
   ROW FORMAT DELIMITED
     FIELDS TERMINATED BY '\001'
  STORED AS SEQUENCEFILE;
  ```

​      上面的语句创建带有viewTime，userid，page_url，referrer_url和ip列（包括注释）的page_view表。该表也被分区，数据存储在序列文件中,文件中的数据格式由ctrl-A分隔字段，由换行分隔行。

---

重命名表：

```mysql
ALTER TABLE table_name RENAME TO new_table_name;
```

更改表属性：

```mysql
`ALTER TABLE table_name SET TBLPROPERTIES table_properties;` `table_properties:``  ``: (property_name = property_value, property_name = property_value, ... )`
```

` table_properties`  : (property_name = property_value, property_name = property_value, ... )

`TBLPROPERTIES`:允许您使用自己的**元数据键/值**对标记表定义;还存在一些预定义的表属性，由Hive自动添加和管理的last_modified_user和last_modified_time。

修改表注释：要更改表的注释，您必须更改的`comment`属性`TBLPROPERTIES`：

```mysql
`ALTER TABLE table_name SET TBLPROPERTIES (``'comment'` `= new_comment);`
```

重命名现有表的列：

```mysql
`ALTER TABLE old_table_name REPLACE COLUMNS (col1 TYPE, ...);`
```

将列添加到现有表：

```mysql
ALTER TABLE tab1 ADD COLUMNS (c1 INT COMMENT 'a new int column', c2 STRING DEFAULT 'def val');
```

删除表：

```mysql
`DROP TABLE pv_users;`
```

删除表分区(更改表以删除分区)：

```mysql
`ALTER TABLE pv_users DROP PARTITION (ds=``'2008-08-08'``)`
```

加载数据-----略

## Hive DML详解

Hive查询操作记录在[Select中](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+Select)，而插入操作记录在[将数据从查询插入Hive表](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DML#LanguageManualDML-InsertingdataintoHiveTablesfromqueries)和[从查询](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DML#LanguageManualDML-InsertingdataintoHiveTablesfromqueries)[将数据写入文件系统中](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DML#LanguageManualDML-Writingdataintothefilesystemfromqueries)。

#### 简单查询

```mysql
`INSERT OVERWRITE TABLE user_active``SELECT user.*``FROM user``WHERE user.active = ``1``;`
```

请注意，与SQL不同，我们总是将结果插入表中。也可以将其转储到本地文件中。

也可以在[Beeline](https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients#HiveServer2Clients-Beeline–NewCommandLineShell)  或Hive  [CLI中](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+Cli)运行以下查询  ：

```mysql
SELECT user.*
FROM user
WHERE user.active = ``1``;
```

#### 基于分区的查询

```mysql
`INSERT OVERWRITE TABLE xyz_com_page_views``SELECT page_views.*``FROM page_views``WHERE page_views.date >= ``'2008-03-01'` `AND page_views.date <= ``'2008-03-31'` `AND``      ``page_views.referrer_url like ``'%xyz.com'``;`
```

系统根据分区列上的where子句条件自动确定要在查询中使用的分区。例如，为了获取域xyz.com引用的03/2008月份的所有page_views

*请注意，此处使用page_views.date，因为该表（上面）是使用PARTITIONED BY（date DATETIME，country STRING）定义的；如果您给分区命名不同，请不要期望.date发挥您的想法！*

#### 连接查询

```mysql
INSERT OVERWRITE TABLE pv_users
SELECT pv.*, u.gender, u.age
FROM user u JOIN page_view pv ON (pv.userid = u.id)
WHERE pv.date = '2008-03-03';
```

* 可以使用LEFT OUTER，RIGHT OUTER或FULL OUTER关键字限定联接，以指示外部联接的类型（保留的左侧，保留的右侧或两侧保留）

* 检查另一个表中是否存在键，用户可以使用LEFT SEMI JOIN
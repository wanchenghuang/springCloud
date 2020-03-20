package com.chauncy.cloud.common.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-05 23:04
 */
public class AutoGeneratorUtils {

    private AutoGeneratorUtils() {
    }

    public static void main(String args[]){

        autoGenerator("root","%dt6$#@%s","gateway","sc_gateway",
                "gateway_route","39.108.136.61",8386L);
    }

    //参数 username/password/packageName/dataBaseName/tableName/url
    //常量 user.dir/targetModel/ /src/main/java /entityName /xmlName /controllerName
    // jdbc:mysql:// ?useUnicode=true&useSSL=false&characterEncoding=utf8
    // driverName/groupId/mapperTarget/
    //枚举 AUTO、INPUT、ID_WORKER、UUID

    public static void autoGenerator(String userName, String password, String modelName,
                                     String database, String tableName, String host,Long port) {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/chauncy-data";
        gc.setOutputDir(path + "/src/main/java");
        gc.setAuthor("admin");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setEntityName("%sPo");
        gc.setXmlName("%sMapper");
        gc.setControllerName("%sController");
        gc.setOpen(false); // 是否打开输出目录
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setFileOverride(true);// 开启文件覆盖
        // ID 策略 AUTO->("数据库ID自增") INPUT->(用户输入ID") ID_WORKER->("全局唯一ID") UUID->("全局唯一ID")
        gc.setIdType(IdType.ID_WORKER);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://"+host+":"+port+"/" + database + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(userName);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("data");
        pc.setParent("com.chauncy.cloud");
        pc.setEntity("domain.po." + modelName);
        pc.setMapper("mapper." + modelName);
        pc.setService("temp.client." + modelName);
        pc.setServiceImpl("temp.client." + modelName + ".impl");
        pc.setController("temp.controller." + modelName);

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return path + "/src/main/resources/mapper/" + modelName
                        + "/" + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        templateConfig.setServiceImpl("templates/mybatis/serviceImpl.java");
        templateConfig.setController("templates/mybatis/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.chauncy.cloud.data.domain.po.BasePo");
        strategy.setSuperServiceClass("com.chauncy.cloud.core.config.base.Service");
        strategy.setSuperServiceImplClass("com.chauncy.cloud.core.config.base.AbstractService");
        strategy.setSuperMapperClass("com.chauncy.cloud.data.mapper.base.IBaseMapper");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass("com.chauncy.cloud.common.base.BaseController");
        strategy.setInclude(tableName/*scanner("表名，多个英文逗号分割").split(",")*/);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntitySerialVersionUID(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setLogicDeleteFieldName("del_flag");//对字段del_flag自动添加注解@TableLogic
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}

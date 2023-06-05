package com.xzj.mysql.plus.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.xzj.mysql.plus.aspect.DataSourceAspect;
import com.xzj.mysql.plus.constants.CommonConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Category com.xzj.mysql.config
 * @Description 【数据源信息配置类，读取数据源配置信息并注册成bean。】
 * @Author xiaj07
 * @Since 2022/11/21 8:47
 **/
@Configuration
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class})
@MapperScan("com.*.**.mapper")
public class DruidConfig {
    @Resource
    private Environment environment;


    @Bean(name = CommonConstant.MASTER)
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource()
    {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = CommonConstant.SLAVE)
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnExpression(value = "environment.containsProperty('spring.datasource.druid.slave.url')")
    public DataSource slaveDataSource()
    {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource()
    {
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(CommonConstant.MASTER,masterDataSource());
        if(environment.containsProperty("spring.datasource.druid.slave.url")){
            dataSourceMap.put(CommonConstant.SLAVE,slaveDataSource());
        }
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //将数据源信息备份在defineTargetDataSources中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    /**
     * 配置多数据源后IOC中存在多个数据源了，事务管理器需要重新配置，不然器不知道选择哪个数据源
     * 事务管理器此时管理的数据源将是动态数据源dynamicDataSource
     * 配置@Transactional注解
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    /**
     * 配置插件
     *
     * @return bean
     */
    @Bean(name = "plugins")
    public Interceptor[] plugins() {
        //org.apache.ibatis.plugin.Interceptor
       /* Interceptor interceptor = new PageInterceptor();
        //java.util.Properties
        Properties properties = new Properties();
        //是否将参数offset作为pageNum
        properties.setProperty("offsetAsPageNum", "true");
        // 数据库类型
        properties.setProperty("helperDialect", "mysql");
        // 是否返回行数，相当于MySQL的count(*)
        properties.setProperty("rowBoundsWithCount", "true");
        // 是否分页合理化：即翻页小于0时，显示第一页数据，翻页数较大查不到数据时，显示最后一页的数据，默认即为false，本处显示写出，以供参考。
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);*/
        Interceptor[] plugins = new Interceptor[]{new PageInterceptor()};
        // plugins[0] = interceptor;
        return plugins;
    }

    /**
     * sql工厂(通过名称来注入)
     * @return bean
     * @throws Exception
     *             异常
     */
    @Bean(name = "sessionFactory")
    public SqlSessionFactory sqlSessionFactory()
            throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        // 配置xml文件的路径
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**.xml"));
        //装载/设置插件，本步骤必须在sqlSessionFactoryBean.getObject()之前执行
        sqlSessionFactoryBean.setPlugins();
        //返回工厂
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactoryBean.getObject().getConfiguration();
        //通过set方法配置相关的属性，还可以自己继续补加
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceAspect dataSourceAspect(){
        return new DataSourceAspect();
    }

}

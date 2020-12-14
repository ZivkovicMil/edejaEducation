package com.edeja.edejaEducation.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "XdpAdminEntityManagerFactory",
    transactionManagerRef = "XdpAdminTransactionManager",
    basePackages = {"com.edeja.edejaEducation.repositories.adminDao"}
)
public class AdminConfig {
  
  @Autowired
  private Properties jpaProperties;

  @Bean
  @ConfigurationProperties(prefix = "admin.datasource")
  public DataSourceProperties dataSourcePropertiesXdpAdmin() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "xdpAdminDataSource")
  public DataSource dataSource() {
    DataSourceProperties dataSourceProperties = dataSourcePropertiesXdpAdmin();
    HikariDataSource dataSource = (HikariDataSource)
        DataSourceBuilder
            .create(dataSourceProperties.getClassLoader())
            .driverClassName(dataSourceProperties.getDriverClassName())
            .url(dataSourceProperties.getUrl())
            .username(dataSourceProperties.getUsername())
            .password(dataSourceProperties.getPassword())
            .type(HikariDataSource.class)
            .build();
    return dataSource;
  }

  @Primary
  @Bean(name = "XdpAdminEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory()
      throws NamingException {
    LocalContainerEntityManagerFactoryBean factoryBean = new
        LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(dataSource());
    factoryBean.setPackagesToScan("com.edeja.edejaEducation.entity.adminEntity");
    factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
    factoryBean.setJpaProperties(jpaProperties);
    return factoryBean;
  }


  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new
        HibernateJpaVendorAdapter();
    return hibernateJpaVendorAdapter;
  }


  @Bean(name = "XdpAdminTransactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("XdpAdminEntityManagerFactory") EntityManagerFactory emf) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(emf);
    return txManager;
  }
  
}

package seung.springbatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MetaDBConfig {

    //DataSource가 두개여서 충돌나서 primary 처리
    @Primary
    @Bean
    //application.properties 값 가져오기
    @ConfigurationProperties(prefix = "spring.datasource-meta")
    public DataSource metaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager metaTransactionManager(){
        return new DataSourceTransactionManager(metaDataSource());
    }
}

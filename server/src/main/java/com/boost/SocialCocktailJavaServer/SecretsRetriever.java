package com.boost.SocialCocktailJavaServer;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * If using DEV mode, need to set ENV=DEV and DB_PASSWORD=_password_ environment variables
 * Otherwise (PROD), set REGION and SECRETS_NAME (expecting arn) to retrieve the password
 * from secretsManager
 */
@Configuration
public class SecretsRetriever {
    @Value("${spring.datasource.username}")
    private String configUsername;

    @Value("${spring.datasource.url}")
    private String configUrl;

    @Bean(name = "dsMaster")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource masterDataSource() {
        String environment = System.getenv("ENV");

        DataSourceBuilder<?> builder = DataSourceBuilder.create().url(configUrl).username(configUsername);

        // If the environment is dev then we just use the password
        // set through environment variables
        if (environment != null && environment.equals("DEV")) {
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbPassword == null) {
                throw new IllegalArgumentException("Using DEV mode but database password is not set as an env variable");
            }

            return builder.password(dbPassword).build();
        }


        String password = SecretsRetriever.getSecret();
        return builder.password(password).build();
    }

    @Bean(name = "jdbcMaster")
    @Autowired
    public JdbcTemplate masterJdbcTemplate(@Qualifier("dsMaster") DataSource dsMaster) {
        return new JdbcTemplate(dsMaster);
    }

    private static String getSecret() {
        String secretName = System.getenv("SECRETS_NAME");

        String region = System.getenv("REGION");
        String endpoint = String.format("secretsmanager.%s.amazonaws.com", region);

        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName).withVersionStage("AWSCURRENT");
        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);

        if(getSecretValueResult == null || getSecretValueResult.getSecretString() == null) {
            throw new ResourceNotFoundException("Couldn't retrieve database password secret");
        }

        return getSecretValueResult.getSecretString();
    }
}

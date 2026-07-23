package com.rumpus.common.Config.Server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Server.Port.IPort;
import com.rumpus.common.Server.Port.Port;

@Configuration
@EnableConfigurationProperties(ServerProperties.class)
public class ServerConfig {

    @Bean
    public IPort applicationPort(ServerProperties props) {
        return Port.create(String.valueOf(props.getPort()));
    }
}

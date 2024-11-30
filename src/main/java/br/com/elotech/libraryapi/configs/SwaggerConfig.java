package br.com.elotech.libraryapi.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuração para a documentação utilizando o SpringDoc OpenAPI 3.
 *
 * <p>
 * Esta classe define as configurações necessárias para gerar e personalizar
 * a documentação da API no formato OpenAPI 3, acessando pelo Swagger UI.
 * </p>
 *
 * <ul>
 *   <li>Define informações gerais da API, como título e versão.</li>
 *   <li>Agrupa os endpoints da API por pacotes.</li>
 * </ul>
 *
 * <p>URL padrão da documentação foi personalizada para: <code>/docs</code></p>
 *
 * @author João Ayezzer
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library API")
                        .version("v1.0.0")
                );
    }
    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan("br.com.elotech.libraryapi.controllers")
                .build();
    }

}

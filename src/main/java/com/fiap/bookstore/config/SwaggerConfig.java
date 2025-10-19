package com.fiap.bookstore.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookstoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("📚 Bookstore API")
                        .description("API REST para **gerenciamento de Autores e Livros**.\n\n" +
                                "Permite realizar operações completas de CRUD (criar, listar, atualizar e remover) " +
                                "para autores e seus respectivos livros.")
                        .version("1.0.0")
                        .license(new License()
                                .name("Licença MIT")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação completa do projeto (GitHub)")
                        .url("https://github.com/seu-usuario/bookstore-api"));
    }
}

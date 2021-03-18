package br.com.felipe.pessoal.sistema.ordem_servico.config.swagger;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Perfil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.Collections.singletonList;

@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket ordemServicoApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.felipe.pessoal.sistema.ordem_servico"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Cliente.class, Perfil.class)
                .globalRequestParameters(singletonList(new springfox.documentation.builders.RequestParameterBuilder()
                        .name("Authorization")
                        .description("Header para token JWT")
                        .in(ParameterType.HEADER)
                        .required(false)
                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                        .build()));
    }

}

package br.com.elotech.libraryapi.configs;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * Configuração personalizada para serialização de datas e horários no formato JSON.
 *
 * <p>
 * Esta classe define formato padrão para datas e horários no sistema utilizando Jackson.
 * </p>
 *
 * <ul>
 *   <li>Formato de data: <strong>dd/MM/yyyy</strong></li>
 *   <li>Formato de data e hora: <strong>dd/MM/yyyy HH:mm:ss</strong></li>
 * </ul>
 *
 * <p>
 * A configuração é aplicada globalmente em toda a aplicação por meio da anotação {@link Configuration},
 * que indica que esta classe fornece configurações para o contexto do Spring.
 * </p>
 *
 * @author João Ayezzer
 */
@Configuration
public class JsonFormatConfig {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }

}

package no.lundesgaard.sudokufeud.api;

import java.io.File;

import no.lundesgaard.sudokufeud.SudokuFeudCoreConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaMapper;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
@Import(SudokuFeudCoreConfiguration.class)
@ComponentScan
@EnableAutoConfiguration
public class SudokuFeudApiConfiguration {
	public static final String ROOT_PATH = "api";
	private static final String LOGBACK_ACCESS_CONFIG_XML = "config/logback-access.xml";

	public static void main(String... args) {
		SpringApplication springApplication = new SpringApplication(SudokuFeudApiConfiguration.class);
		springApplication.run(args);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		File file = new File(LOGBACK_ACCESS_CONFIG_XML);
		if (file.exists()) {
			String fileName = file.getAbsolutePath();

			return factory -> {
				if (factory instanceof TomcatEmbeddedServletContainerFactory) {
					TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
					LogbackValve logbackValve = new LogbackValve();
					logbackValve.setFilename(fileName);
					containerFactory.addContextValves(logbackValve);
				}
			};
		}

		return factory -> {
		};
	}

	@Bean
	public JodaMapper jodaMapper() {
		JodaMapper jodaMapper = new JodaMapper();
		jodaMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return jodaMapper;
	}
}

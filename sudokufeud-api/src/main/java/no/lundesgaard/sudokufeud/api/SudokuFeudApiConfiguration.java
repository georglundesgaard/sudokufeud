package no.lundesgaard.sudokufeud.api;

import no.lundesgaard.sudokufeud.SudokuFeudCoreConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SudokuFeudCoreConfiguration.class)
@ComponentScan
@EnableAutoConfiguration
public class SudokuFeudApiConfiguration {
    public static final String ROOT_PATH = "api";

    public static void main(String... args) {
        SpringApplication springApplication = new SpringApplication(SudokuFeudApiConfiguration.class);
        springApplication.run(args);
    }
}

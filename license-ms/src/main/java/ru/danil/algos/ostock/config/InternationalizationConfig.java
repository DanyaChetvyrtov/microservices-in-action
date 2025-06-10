package ru.danil.algos.ostock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class InternationalizationConfig {

    @Bean
    public LocaleResolver localeResolver() { // Устанавливает US как локаль по умолчанию
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");

        // Не генерирует ошибку, если сообщение не найдено,
        // а вместо этого возвращает код сообщения
        messageSource.setUseCodeAsDefaultMessage(true);

        // Задаёт базовое имя файлов с переводами
        // сообщений на разные языки
        messageSource.setBasename("messages");
        return messageSource;
    }

}

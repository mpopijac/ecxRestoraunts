package ecx.mpopijac.restaurants;

import java.util.Locale;
import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
@EnableAsync
public class EcxRestaurantsApplication extends AsyncConfigurerSupport {

	public static void main(String[] args) {
		SpringApplication.run(EcxRestaurantsApplication.class, args);
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("MailService-");
		executor.initialize();
		return executor;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver r = new CookieLocaleResolver();
		r.setDefaultLocale(Locale.ENGLISH);
		r.setCookieName("localeInfo");
		r.setCookieMaxAge(60*60);
	    return r;
	}

	@Bean
	public WebMvcConfigurerAdapter configurer(){
		return new WebMvcConfigurerAdapter(){
			@Override
            public void addInterceptors (InterceptorRegistry registry) {
                LocaleChangeInterceptor l = new LocaleChangeInterceptor();
                l.setParamName("localeCode");
                registry.addInterceptor(l);
            }
		};
	}
}

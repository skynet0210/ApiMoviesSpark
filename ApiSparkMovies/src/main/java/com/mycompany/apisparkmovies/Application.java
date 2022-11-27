package com.mycompany.apisparkmovies;

/**
 *
 * @author mauro
 */

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mycompany.apisparkmovies.config.DatabaseConfig;
import com.mycompany.apispark.service.MoviesService;

@Configuration
@ComponentScan({"com.mycompany.apisparkmovies"})

public class Application {
    public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class, DatabaseConfig.class);
    	new MoviesApplication(ctx.getBean(MoviesService.class));
        ctx.registerShutdownHook();
	}
    
}

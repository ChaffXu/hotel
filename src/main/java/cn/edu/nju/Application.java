package cn.edu.nju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Qiang
 * @since 18/02/2017
 */
//@EnableScheduling
@SpringBootApplication
//@EnableJpaRepositories(repositoryBaseClass = CustomPayRecordRepositoryImpl.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
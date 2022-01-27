package omg.excelmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("omg.excelmanager.mapper")
public class ExcelManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelManagerApplication.class, args);
    }

}

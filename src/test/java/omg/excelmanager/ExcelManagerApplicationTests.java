package omg.excelmanager;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class ExcelManagerApplicationTests {

    @Test
    void contextLoads() {
        String path = System.getProperty("user.dir") + "\\src\\main";
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/excel_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=GMT%2B8", "root", "aaa1024")
                .globalConfig(builder -> {
                    builder.author("oneadm") // 设置作者

                            .fileOverride() // 覆盖已生成文件
                            .outputDir(path+"\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("omg.excelmanager") // 设置父包名

                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, path+"/resources/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("company_excel") // 设置需要生成的表名
                            .addInclude("user");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}

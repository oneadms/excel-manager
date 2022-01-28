package omg.excelmanager.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.model.entity.CompanyExcel;
import org.apache.poi.util.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel工具类
 */
@Slf4j
public class ExcelUtils {

    /**
     * 导出Excel(07版.xlsx)到指定路径下
     *
     * @param path      路径
     * @param excelName Excel名称
     * @param sheetName sheet页名称
     * @param clazz     Excel要转换的类型
     * @param data      要导出的数据
     */
    public static void export2File(String path, String excelName, String sheetName, Class clazz, List data) {
        String fileName = path.concat(excelName).concat(ExcelTypeEnum.XLSX.getValue());
        //调用自适应列宽方法（util包里面的Custemhandler）
        ExcelWriter excelWriter = EasyExcel.write(fileName).registerWriteHandler(new Custemhandler()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName).head(CompanyExcel.class).build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
        //不使用自动适应列宽则注释上面四行，取消下面一行的注释，然后在CompanyExcel实体类里面使用注解自定义列宽
        //EasyExcel.write(fileName, clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 导出Excel(07版.xlsx)到web
     *
     * @param response  响应
     * @param excelName Excel名称
     * @param sheetName sheet页名称
     * @param clazz     Excel要转换的类型
     * @param data      要导出的数据
     * @throws Exception
     */
    public static void export2Web(HttpServletResponse response, String excelName, String sheetName, Class clazz, List data) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        excelName = URLEncoder.encode(excelName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        //调用自适应列宽方法（util包里面的Custemhandler）
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).registerWriteHandler(new Custemhandler()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName).head(CompanyExcel.class).build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
        //不使用自动适应列宽则注释上面四行，取消下面一行的注释，然后在CompanyExcel实体类里面使用注解自定义列宽
        //EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 将指定位置指定名称的Excel导出到web
     * @param response  响应
     * @param path      文件路径
     * @param excelName 文件名称
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String export2Web4File(HttpServletResponse response, String path, String excelName) throws UnsupportedEncodingException {
        File file = new File(path.concat(excelName).concat(ExcelTypeEnum.XLSX.getValue()));
        if (!file.exists()) {
            return "文件不存在！";
        }

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        excelName = URLEncoder.encode(excelName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        try (
                FileInputStream in = new FileInputStream(file);
                ServletOutputStream out = response.getOutputStream();
        ) {
            IOUtils.copy(in, out);
            return "导出成功！";
        } catch (Exception e) {
            log.error("导出文件异常：", e);
        }
        return "导出失败！";
    }

}

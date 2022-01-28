package omg.excelmanager.controller;


import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.common.exception.ApiAsserts;
import omg.excelmanager.jwt.jwtUtil;
import omg.excelmanager.listener.CompanyExcelDataListener;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.User;
import omg.excelmanager.service.ICompanyExcelService;
import omg.excelmanager.service.ICompanyExcelService;
import omg.excelmanager.service.IUserService;
import omg.excelmanager.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
@Slf4j
@Controller
@RequestMapping("/company_excel")
public class CompanyExcelController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICompanyExcelService iCompanyExcelService;
    private   String path;
    public CompanyExcelController() {
        super();
        path = System.getProperty("CompanyExcel.dir") + "src/main/resources/export.excel";

    }

    //总公司才能看到
    @RequestMapping("/list")

    public ApiResult<List<CompanyExcel>> list(@ModelAttribute(name = "user") Map<String,Object> user) {
        checkUser(user);
        if (((Integer) user.get(jwtUtil.USER_TYPE)) == 1) {

            return ApiResult.success(iCompanyExcelService.list()) ;
        }
        return ApiResult.failed("您无权限查看");
    }

    @RequestMapping("/add")
    public ApiResult addExcel2Database(@ModelAttribute("user") Map<String,Object> user, @RequestBody @Valid CompanyExcel companyExcel){
        checkUser(user);
        if (((Integer) user.get(jwtUtil.USER_TYPE)) == 1||((Integer) user.get(jwtUtil.USER_TYPE)) == 2) {
            iCompanyExcelService.save(companyExcel);
            return ApiResult.success("添加成功");
        }
        return ApiResult.failed("添加失败");
    }

    private void checkUser(@ModelAttribute("user") Map<String, Object> user) {
        if (null == user) {
            ApiAsserts.fail("token无效");

        }
        User checkUser = iUserService.getUserByUserName((String) user.get(jwtUtil.USER_NAME));
        if (null == checkUser) {
            ApiAsserts.fail("该用户不存在!");
        }
        if (!checkUser.getPassword().equals((String) user.get(jwtUtil.PASSWORD))) {
            ApiAsserts.fail("青重新登录，您修改了密码");

        }
    }

    // easyexcel获取数据数据并导出Excel到浏览器下载下来
    @GetMapping("/exportWeb")
    public void export2Web(HttpServletResponse response) {

        try {
            ExcelUtils.export2Web(response, "学习能力评测表", "sheet1", CompanyExcel.class, iCompanyExcelService.list());
        } catch (Exception e) {
            log.error("报表导出异常:", e);
        }
    }

    //将指定路径（此处使用resources下excel作为示例）下指定名称的excel表格导出到浏览器
    //该功能一般情况下用于导出模板，别人下载模板之后按照模板写入数据到excel，然后导入数据库
    @GetMapping("/exportFileWeb/{excelName}")
    @ResponseBody
    public String export2Web4File(HttpServletResponse response, @PathVariable String excelName) {
        try {
            return ExcelUtils.export2Web4File(response, path, excelName);
        } catch (Exception e) {
            log.error("文件导出异常：", e);
        }
        return "文件导出成功";
    }


}

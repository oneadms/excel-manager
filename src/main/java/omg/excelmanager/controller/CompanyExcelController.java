package omg.excelmanager.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.jwt.JwtUtil;
import omg.excelmanager.listener.CompanyExcelDataListener;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.User;
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

import static omg.excelmanager.jwt.JwtUtil.USER_ID;

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
    private String path;

    public CompanyExcelController() {
        super();
        path = System.getProperty("CompanyExcel.dir") + "src/main/resources/export.excel";

    }

    //总公司才能看到
    @RequestMapping("/list")

    public ApiResult<List<CompanyExcel>> list(@RequestHeader(value = USER_ID) String userid ) {
        int userType = checkUser(userid);
        if ( userType == 1) {

            return ApiResult.success(iCompanyExcelService.list());
        }
        return ApiResult.failed("您无权限查看");
    }
    //查看自己的
    @RequestMapping("/list/self")
    public ApiResult<List<CompanyExcel>> listSelf(@RequestHeader(value = USER_ID) String userid ) {
        int userType = checkUser(userid);
        if ( userType == 2||userType==1) {

            return ApiResult.success(iCompanyExcelService.list(new LambdaQueryWrapper<CompanyExcel>().eq(CompanyExcel::getUserId, userid)));
        }
        return ApiResult.failed("您无权限查看");
    }

    @RequestMapping("/addExcel")
    public ApiResult addExcel2Database(@RequestHeader(value = USER_ID) String userid, @RequestBody @Valid CompanyExcel companyExcel) {
        int userType = checkUser(userid);
        if ( userType == 1||userType==2) {
            iCompanyExcelService.save(companyExcel);
            return ApiResult.success("添加成功");

        }

        return ApiResult.failed("你没有权限添加");
    }

    //未登录返回-1.
    //否则返回用户类型
    private int checkUser(String userid) {
        User user = iUserService.getUserByUserId(userid);
        if (null == user
        ) {
            return -1;
        }
        return user.getUserType();
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

    // easyexcel上传文件
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestHeader(USER_ID)String userid,MultipartFile file) throws IOException {
        int userType = checkUser(USER_ID);
        if (userType == 1||userType==2
        ) {
            EasyExcel.read(file.getInputStream(), User.class, new CompanyExcelDataListener(iCompanyExcelService)).sheet().doRead();
            return "上传成功";
        }
        return "您没有权限上传";

    }
}

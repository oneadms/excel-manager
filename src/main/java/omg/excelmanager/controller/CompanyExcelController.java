package omg.excelmanager.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.jwt.JwtUtil;
import omg.excelmanager.listener.CompanyExcelDataListener;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.User;
import omg.excelmanager.model.vo.CompanyExcelVO;
import omg.excelmanager.service.ICompanyExcelService;
import omg.excelmanager.service.IExcelAttributionService;
import omg.excelmanager.service.IUserService;
import omg.excelmanager.utils.ExcelUtils;
import omg.excelmanager.utils.easyexcel.StudentSheetReadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
@RestController
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

    public ApiResult<List<CompanyExcelVO>> list(@RequestHeader(value = USER_ID) Integer userid ) {
        int userType = checkUser(userid);
        if ( userType == 1) {
            List<CompanyExcelVO> excels = iCompanyExcelService.listExcel();


            return ApiResult.success(excels);
        }
        return ApiResult.failed("您无权限查看");
    }
    //查看自己的
    @RequestMapping("/list/self")
    public ApiResult<List<CompanyExcel>> listSelf(@RequestHeader(value = USER_ID) Integer userid ) {
        int userType = checkUser(userid);
        if ( userType == 2||userType==1) {

            return ApiResult.success(iCompanyExcelService.list(new LambdaQueryWrapper<CompanyExcel>().eq(CompanyExcel::getUserId, userid)));
        }
        return ApiResult.failed("您无权限查看");
    }

    @RequestMapping("/addExcel")
    public ApiResult addExcel2Database(@RequestHeader(value = USER_ID) Integer userid, @RequestBody @Valid CompanyExcel companyExcel) {
        int userType = checkUser(userid);
        if ( userType == 1||userType==2) {
            iCompanyExcelService.save(companyExcel);
            return ApiResult.success("添加成功");

        }

        return ApiResult.failed("你没有权限添加");
    }

    //未登录返回-1.
    //否则返回用户类型
    private int checkUser(Integer userid) {
        log.debug("userid={}",userid);
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
@Autowired
    private IExcelAttributionService excelAttributionService;
    // easyexcel上传文件
    @PostMapping("/upload")
    @ResponseBody
    public ApiResult upload(@RequestHeader(USER_ID)Integer userid,MultipartFile file) throws IOException {
        int userType = checkUser(userid);
        log.info(userType+"");

        if (userType == 1||userType==2
        ) {
            EasyExcel.read(file.getInputStream(), CompanyExcel.class, new CompanyExcelDataListener(iCompanyExcelService,userid,excelAttributionService)).sheet().
            registerReadListener(new StudentSheetReadListener()).headRowNumber(2).doRead();
            return ApiResult.success("上传成功");
        }
        return ApiResult.failed("您没有权限上传");

    }
    @GetMapping("/del/{id}")
    public ApiResult delete(@PathVariable("id") String id){
        boolean flag = iCompanyExcelService.remove(new LambdaQueryWrapper<CompanyExcel>().eq(CompanyExcel::getUserId, id));
        if (flag) {
            return ApiResult.success("删除成功");
        }
        return ApiResult.failed("删除失败");
    }
}

package omg.excelmanager.controller;


import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.common.exception.ApiAsserts;
import omg.excelmanager.jwt.jwtUtil;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.User;
import omg.excelmanager.service.ICompanyExcelService;
import omg.excelmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
@Controller
@RequestMapping("/company_excel")
public class CompanyExcelController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICompanyExcelService iCompanyExcelService;
    //总公司才能看到
    @RequestMapping("/list")

    public ApiResult<List<CompanyExcel>> list(@ModelAttribute(name = "user") Map<String,Object> user) {
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
        return ApiResult.success(iCompanyExcelService.list());
    }
}

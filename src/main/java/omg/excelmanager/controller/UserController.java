package omg.excelmanager.controller;


import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.model.dto.LoginDTO;
import omg.excelmanager.model.dto.RegisterDTO;
import omg.excelmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @PostMapping("/login")

    public ApiResult<Map<String,String>> login(@Valid      @RequestBody LoginDTO loginDTO) {
        Map<String,String> map = new HashMap<>();
        String token = iUserService.executeLogin(loginDTO);
        if (null == token) {
           return ApiResult.failed("账号密码错误");
        }
        map.put("token", token);
        return ApiResult.success(map);
    }
    @PostMapping("/register")

    public ApiResult<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        boolean res = iUserService.executeRegister(registerDTO);
        if (res) {
            return ApiResult.success("注册成功");

        }
        return ApiResult.failed("注册失败");
    }


}

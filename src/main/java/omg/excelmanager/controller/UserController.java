package omg.excelmanager.controller;


import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.jwt.JwtUtil;
import omg.excelmanager.model.dto.LoginDTO;
import omg.excelmanager.model.dto.RegisterDTO;
import omg.excelmanager.model.entity.User;
import omg.excelmanager.model.vo.UserVo;
import omg.excelmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userinfo")
    public ApiResult<UserVo> getUserInfo(@RequestHeader(value = JwtUtil.USER_ID)String userid ) {
        User user = iUserService.getUserByUserId(userid);
        UserVo response = UserVo.builder().username(user.getUsername()).nickname(user.getNickname()).userType(user.getUserType()).build();
        return ApiResult.success(response);
    }


}

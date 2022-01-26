package omg.excelmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jdk.nashorn.internal.parser.Token;
import omg.excelmanager.common.exception.ApiAsserts;
import omg.excelmanager.common.exception.ApiException;
import omg.excelmanager.jwt.jwtUtil;
import omg.excelmanager.model.dto.LoginDTO;
import omg.excelmanager.model.dto.RegisterDTO;
import omg.excelmanager.model.entity.User;
import omg.excelmanager.mapper.UserMapper;
import omg.excelmanager.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User getUserByUserName(String username) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public boolean executeRegister(RegisterDTO registerDTO) {
        User user = getUserByUserName(registerDTO.getUsername());
        if (null != user) {
            ApiAsserts.fail("该账号已经存在");

        }
        User registerUser = User.builder()
                .username(registerDTO.getUsername())
                .nickname(registerDTO.getNickname())
                .password(registerDTO.getPassword())
                .userType(registerDTO.getUserType())
                .build();

        return this.baseMapper.insert(registerUser)>0;
    }

    @Override
    public String executeLogin(LoginDTO loginDTO) {
        User user = this.getUserByUserName(loginDTO.getUsername());
        if (null == user) {
            ApiAsserts.fail("该用户不存在");
        }
        String token=null;
        if (user.getUsername().equals(loginDTO.getUsername())) {
             token = jwtUtil.generateToken(user);

        }
        return token;
    }

}

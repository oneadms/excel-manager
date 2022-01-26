package omg.excelmanager.service;

import omg.excelmanager.model.dto.LoginDTO;
import omg.excelmanager.model.dto.RegisterDTO;
import omg.excelmanager.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
public interface IUserService extends IService<User> {
     String executeLogin(LoginDTO loginDTO);
     boolean executeRegister(RegisterDTO registerDTO);
     User getUserByUserName(String username);

}

package omg.excelmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "请输入您的用户名")
    @Size(min = 5,max = 16,message = "长度为5-16字符")

    private String username;
    @NotBlank(message = "请输入您的昵称")
    private String nickname;
    @Size(min = 7,max = 16,message = "长度为7-16字符")
    @NotBlank(message = "请输入您的密码")
    private String password;
    @NotBlank(message = "请选择用户类型")
    private Integer userType;

}

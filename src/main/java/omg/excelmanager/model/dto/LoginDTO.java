package omg.excelmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 5,max = 16,message = "账号长度为5-16字符")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 7,max = 16,message = "账号长度为7-16字符")
    private String password;
}

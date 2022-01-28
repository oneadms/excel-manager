package omg.excelmanager.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.User;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CompanyExcelVO   {
    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    private Integer userId;

    /**
     * 项目类型
     */

    private String projectType;

    /**
     * 学习能力总评
     */

    private String overallReview;

    /**
     * 能力类别
     */

    private String abilityCategory;

    /**
     * 评测分值
     */

    private Double evaluationScore;

    /**
     * 同类均分
     */

    private Double sameClass;

    /**
     * 对比差值
     */

    private Double contrastDifference;



    private String username;

    /**
     * 1 为 总公司 2为分公司 3 为客户
     */
    private Integer userType;

    private String nickname;

}

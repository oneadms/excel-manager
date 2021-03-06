package omg.excelmanager.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
@Data
@ToString
@TableName("company_excel")
@Accessors(chain = true)
public class CompanyExcel implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    private Integer userId;
    @ExcelIgnore
    private Integer excelId;
    @ExcelIgnore

    private String excelTitle;
    /**
     * 项目类型
     */
    @ExcelProperty("项目类型")
    private String projectType;

    /**
     * 学习能力总评
     */
    @ExcelProperty("学习能力总评")
    private String overallReview;

    /**
     * 能力类别
     */
    @ExcelProperty("能力类别")
    private String abilityCategory;

    /**
     * 评测分值
     */
    @ExcelProperty("评测分值")
    private Double evaluationScore;

    /**
     * 同类均分
     */
    @ExcelProperty("同类均分")
    private Double sameClass;

    /**
     * 对比差值
     */
    @ExcelProperty("对比差值")
    private Double contrastDifference;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getOverallReview() {
        return overallReview;
    }

    public void setOverallReview(String overallReview) {
        this.overallReview = overallReview;
    }
    public String getAbilityCategory() {
        return abilityCategory;
    }

    public void setAbilityCategory(String abilityCategory) {
        this.abilityCategory = abilityCategory;
    }
    public Double getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Double evaluationScore) {
        this.evaluationScore = evaluationScore;
    }
    public Double getSameClass() {
        return sameClass;
    }

    public void setSameClass(Double sameClass) {
        this.sameClass = sameClass;
    }
    public Double getContrastDifference() {
        return contrastDifference;
    }

    public void setContrastDifference(Double contrastDifference) {
        this.contrastDifference = contrastDifference;
    }

}

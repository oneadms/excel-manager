package omg.excelmanager.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName excel_attribution
 */
@Accessors(chain = true)
@Data
@TableName("excel_attribution")
public class ExcelAttribution implements Serializable {

    /**
     *
     */
    @TableId(value = "excel_id",type = IdType.AUTO)
    private Integer excelId;
    @TableField("`title`")
    private String title;
}
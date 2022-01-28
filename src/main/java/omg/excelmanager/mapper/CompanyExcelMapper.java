package omg.excelmanager.mapper;

import omg.excelmanager.model.entity.CompanyExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import omg.excelmanager.model.vo.CompanyExcelVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
public interface CompanyExcelMapper extends BaseMapper<CompanyExcel> {

    List<CompanyExcelVO> listExcel();
}

package omg.excelmanager.service;

import omg.excelmanager.model.entity.CompanyExcel;
import com.baomidou.mybatisplus.extension.service.IService;
import omg.excelmanager.model.vo.CompanyExcelVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
public interface ICompanyExcelService extends IService<CompanyExcel> {

    List<CompanyExcelVO> listExcel();
}

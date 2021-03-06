package omg.excelmanager.service.impl;

import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.mapper.CompanyExcelMapper;
import omg.excelmanager.model.vo.CompanyExcelVO;
import omg.excelmanager.service.ICompanyExcelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oneadm
 * @since 2022-01-27
 */
@Service
public class CompanyExcelServiceImpl extends ServiceImpl<CompanyExcelMapper, CompanyExcel> implements ICompanyExcelService {
    @Override
    public List<CompanyExcelVO> listExcel() {
        return this.baseMapper.listExcel();
    }
}

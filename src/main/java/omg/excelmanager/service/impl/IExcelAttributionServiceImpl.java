package omg.excelmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import omg.excelmanager.mapper.ExcelAttributionMapper;
import omg.excelmanager.model.entity.ExcelAttribution;
import omg.excelmanager.service.IExcelAttributionService;
import org.springframework.stereotype.Service;

@Service
public class IExcelAttributionServiceImpl extends ServiceImpl<ExcelAttributionMapper, ExcelAttribution> implements IExcelAttributionService {
}

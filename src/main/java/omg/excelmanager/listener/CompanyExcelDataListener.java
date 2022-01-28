package omg.excelmanager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.model.entity.CompanyExcel;
import omg.excelmanager.model.entity.ExcelAttribution;
import omg.excelmanager.service.ICompanyExcelService;
import omg.excelmanager.service.IExcelAttributionService;
import omg.excelmanager.utils.easyexcel.Constant;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CompanyExcelDataListener extends AnalysisEventListener<CompanyExcel> {

    private final Integer userId;
    private ICompanyExcelService companyExcelService;

    private IExcelAttributionService excelAttributionService;

    public CompanyExcelDataListener(ICompanyExcelService companyExcelService, Integer userId,IExcelAttributionService excelAttributionService) {
        this.companyExcelService = companyExcelService;
        this.excelAttributionService = excelAttributionService;
        this.userId = userId;

    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<CompanyExcel> list = new ArrayList<CompanyExcel>();

    @Override
    public void invoke(CompanyExcel data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        log.info("Title={}",Constant.getTitle());
        ExcelAttribution excelAttribution = new ExcelAttribution();
        excelAttributionService.save(excelAttribution
                .setTitle(Constant.getTitle()));
        data.setUserId(userId);
        data.setExcelId(excelAttribution.getExcelId());
        data.setExcelTitle(Constant.getTitle());
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());


        if (!CollectionUtils.isEmpty(list)) {
            companyExcelService.saveBatch(list);
        }
        log.info("存储数据库成功！");
    }
}

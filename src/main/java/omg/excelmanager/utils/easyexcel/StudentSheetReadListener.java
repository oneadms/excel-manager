package omg.excelmanager.utils.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.model.entity.CompanyExcel;

import java.util.Map;

@Slf4j
public class StudentSheetReadListener implements ReadListener<CompanyExcel> {
    @Override
    public void invoke(CompanyExcel data, AnalysisContext context) {

    }
    boolean flag=true;
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        headMap.forEach((k,v)->{
            //k为index
            //v 为单元格数据
            log.debug("K为{}",k);
            if (flag) {
                String headData = v.getStringValue();
                Constant.setTitle(headData);
                flag = false;
            }
            System.out.println(v.getStringValue());
        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        ReadListener.super.onException(exception, context);
    }
}

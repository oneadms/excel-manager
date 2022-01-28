package omg.excelmanager.utils;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * easyexcel的自动列宽有些小问题，将官方代码略微修改一下使用
 * 此处尽量使用高一些的版本，不然无法修改CellData为List<CellData>
 */
public class Custemhandler extends AbstractColumnWidthStyleStrategy{
    private static final int MAX_COLUMN_WIDTH = 255;
    //因为在自动列宽的过程中，有些设置地方让列宽显得紧凑，所以做出了个判断
    private static final int COLUMN_WIDTH = 20;
    private  Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);

    public Custemhandler() {
    }



    private  Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = (CellData)cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch(type) {
                    case STRING:
                        return cellData.getStringValue().getBytes().length;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }
}

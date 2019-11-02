package com.example.excelto.Service;

import com.example.excelto.Entity.DataAllEntity;
import com.example.excelto.Entity.DataTabEntity;
import com.example.excelto.Repostory.DataAllRepository;
import com.example.excelto.Repostory.DataTabRepository;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.Formula;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.druid.support.monitor.annotation.AggregateType.Last;

@Service
@Transactional(rollbackOn = Exception.class)
public class DataAllServiceImpl implements DataAllService {

    @Autowired
    private DataAllRepository dataAllRepository;

    @Autowired
    private DataTabRepository dataTabRepository;

    @Value("${file.savePath}")
    private String fileSavePath;

    @Override
    public void addDataAll(String monthDataOn, String path) {
        DataTabEntity dataTabEntity = new DataTabEntity();
        dataTabEntity.setDateTime(monthDataOn);
        DataTabEntity save = dataTabRepository.save(dataTabEntity);
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        // return a list contains many list
        List<List<String>> lists = new ArrayList<List<String>>();
        Gson gson = new Gson();

        //读取excel文件
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            //获取工作薄
            Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
            }

            for (int w = 0; w < wb.getNumberOfSheets(); w++) {
                is = new FileInputStream(path);
                //获取工作薄
                if (fileType.equals("xls")) {
                    wb = new HSSFWorkbook(is);
                } else if (fileType.equals("xlsx")) {
                    wb = new XSSFWorkbook(is);
                } else {
                }
                //读取第一个工作页sheet
                Sheet sheet = wb.getSheetAt(w);
                //第一行为标题
                for (Row row : sheet) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (Cell cell : row) {
                        //根据不同类型转化成字符串
                        if(cell.getCellType()== CellType.FORMULA){
                            if(cell.getCachedFormulaResultType() == CellType.NUMERIC){
                                list.add(String.valueOf(cell.getNumericCellValue()));
                            }else{

                            }
                        }else {
                            list.add(cell.toString());

                        }
                    }
                    lists.add(list);

                }
                String s1 = gson.toJson(lists);
                DataAllEntity dataAllEntity = new DataAllEntity();
                dataAllEntity.setDataTabEntity(dataTabEntity);
                dataAllEntity.setDataMore(s1);
                dataAllRepository.save(dataAllEntity);
                is.close();
                lists.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

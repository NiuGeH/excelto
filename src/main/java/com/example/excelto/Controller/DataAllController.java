package com.example.excelto.Controller;

import com.example.excelto.Entity.DataAllEntity;
import com.example.excelto.Entity.DataTabEntity;
import com.example.excelto.Repostory.DataAllRepository;
import com.example.excelto.Repostory.DataTabRepository;
import com.example.excelto.Service.DataAllService;
import com.example.excelto.commons.AjaxResult;
import com.example.excelto.commons.AjaxUtils;
import com.example.excelto.commons.Func_T;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/dataAll")
@Controller
public class DataAllController {


    @Autowired
    private DataAllRepository dataAllRepository;

    @Autowired
    private DataTabRepository dataTabRepository;

    @Autowired
    private DataAllService dataAllService;

    @Value("${file.savePath}")
    private String fileSavePath;

    /**
     * 上传excel
     */
    @RequestMapping("/uploadCardImageExamination")
    @ResponseBody
    public AjaxResult uploadExaminationReport(String monthDataOn, final HttpServletRequest request) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.err.println(monthDataOn);
                List<DataTabEntity> allByDateTime = dataTabRepository.findAllByDateTime(monthDataOn);
                if(allByDateTime.size() > 0 ){
                    return "dohave";
                }
                String fileName = null;
                String nowDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
                File file = new File(fileSavePath + nowDay);
                if (!file.exists()) {
                    file.mkdir();// 创建文件根目录
                }
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
                int begin = mf.getOriginalFilename().indexOf(".");
                int last = mf.getOriginalFilename().length();

                fileName = UUID.randomUUID().toString() + mf.getOriginalFilename().substring(begin, last);
                String savePath = file.getPath() + File.separator + fileName;
                File saveFile = new File(savePath);
                FileUtils.copyInputStreamToFile(mf.getInputStream(), saveFile);

                dataAllService.addDataAll(monthDataOn,savePath);
                return "success";
            }
        });
    }

    @RequestMapping("/findBydateTimeReturnNum")
    @ResponseBody
    public AjaxResult findByIndex(String date_time){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.err.println(date_time);
                List<DataTabEntity> allByDateTime = dataTabRepository.findAllByDateTime(date_time);
                if(allByDateTime.size() >0){
                    List<Long> allByDataTabEntity = dataAllRepository.findAllByDataTabEntity(allByDateTime.get(0));
                    HashMap<String,Object> hashMap = new HashMap<>();
                    if(allByDataTabEntity.size() > 0){
                        hashMap.put("listID",allByDataTabEntity);
                        DataAllEntity dataAllEntity = dataAllRepository.findById(allByDataTabEntity.get(0)).get();
                        Gson gson = new Gson();
                        ArrayList arrayList = gson.fromJson(dataAllEntity.getDataMore(), new ArrayList<ArrayList<String>>().getClass());
                        dataAllEntity.setList(arrayList);
                        hashMap.put("dataAll",dataAllEntity);
                        return hashMap;
                    }else {
                        return "此日期无数据";
                    }
                }else{
                    return "此日期无数据";
                }

            }
        });
    }

    @RequestMapping("/findByDataId")
    @ResponseBody
    public AjaxResult findByDataId(Long dataId){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.out.println(dataId);
                DataAllEntity dataAllEntity = dataAllRepository.findById(dataId).get();
                Gson gson = new Gson();
                ArrayList arrayList = gson.fromJson(dataAllEntity.getDataMore(), new ArrayList<ArrayList<String>>().getClass());
                dataAllEntity.setList(arrayList);
                return dataAllEntity;
            }
        });
    }

}

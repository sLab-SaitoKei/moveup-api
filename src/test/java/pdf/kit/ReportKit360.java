package pdf.kit;

import com.google.common.collect.Lists;
import jp.co.vermore.kit.component.PDFHeaderFooter;
import jp.co.vermore.kit.component.PDFKit;
import jp.co.vermore.kit.component.chart.model.XYLine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 * 360报告
 *
 */
@Slf4j
public class ReportKit360 {

    public static List<XYLine> getTemperatureLineList() {
        List<XYLine> list= Lists.newArrayList();
        for(int i=1;i<=7;i++){
            XYLine line=new XYLine();
            float random=Math.round(Math.random()*10);
            line.setxValue("星期"+i);
            line.setyValue(20+random);
            line.setGroupName("下周");
            list.add(line);
        }
        for(int i=1;i<=7;i++){
            XYLine line=new XYLine();
            float random=Math.round(Math.random()*10);
            line.setxValue("星期"+i);
            line.setyValue(20+random);
            line.setGroupName("这周");
            list.add(line);
        }
        return list;
    }

    public  String createPDF(String templatePath,Object data, String fileName){
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter=new PDFHeaderFooter();
            PDFKit kit=new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath("/Users/fgm/Desktop/pdf/hello.pdf");

            String saveFilePath=kit.exportToFile(fileName,data);
            return  saveFilePath;
        } catch (Exception e) {
//            log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(1);
        ReportKit360 kit=new ReportKit360();
        TemplateBO templateBO=new TemplateBO();
        List<String> scores=new ArrayList<String>();
        scores.add("90");
        scores.add("95");
        scores.add("999998");
        templateBO.setScores(scores);

        String templatePath="/moveup/src/test/resources/templates";
        String path= kit.createPDF(templatePath,templateBO,"hello.pdf");
        System.out.println(path);
    }

}

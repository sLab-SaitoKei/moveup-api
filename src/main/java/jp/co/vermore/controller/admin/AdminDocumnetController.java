package jp.co.vermore.controller.admin;

import jp.co.vermore.form.DashboardForm;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * AdminDocumnetController
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/04/26 21:49
 * Copyright: sLab, Corp
 */

@Controller
public class AdminDocumnetController {

    @RequestMapping(value = "/admin/document/list/", method = RequestMethod.GET)
    public String documnetList(Model model){
        return "admin/documentList";
    }

    @RequestMapping(value = "/admin/static/file/{name}/", method = RequestMethod.GET)
    public ResponseEntity<byte[]> down(HttpServletRequest request, @PathVariable String name) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String path = request.getSession().getServletContext().getRealPath("/");
        String pdfUrl = path+"static/file/" + name + ".pdf";
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String((name+".pdf").getBytes("UTF-8"), "iso-8859-1"));
        File f =new File(pdfUrl);
        InputStream in = new FileInputStream(f);
        byte[] b=new byte[in.available()];
        in.read(b);
  //      HttpStatus statusCode=HttpStatus.OK;
  //      FileUtils fu = new FileUtils.readFileToByteArray(f);
        ResponseEntity<byte[]> r = new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);
        return (r);
    }

}

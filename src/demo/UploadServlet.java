package demo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmnt on 2016/12/10.
 */
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String path =req.getRealPath("/upload");
        File file=new File(path);
        if (!file.getParentFile().exists()){
            file.mkdir();
        }

        DiskFileItemFactory factory=new DiskFileItemFactory();
        //factory.setRepository(file) ;
        ServletFileUpload fileUpload=new ServletFileUpload(factory);
        fileUpload.setHeaderEncoding("utf-8");
        fileUpload.setFileSizeMax(8*1024*1024);
        String filename=null;
        List<String> list=new ArrayList<>();
        try {
            List<FileItem> items=fileUpload.parseRequest(req);
            for (FileItem item:items)
            {
                if (item.isFormField())
                {
                    list.add(item.getString().toString());
                    //out.println(item.getString().toString());
                }
                else if (!item.isFormField()){
                    filename= item.getName();
                    item.write(new File(path+File.separator+item.getName()));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

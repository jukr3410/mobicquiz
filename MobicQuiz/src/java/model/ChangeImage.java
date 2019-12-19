/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Wine.N
 */
public class ChangeImage {
    
    public void editImages(HttpServletRequest request, String target, String name){
        File targetFile = new File(target + name + ".jpg");
        try {
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> file = sf.parseRequest(request);
                        
            for (FileItem item : file) {
                item.write(targetFile);
            }
            System.out.println("*****************************************************");
            System.out.println("Upload image successfully.");

        } catch (Exception e){
            e.printStackTrace();
        }
        
        
    }
}

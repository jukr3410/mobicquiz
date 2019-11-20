/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Wine.N
 */
public class ChangeImage {
    
    public void editImages(String source, String target, String name){
        File sourceFile = new File(source);
        File targetFile = new File(target + name + ".jpg");
 
        System.out.println("***************************************************************");
        System.out.println("Copying file : " + sourceFile.getName() + " from Java Program");
        try {
            FileUtils.copyFile(sourceFile, targetFile);
            System.out.println("***************************************************************");
            System.out.println("copying of file from Java program is completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}

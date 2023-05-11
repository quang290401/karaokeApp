/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package KaraokeApp.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import javax.imageio.ImageIO;

/**
 *
 * @author 84374
 */
public class LoadImg {

    private byte[] imgByte;

    public LoadImg() {
    }

    public String getDirToImg() {
        String dir=null;
        try {
            String path = "images/karaoke.jpg";
            File file = new File(path);
            String absolutePath = file.getAbsolutePath();
            dir=absolutePath;
            System.out.println(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }
}

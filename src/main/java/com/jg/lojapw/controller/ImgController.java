package com.jg.lojapw.controller;

import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImgController {

    private static String imgPath = System.getProperty("user.dir")+"\\imgs\\";

    @Autowired
    private ProdutoRepo produtoRepo;


    @GetMapping("mostrarImg/{img}")
    @ResponseBody
    public byte[] mostrarImg(@PathVariable("img") String img) throws IOException {

        System.out.println(img);
        File arqImg = new File(imgPath+img);

        if (img != null || img.trim().length() > 0){
            return Files.readAllBytes(arqImg.toPath());
        }
        return null;
    }

}

package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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

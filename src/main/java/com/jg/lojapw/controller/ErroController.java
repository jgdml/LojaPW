package com.jg.lojapw.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErroController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView acessarPaginaErro(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("erro");

        Object errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);


        String erroInfo = "Detalhes: Erro " + errorCode.toString();

        mv.addObject("erroInfo", erroInfo);
        return mv;
    }
}

package com.jg.lojapw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

@Controller
public class AdminReportController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("administrativo/relatorios/vendas")
    public void vendas(HttpServletResponse response) throws JRException, SQLException, IOException {
        sendPdfReport(response, "/reports/vendas.jasper");
    }

    @GetMapping("administrativo/relatorios/clientes")
    public void clientes(HttpServletResponse response) throws JRException, SQLException, IOException {
        sendPdfReport(response, "/reports/clientes.jasper");
    }

    private void sendPdfReport(HttpServletResponse response, String file) throws JRException, IOException, SQLException {
        JasperPrint report = buildReport(file);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=relatorioVendas.pdf");

        OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(report, outputStream);
    }

    private JasperPrint buildReport(String file) throws JRException, SQLException{
        InputStream jasper = this.getClass().getResourceAsStream(file);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasper);
        return JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());
    }


}



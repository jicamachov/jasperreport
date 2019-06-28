package com.dca.jasperreport.service.impl;

import com.dca.jasperreport.repository.impl.UserRepositoryImpl;
import com.dca.jasperreport.repository.impl.UserRepositoryJPA;
import com.dca.jasperreport.service.UserService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    //@Autowired
    //private UserRepositoryImpl userRepository;

    @Autowired
    private UserRepositoryJPA userRepository;

    @Override
    public ResponseEntity<byte[]> exportPdfFile() throws SQLException, JRException, IOException {
        JasperPrint jasperPrint = null;
        HttpHeaders headers = this.header("exportpdf", "pdf");
        jasperPrint = userRepository.generateReport();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    @Override
    public ResponseEntity<byte[]> exportXlsxFile() throws SQLException, JRException, IOException {
        JasperPrint jasperPrint = null;
        jasperPrint = userRepository.generateReport();
        HttpHeaders headers = this.header("exportxls", "xlsx");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setIgnoreGraphics(false);
        Exporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    @Override
    public ResponseEntity<byte[]> exportDocxFile() throws SQLException, JRException, IOException {
        JasperPrint jasperPrint = null;
        jasperPrint = userRepository.generateReport();
        HttpHeaders headers = this.header("exportdoc", "docx");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        exporter.exportReport();
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    private HttpHeaders header(String reportName, String format) {
        String dateNow = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String random = String.valueOf(Math.round(Math.random() * 100000));
        String attachment = String.format("attachment; filename=%s_%s_%s.%s", reportName, dateNow, random, format);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", attachment);
        return  headers;
    }
}

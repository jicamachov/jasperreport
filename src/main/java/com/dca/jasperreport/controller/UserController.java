package com.dca.jasperreport.controller;

import com.dca.jasperreport.service.impl.UserServiceImpl;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.sql.SQLException;

@RestController
@RequestMapping("export")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportpdf() throws SQLException, JRException, IOException {
       return userService.exportPdfFile();
    }

    @GetMapping("/xlsx")
    public ResponseEntity<byte[]> exportexcel() throws SQLException, JRException, IOException {
        return userService.exportXlsxFile();
    }

    @GetMapping("/docx")
    public ResponseEntity<byte[]> exportdoc() throws SQLException, JRException, IOException {
       return userService.exportDocxFile();
    }
}

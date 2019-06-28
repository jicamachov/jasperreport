package com.dca.jasperreport.service;

import com.dca.jasperreport.repository.UserRepository;
import com.dca.jasperreport.repository.impl.UserRepositoryImpl;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;


public interface UserService {
    ResponseEntity<byte[]> exportPdfFile() throws SQLException, JRException, IOException;
    ResponseEntity<byte[]> exportXlsxFile() throws SQLException, JRException, IOException;
    ResponseEntity<byte[]> exportDocxFile() throws SQLException, JRException, IOException;
}

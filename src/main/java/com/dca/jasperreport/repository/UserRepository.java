package com.dca.jasperreport.repository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;
import java.sql.SQLException;

public interface UserRepository {
    JasperPrint generateReport()  throws SQLException, JRException, IOException;
}

package com.dca.jasperreport.repository.impl;

import net.sf.jasperreports.engine.*;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryJPA {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    public JasperPrint generateReport()throws SQLException, JRException, IOException {
        String path = resourceLoader.getResource("classpath:reports/rpt_users.jrxml").getURI().getPath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        return print;
    }
}

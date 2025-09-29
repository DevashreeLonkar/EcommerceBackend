package com.ecommerce.jwt.reports;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource; // uses your Spring Boot DB connection

    public byte[] exportReport() throws Exception {
        // Load the precompiled .jasper file from resources/reports
        InputStream reportStream = getClass().getResourceAsStream("/reports/OrderDetailsReport.jasper");

        if (reportStream == null) {
            throw new RuntimeException("Report file not found in resources/reports/");
        }

        // Fill the report with data (DB connection + empty parameters map)
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                reportStream,
                new HashMap<>(),
                dataSource.getConnection()
        );

        // Export the filled report to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}

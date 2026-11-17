package com.automationexercise.utils.report;

import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.automationexercise.utils.dataReader.PropertyReader.getProperty;
import static com.automationexercise.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.automationexercise.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;

public class AllureReportGenerator {
    //Generate Allure report
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }
    /**
    //open Allure report in browser
    public static void openReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local")) return;

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportPath.toString());
            case MAC, LINUX -> TerminalUtils.executeTerminalCommand("open", reportPath.toString());
            default -> LogsManager.warn("Opening Allure Report is not supported on this OS.");
        }
    }
     **/
    //open Allure report in browser
    public static void openReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local")) return;

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);

        try {
            // FIX: Use Java's Desktop API instead of cmd.exe to handle spaces in paths correctly
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(reportPath.toUri());
            } else {
                LogsManager.warn("Desktop is not supported on this OS.");
            }
        } catch (Exception e) {
            LogsManager.error("Failed to open the report: " + e.getMessage());
        }
    }
    //copy history folder to results folder
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULTS_HISTORY_FOLDER.toFile());
        } catch (Exception e) {
            LogsManager.error("Error copying history files", e.getMessage());
        }
    }

    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX + TimeManager.getTimestamp() + AllureConstants.REPORT_EXTENSION;
        com.automationexercise.FileUtils.renameFile(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }
}

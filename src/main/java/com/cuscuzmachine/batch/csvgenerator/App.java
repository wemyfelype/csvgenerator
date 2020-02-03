package com.cuscuzmachine.batch.csvgenerator;

import com.cuscuzmachine.batch.csvgenerator.utils.Help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

// Author: Wemy Vieira Felipe (85) 987741858
// Copyright (c) Cuscuz Machine. All rights reserved.

public class App {
  private static final List<String> yesList = Arrays.asList(new String[] { "yes", "sim", "y", "s" });
  
  private static final List<String> noList = Arrays.asList(new String[] { "no", "nao", "n" });
  
  private static boolean withTitle = true;
  
  private static String procedureName = "";
  
  private static String fileName = null;
  
  private static String dirName = "C:/Innovation/";
  
	public static void main(String[] args) {
		
		populateArgs(args);
		Connection conn = getConnection();
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuilder sb = new StringBuilder();
		 
		try {
      
      CallableStatement cstmt = conn.prepareCall("CALL " + procedureName);
      
      rs = cstmt.executeQuery();
			rsmd = rs.getMetaData();
      
      int numColumns = rsmd.getColumnCount();
      
      if (withTitle) {
				for (int i = 1; i <= numColumns; i++) {
					sb.append(rsmd.getColumnName(i));
					if (i < numColumns)
						sb.append(";");
				}
				sb.append(System.getProperty("line.separator"));
      }
      
			while (rs.next()) {
        
        for (int i = 1; i <= numColumns; i++) {
					sb.append(rs.getObject(i));
					if (i < numColumns)
						sb.append(";");
				}
				sb.append(System.getProperty("line.separator"));

			}
	  
	  if (fileName == null)
		fileName = procedureName.split("\\(")[0];

      
      try (PrintWriter writer = new PrintWriter(new File(dirName + fileName + ".csv"))) {
				writer.write(sb.toString());
				System.out.println("Arquivo gerado com sucesso em " + dirName + fileName + ".csv");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
      }
      
		} catch (SQLException e) {
			System.out.println("Falha ao gerar o arquivo " + dirName + fileName + ".csv");
			e.printStackTrace();
			return;
		}
	}
  
  private static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("","","");
    } catch (ClassNotFoundException|SQLException e) {
      e.printStackTrace();
    } 
    return connection;
  }
  
  private static void populateArgs(String[] args) {
    
    if (args.length > 0)
      for (int i = 0; i < args.length; i++) {
        switch (args[i].toUpperCase()) {
          case "-P":
            procedureName = args[++i];
            break;
          case "-T":
            if (yesList.contains(args[++i].toLowerCase())) {
              withTitle = true;
              break;
            } 
            if (noList.contains(args[++i].toLowerCase())) {
              withTitle = false;
              break;
            } 
            System.out.println("O comando [ " + args[i] + " " + args[++i] + " ] nao foi reconhecido.");
            Help.show();
            return;
          case "-F":
            fileName = args[++i];
            break;
          case "-D":
            dirName = args[++i];
            break;
          case "-H":
            dirName = args[++i];
            return;
          case "-HELP":
            dirName = args[i];
            return;
          default:
            System.out.println("O comando [ " + args[i] + " ] nao foi reconhecido.");
            Help.show();
            return;
        } 
      }  
  }

}

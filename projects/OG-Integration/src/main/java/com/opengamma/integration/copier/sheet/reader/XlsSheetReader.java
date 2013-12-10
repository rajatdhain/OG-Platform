/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */

package com.opengamma.integration.copier.sheet.reader;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.tuple.Pair;

/**
 * A class for importing portfolio data from XLS (pre-Excel 2007) worksheets
 * TODO XLS reader is incomplete, and does not really work yet!!!
 */
public class XlsSheetReader extends SheetReader {

  private Sheet _sheet;
  private Workbook _workbook;
  private int _currentRowIndex =0;
  private InputStream _inputStream;
  
  public XlsSheetReader(String filename, int sheetIndex) {
    
    ArgumentChecker.notEmpty(filename, "filename");

    _inputStream = openFile(filename);
    _workbook = getWorkbook(_inputStream);
    _sheet = _workbook.getSheetAt(sheetIndex);
    _currentRowIndex = _sheet.getFirstRowNum();
    
    // Read in the header row
    Row rawRow = _sheet.getRow(_currentRowIndex++);
    
    // Normalise read-in headers (to lower case) and set as columns
    setColumns(getColumnNames(rawRow));
  }
  
  public XlsSheetReader(String filename, String sheetName) {
    
    ArgumentChecker.notEmpty(filename, "filename");
    ArgumentChecker.notEmpty(sheetName, "sheetName");

    InputStream fileInputStream = openFile(filename);
    _workbook = getWorkbook(fileInputStream);
    _sheet = _workbook.getSheet(sheetName);
    _currentRowIndex = _sheet.getFirstRowNum();

    // Read in the header row
    Row rawRow = _sheet.getRow(_currentRowIndex++);

    // Normalise read-in headers (to lower case) and set as columns
    setColumns(getColumnNames(rawRow)); 
  }

  public XlsSheetReader(InputStream inputStream, int sheetIndex) {
    
    ArgumentChecker.notNull(inputStream, "inputStream");

    _workbook = getWorkbook(inputStream);
    _sheet = _workbook.getSheetAt(sheetIndex);
    _currentRowIndex = _sheet.getFirstRowNum();
    
    // Read in the header row
    Row rawRow = _sheet.getRow(_currentRowIndex++);
    
    // Normalise read-in headers (to lower case) and set as columns
    setColumns(getColumnNames(rawRow));
  }
  
  public XlsSheetReader(InputStream inputStream, String sheetName) {
    
    ArgumentChecker.notNull(inputStream, "inputStream");
    ArgumentChecker.notEmpty(sheetName, "sheetName");

    _workbook = getWorkbook(inputStream);
    _sheet = _workbook.getSheet(sheetName);
    _currentRowIndex = _sheet.getFirstRowNum();

    // Read in the header row
    Row rawRow = _sheet.getRow(_currentRowIndex++);

    String[] columns = getColumnNames(rawRow);
    setColumns(columns); 
  }

  
  private Workbook getWorkbook(InputStream inputStream) {
    
    try {
      return new HSSFWorkbook(inputStream);
    } catch (IOException ex) {
      throw new OpenGammaRuntimeException("Error opening Excel workbook: " + ex.getMessage());
    }    
  }
  
  @Override
  public Map<String, String> loadNextRow() {
    
    // Get a reference to the next Excel row
    Row rawRow = _sheet.getRow(_currentRowIndex++);

    // If the row is empty return null (assume end of table)
    if (rawRow == null || rawRow.getFirstCellNum() == -1) {
      return null; // new HashMap<String, String>();
    } 
      
   
    // Map read-in row onto expected columns
    Map<String, String> result = new HashMap<String, String>();
    for (int i = 0; i < getColumns().length; i++) {
      String cell = getCell(rawRow, rawRow.getFirstCellNum() + i).trim();
      if (cell != null && cell.length() > 0) {
        result.put(getColumns()[i], cell);
      }
    }

    return result;
  }
  
  private String[] getColumnNames(Row rawRow) {
    String[] columns = new String[rawRow.getPhysicalNumberOfCells()];
    for (int i = 0; i < rawRow.getPhysicalNumberOfCells(); i++) {
      columns[i] = getCell(rawRow, i).trim().toLowerCase();
    }
    return columns;
  }
  
  private static Cell getCellSafe(Row rawRow, int column) {
    return rawRow.getCell(column, Row.CREATE_NULL_AS_BLANK);
  }
  
  private static String getCell(Row rawRow, int column) {
    return getCellAsString(getCellSafe(rawRow, column));
  }
  
  private static String getCellAsString(Cell cell) {

    if (cell == null) {
      return "";
    }
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_NUMERIC:
        //return Double.toString(cell.getNumericCellValue());
        return (new DecimalFormat("#.##")).format(cell.getNumericCellValue());
      case Cell.CELL_TYPE_STRING:
        return cell.getStringCellValue();
      case Cell.CELL_TYPE_BOOLEAN:
        return Boolean.toString(cell.getBooleanCellValue());
      case Cell.CELL_TYPE_BLANK:
        return "";
      default:
        return null;
    }
  }

  @Override
  public void close() {
    try {
      if (_inputStream != null) {
        _inputStream.close();
      }
    } catch (IOException ex) {
      // TODO Auto-generated catch block
    }
  }

  public int getCurrentRowIndex() {
    return _currentRowIndex++;
  }

  public Map<String, String> readKeyValueBlock(int startRow, int startCol) {
    Map<String, String> keyValueMap = new HashMap<>();
    _currentRowIndex = startRow;
    Row row = _sheet.getRow(_currentRowIndex);
    while (row != null) {
      Cell keyCell = row.getCell(startCol);
      Cell valueCell = row.getCell(startCol + 1);
      keyValueMap.put(keyCell.getStringCellValue(), valueCell.getStringCellValue());
      _currentRowIndex++;
      row = _sheet.getRow(_currentRowIndex);
    }
    _currentRowIndex++;
    return keyValueMap;
  }

  public Map<Pair<String, String>, String> readMatrix(int startRow, int startCol) {



    Map<Pair<String, String>, String> valueMap = new HashMap<>();
    //Maps used to store the index of each x and y axis
    Map<String, Integer> xCol = new HashMap<>();
    Map<String, Integer> yRow = new HashMap<>();

    Row row = _sheet.getRow(startRow);

    for (Cell cell : row) {
     xCol.put(getCellAsString(cell), cell.getColumnIndex());
    }

    //do {
    //  Cell cell;
    //}
    //while (cell != null);

    return valueMap;
  }
}
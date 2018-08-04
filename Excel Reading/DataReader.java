import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class DataReader {

  private static final String FILE_NAME = "Training Data.xlsx";

  public static void main(String[] args) {

    double[][] inputData = new double[36][13];
    double[] desiredOutcome = new double[36];
    try {
      XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
      FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
      XSSFSheet datatypeSheet = workbook.getSheetAt(0);
      Iterator<Row> iterator = datatypeSheet.iterator();
      Row currentRow = iterator.next();
      int dataSet = 0;
      int dataSetValue = 0;

      while (iterator.hasNext()) {

      currentRow = iterator.next();
      Iterator<Cell> cellIterator = currentRow.iterator();
      int i = 0; //make sure date is not put into the data set
      dataSetValue = 0;

      while (cellIterator.hasNext()) {

        Cell currentCell = cellIterator.next();
        if(currentCell.getCellTypeEnum() != CellType.NUMERIC){
          currentCell = cellIterator.next();
        }
        if (currentCell.getCellTypeEnum() == CellType.NUMERIC & i >= 1 & dataSetValue!=13) {
          //put dataset into the array
          inputData[dataSet][dataSetValue] = currentCell.getNumericCellValue();
          dataSetValue++;
        }
        elseif(dataSetValue == 12){
          desiredoutcome[dataSet] = currentCell.getNumericCellValue();
        }
        i++;

      }
      dataSet++;
      System.out.println();

    }
  }
    catch (FileNotFoundException e) {
      e.printStackTrace();
  }
    catch (IOException e) {
      e.printStackTrace();
  }

  }
  }

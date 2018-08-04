import java.util.Iterator;

public class DataReader {

  private static final String FILE_NAME = "Training Data.xlsx";

  public double[][] retreaveData(int dataSets, int datavalues) {

    double[][] inputData = new double[dataSets][datavalues];
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
        if (currentCell.getCellTypeEnum() == CellType.NUMERIC & i >= 1 & dataSetValue!=datavalues) {
          //put dataset into the array
          inputData[dataSet][dataSetValue] = currentCell.getNumericCellValue();
          dataSetValue++;
        }

      }
      dataSet++;


    }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return inputData;

  }
  public double[][] retreaveAnswers(int dataSets, int possibleOutcomes, int dataPoints){
    double[][] answers = new double[dataSets][posssibleOutcomes];
    double[] answersPreAdjust = new double[dataStes];
    try {
      XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
      FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
      XSSFSheet datatypeSheet = workbook.getSheetAt(0);
      Iterator<Row> iterator = datatypeSheet.iterator();
      Row currentRow = iterator.next(); // remove category lables
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
        if (currentCell.getCellTypeEnum() == CellType.NUMERIC & i >= 1 & dataSetValue!=dataPoints) {
          //put dataset into the array
          dataSetValue++;
        }
        if(dataSetValue = dataPoints){
          answersPreAdjust[dataSet] = currentCell.getNumericCellValue();
        }

        }
      dataSet++;
    }
    dataSet = 0;
    for(double i: answersPreAdjust){
      if(i <= -10){
         answers[dataSet][0]= 1;
         answers[dataSet][1]= 0;
         answers[dataSet][2]= 0;
         answers[dataSet][3]= 0;
         answers[dataSet][4]= 0;
         answers[dataSet][5]= 0;
         answers[dataSet][6]= 0;
         answers[dataSet][7]= 0;
      }
      elseif(i<=-5){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 1;
        answers[dataSet][2]= 0;
        answers[dataSet][3]= 0;
        answers[dataSet][4]= 0;
        answers[dataSet][5]= 0;
        answers[dataSet][6]= 0;
        answers[dataSet][7]= 0;
      }
      elseif(i<= -2){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 0;
        answers[dataSet][2]= 1;
        answers[dataSet][3]= 0;
        answers[dataSet][4]= 0;
        answers[dataSet][5]= 0;
        answers[dataSet][6]= 0;
        answers[dataSet][7]= 0;
      }
      elseif(i<= 0){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 0;
        answers[dataSet][2]= 0;
        answers[dataSet][3]= 1;
        answers[dataSet][4]= 0;
        answers[dataSet][5]= 0;
        answers[dataSet][6]= 0;
        answers[dataSet][7]= 0;
      }
      elseif(i <=2){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 0;
        answers[dataSet][2]= 0;
        answers[dataSet][3]= 0;
        answers[dataSet][4]= 1;
        answers[dataSet][5]= 0;
        answers[dataSet][6]= 0;
        answers[dataSet][7]= 0;
      }
      elseif(i<=5){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 0;
        answers[dataSet][2]= 0;
        answers[dataSet][3]= 0;
        answers[dataSet][4]= 0;
        answers[dataSet][5]= 1;
        answers[dataSet][6]= 0;
        answers[dataSet][7]= 0;
      }
      elseif(i<=10){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 0;
        answers[dataSet][2]= 0;
        answers[dataSet][3]= 0;
        answers[dataSet][4]= 0;
        answers[dataSet][5]= 0;
        answers[dataSet][6]= 1;
        answers[dataSet][7]= 0;
      }
      elseif(i>10){
        answers[dataSet][0]= 0;
        answers[dataSet][1]= 0;
        answers[dataSet][2]= 0;
        answers[dataSet][3]= 0;
        answers[dataSet][4]= 0;
        answers[dataSet][5]= 0;
        answers[dataSet][6]= 0;
        answers[dataSet][7]= 1;
      }
    }
    return answers;
  }

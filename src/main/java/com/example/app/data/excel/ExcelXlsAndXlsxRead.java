package com.example.app.data.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.*;

public class ExcelXlsAndXlsxRead {

    public static String[][] getEXlsXlsx(String filename, int sheet) {

        //String excelFilePath = "students.xls"; // can be .xls or .xlsx

        String[][] dataTable = null;

        String path = "src/main/resources/data/"+filename;
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        try {
            FileInputStream inputStream = new FileInputStream(absolutePath);//new File(filename));

            Workbook workbook = getWorkbook(inputStream, filename);

            Sheet firstSheet = workbook.getSheetAt(sheet);
            Iterator<Row> iterator = firstSheet.iterator();

            Cell cell;

            // Get the number of rows and columns
            int numRows = firstSheet.getLastRowNum() + 1;
            int numCols = firstSheet.getRow(0).getLastCellNum();

            // Create double array data table - rows x cols
            // We will return this data table
            dataTable = new String[numRows][numCols];

            int j=0;

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();

                for(int i=0; i<nextRow.getLastCellNum(); i++) {
                    cell = nextRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    if((cell.toString()=="")||(cell.toString()==" "))dataTable[j][i]=cell.toString()+"--";
                    else
                    {
                        switch (cell.getCellType()) {
                            case STRING:
                                dataTable[j][i]=cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                if(cell.getBooleanCellValue())dataTable[j][i]="True"; else dataTable[j][i]="False";
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    //SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                                    //java.util.Date date = sdf1.parse(cell.toString());
                                    Date date = cell.getDateCellValue();
                                    //LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");  // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    if(Integer.parseInt(sdf.format(date))<1990) dataTable[j][i]=sdf1.format(date); else dataTable[j][i]=sdf2.format(date);
                                }else
                                    dataTable[j][i]=String.valueOf(cell.getNumericCellValue());
                                break;
                            case BLANK:
                                dataTable[j][i]="BLANK";
                                break;
                            case ERROR:
                                dataTable[j][i]="ERROR";
                                break;
                            case FORMULA:
                                dataTable[j][i]="FORMULA";
                                break;
                            case _NONE:
                                dataTable[j][i]="_NONE";
                                break;
                            default :
                                dataTable[j][i]="DEFAULT";
                        }
                        //System.out.print(cell.toString()+"\t");
                    }
                }
                System.out.println();
                j++;
            }

            workbook.close();
            inputStream.close();

        } catch (IOException e) {
            System.out.println("ERROR FILE HANDLING " + e.toString());
        }

        return dataTable;
    }



    private static Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)	// switch between xls and xlsx
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

	 /*
	    int[][] foo = new int[][] {
     new int[] { 1, 2, 3 },
     new int[] { 1, 2, 3, 4},
	    };

	    System.out.println(foo.length); //2     // rows number
	    System.out.println(foo[0].length); //3  // colls number
	    System.out.println(foo[1].length); //4
	  */

    public static String getETable(String filename, int sheet) {		// write table in consol

        String[][] dataTable = getEXlsXlsx(filename, sheet);		// read from cells
        String str="";
        int j=1, i=dataTable[0].length;

        for(String[] data : dataTable ){
            for(String str1 : data) {
                if(j==i) {str+=str1+"\n"; j=1;} else {str+=str1+"\t"; j++;};		// write table in consol
            }
        }

        return str;
    }


    public static String getERow(String filename, int sheet, int row) {

        String[][] dataTable = getEXlsXlsx(filename, sheet);		// read from cells -- .xls

        String str="";
        for(int i=0;i<dataTable[row].length;i++) {		// write row in consol
            str+= dataTable[row][i]+"\n";;
        }

        return str;
    }

    public static String getEColumn(String filename, int sheet, int col) {

        String[][] dataTable = getEXlsXlsx(filename, sheet);		// read from cells -- .xls

        String str="";
        for(int i=0;i<dataTable.length;i++) {		// write column in consol
            str+= dataTable[i][col]+"\n";
        }

        return str;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");
    //static ArrayList<String> arrStr = new ArrayList<String>();					//dinamikus tömb létrehozása a változó darabszámú adatok beírásához
    //static ArrayList<Integer> arrInt = new ArrayList<Integer>();
    //static ArrayList<Double> arrDbl = new ArrayList<Double>();

    public static String getEColumnCalcualte(String filename, int sheet, int col1, int col2, double lot, double sl_alap, double veszteseg, double tp_alap, double nyereseg, double spread, int slMettol, int slMeddig, int slLepeskoz, int tpMettol, int tpMeddig, int tpLepeskoz, int mennyiSoron) {

        String[][] dataTable = getEXlsXlsx(filename, sheet);		// read from cells -- .xls
        String str="";

        double egyPontVeszteseg = veszteseg/sl_alap;
        double egyPontNyereseg = nyereseg/tp_alap;

        //megnézzük, mennyi darabszámot kell elmenteni, mekkora legyen a tömbök mérete
        int lepes = 0;
        for(int j=slMettol; j<=slMeddig; j+=slLepeskoz) {			// SL tartományon végigmegyek
            for(int k=tpMettol; k<= tpMeddig; k+=tpLepeskoz) {		// TP tartományon végigmegyek
                lepes++;
            }
        }
        // hátulról mennyi sort vegyen csak az Excel táblából
        int excelSorokSzama = dataTable.length;
        if(mennyiSoron==0) mennyiSoron=excelSorokSzama;
        int honnanMentseEl = excelSorokSzama-mennyiSoron;
        //if(lepes>mennyiSoron) honnanMentseEl = lepes-mennyiSoron;

        double[] doubleSorrend 	= new double[lepes];
        String[] strSorrend		= new String[lepes];
        int[] 	 intSorrend		= new int[lepes];
        int lepes2 = 0;
        // végigmenyünk, és elmentjük a darabszámokat a tömbökbe
        for(int j=slMettol; j<=slMeddig; j+=slLepeskoz) {			// SL tartományon végigmegyek
            for(int k=tpMettol; k<= tpMeddig; k+=tpLepeskoz) {		// TP tartományon végigmegyek

                int dbNullanalKisebb = 0, dbNullanalNagyobb=0;
                double sl = 0, tp=0, ideiglenesAr=0, osszar=0;

                for(int i=0;i<dataTable.length;i++) {		// read column in consol
                    if(i>=honnanMentseEl) {
                        if(isNumeric(dataTable[i][col1])) sl= Double.valueOf(dataTable[i][col1]);
                        if(isNumeric(dataTable[i][col2])) tp= Double.valueOf(dataTable[i][col2]);

                        if(((sl+spread)<j) && (tp>(k+spread))) ideiglenesAr=k*egyPontNyereseg; else ideiglenesAr=j*egyPontVeszteseg; //ideiglenesAr=nyereseg; else ideiglenesAr=veszteseg;

                        osszar += ideiglenesAr;

                        if(ideiglenesAr>0) dbNullanalNagyobb++; else dbNullanalKisebb++;
                    }
                }

                doubleSorrend[lepes2] = osszar;
                strSorrend[lepes2] = "Összár: "+df.format(osszar)+" LOT: "+lot+" SL: "+j+" Veszteség: "+df.format(j*egyPontVeszteseg)+" TP: "+k+" Nyereség: "+df.format(k*egyPontNyereseg)+" Spread: "+spread+" <0: "+dbNullanalKisebb+" >0: "+dbNullanalNagyobb+"\n";
                lepes2++;
            }
        }

        // sorrendbe tesszük az adott elemnek megfelelően

        for(int i= lepes-2; i>0; i--)
            for(int j=0; j<=i; j++)
                if(doubleSorrend[j] > doubleSorrend[j+1])
                {
                    //első tömb
                    double tmp = doubleSorrend[j];
                    doubleSorrend[j] = doubleSorrend[j+1];
                    doubleSorrend[j+1] = tmp;
                    // második tömb
                    String tmp1 = strSorrend[j];
                    strSorrend[j] = strSorrend[j+1];
                    strSorrend[j+1] = tmp1;
                }

        // tömbök kiírása
        for(int i=0; i<lepes; i++) {
            str += strSorrend[i];
        }

	        /*double sl = 0, tp=0, ideiglenesAr=0, osszar=0;
	        int dbNullanalKisebb = 0, dbNullanalNagyobb=0;

	        for(int i=0;i<dataTable.length;i++) {		// read column in consol
	        	if(isNumeric(dataTable[i][col1])) sl= Double.valueOf(dataTable[i][col1]);
	        	if(isNumeric(dataTable[i][col2])) tp= Double.valueOf(dataTable[i][col2]);

	        	if(((sl+spread)<sl_alap) && (tp>(tp_alap+spread))) ideiglenesAr=tp_alap*egyPontNyereseg; else ideiglenesAr=sl_alap*egyPontVeszteseg; //ideiglenesAr=nyereseg; else ideiglenesAr=veszteseg;

	        	osszar += ideiglenesAr;

	        	if(ideiglenesAr>0) dbNullanalNagyobb++; else dbNullanalKisebb++;

	        	str+=ideiglenesAr+"\n";
	        }

	        str+=osszar+"\n"; */


        return str;
    }

    public static int getEColumnSzum(String filename, int sheet, int col) {

        String[][] dataTable = getEXlsXlsx(filename, sheet);		// read from cells -- .xls

        double numb=0;
        for(int i=0;i<dataTable.length;i++) {		// write column in consol
            if(isNumeric(dataTable[i][col])) numb+= Double.valueOf(dataTable[i][col]);   // 1.0 is Double, so convert it from String to Double with Double.valueOf() method
        }

        return (int)numb;					// convert Double numb to Integer
    }

    public static boolean isNumeric(String str) {
        //return str != null && str.matches("[-+]?\\d*\\.?\\d+");
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (str == null || str.length() == 0) {
            return false;
        }

					/*try {
						Double.parseDouble(str);
						return true;

					} catch (NumberFormatException e) {
						return false;
					}*/
				 /*try {
				        double d = Double.parseDouble(str);
				    } catch (NumberFormatException nfe) {
				        return false;
				    }
				    return true; */

        return pattern.matcher(str).matches();

    }

    public static String getESheetNames(String names, int sheet) {

        //FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(names));

            Workbook workbook = getWorkbook(fileInputStream, names);

            // for each sheet in the workbook
         /*for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

             System.out.println("Sheet name: " + workbook.getSheetName(i));
         }*/

            workbook.close();
            return workbook.getSheetName(sheet);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } /*finally {
         if (fileInputStream != null) {
             try {
                 fileInputStream.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     } */
    }



	/* public static void readXLSFileWithBlankCells(String filename, int sheett) {  // good read from xls
			try {
				InputStream ExcelFileToRead = new FileInputStream(filename);
				HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

				HSSFSheet sheet = wb.getSheetAt(sheett);
				HSSFRow row;
				HSSFCell cell;

				Iterator rows = sheet.rowIterator();

				while (rows.hasNext()) {
					row = (HSSFRow) rows.next();

					for(int i=0; i<row.getLastCellNum(); i++) {
						cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						System.out.print(cell.toString()+" ");
					}
					System.out.println();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}*/

}

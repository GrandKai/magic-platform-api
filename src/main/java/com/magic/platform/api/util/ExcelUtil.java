package com.magic.platform.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

/**
 * <!-- Excel 工具类 --> <!-- https://mvnrepository.com/artifact/net.sourceforge.jexcelapi/jxl -->
 * <dependency>
 * <groupId>net.sourceforge.jexcelapi</groupId>
 * <artifactId>jxl</artifactId>
 * <version>2.6.12</version>
 * </dependency>
 * <p>
 * <!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi</artifactId>
 * <version>3.17</version>
 * </dependency>
 * <p>
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi-ooxml</artifactId>
 * <version>3.17</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/cn.afterturn/easypoi-base -->
 * <dependency>
 * <groupId>cn.afterturn</groupId>
 * <artifactId>easypoi-base</artifactId>
 * <version>3.1.0</version>
 * </dependency>
 * <p>
 * <!-- https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils -->
 * <dependency>
 * <groupId>commons-beanutils</groupId>
 * <artifactId>commons-beanutils</artifactId>
 * <version>1.8.3</version>
 * </dependency>
 */
public class ExcelUtil {

  /***
   * 生成导入模板
   * @param sheetName     sheet页名称
   * @param selectList    模板中是否包含下拉框 不包含时可以传null
   * @param headNameList  模板标题数组，格式[["propertyName","headerName"],....] ，使用的是headerName
   * @return
   */
  public static HSSFWorkbook exportTemplate(String sheetName, List<Map> selectList, List<String[]> headNameList) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet(sheetName);

    // 单元格默认样式 ： 居中、并将单元格设置为文本格式
    HSSFCellStyle style = workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.CENTER);
    DataFormat format = workbook.createDataFormat();
    style.setDataFormat(format.getFormat("@"));

    // 表头样式
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setLocked(true);
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    try {
      //填充题头内容
      HSSFRow header = sheet.createRow(0);
      for (int i = 0; i < headNameList.size(); i++) {
        // 设置默认样式以及单元格宽度
        sheet.setDefaultColumnStyle(i, style);
        sheet.setColumnWidth(i, 5500);
        // 为标题设置特定样式并展示value
        Cell cell = header.createCell(i);
        cell.setCellValue(headNameList.get(i)[1]);
        cell.setCellStyle(headerStyle);
      }

      // 判断导入模板中是否含有下拉框
      if (!CollectionUtils.isEmpty(selectList)) {
        for (Map<String, Object> map : selectList) {
          HSSFDataValidation dataValidation = bindSelectItem((Integer) map.get("colNum"), (String[]) map.get("dataArr"), (String) map.get("errorMsg"));
          sheet.addValidationData(dataValidation);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
    return workbook;
  }

  /***
   * 生成导入模板
   * @param sheetName     sheet页名称
   * @param selectList    模板中是否包含下拉框 不包含时可以传null
   * @param headNameList  模板标题数组，格式[["propertyName","headerName"],....] ，使用的是headerName
   * @param  rules 规则
   * @return
   */
  public static HSSFWorkbook exportTemplate(String sheetName, List<Map> selectList, List<String[]> headNameList, List<String[]> rules) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet(sheetName);

    // 单元格默认样式 ： 居中、并将单元格设置为文本格式
    HSSFCellStyle style = workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.CENTER);
    DataFormat format = workbook.createDataFormat();
    style.setDataFormat(format.getFormat("@"));

    // 表头样式
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setLocked(true);
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    try {
      //填充题头内容
      HSSFRow header = sheet.createRow(0);
      for (int i = 0; i < headNameList.size(); i++) {
        // 设置默认样式以及单元格宽度
        sheet.setDefaultColumnStyle(i, style);
        sheet.setColumnWidth(i, 5500);
        // 为标题设置特定样式并展示value
        Cell cell = header.createCell(i);
        cell.setCellValue(headNameList.get(i)[1]);
        cell.setCellStyle(headerStyle);
      }
      //添加校验规则
//            if(!CollectionUtils.isEmpty(rules)){
//                for ( int i = 0; i < rules.size(); i++) {
//                    DataValidation dataValidation = bindRules(sheet, i, rules.get(i)[0], rules.get(i)[1], rules.get(i)[2]);
//                    sheet.addValidationData(dataValidation);
//                }
//            }
      // 判断导入模板中是否含有下拉框
      if (!CollectionUtils.isEmpty(selectList)) {
        for (Map<String, Object> map : selectList) {
          HSSFDataValidation dataValidation = bindSelectItem((Integer) map.get("colNum"), (String[]) map.get("dataArr"), (String) map.get("errorMsg"));
          sheet.addValidationData(dataValidation);
        }
      }

    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
    return workbook;
  }


  /***
   * 生成导出excel
   * @param sheetName     导出sheet页名称
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param list          导出数据list
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static HSSFWorkbook exportExcel(String sheetName, List<String[]> headNameList, List list) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    HSSFWorkbook workbook = new HSSFWorkbook();
    // 设置标题单元格样式
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    // 设置错误提示单元格样式
    HSSFCellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);

    // 创建sheet页
    HSSFSheet sheet = workbook.createSheet(sheetName);
    sheet.setDefaultColumnWidth(15);
    // 填充题头内容(首行)
    HSSFRow header = sheet.createRow(0);
    int i = 0;
    for (String[] name : headNameList) {
      HSSFCell cell = header.createCell(i++);
      cell.setCellValue(name[1]);
      cell.setCellStyle(headerStyle);
    }
    if (!CollectionUtils.isEmpty(list)) {
      // 从第二行开始为数据内容
      int j = 1;
      for (Object bean : list) {
        HSSFRow row = sheet.createRow(j++);
        i = 0;
        for (String[] name : headNameList) {
          HSSFCell cell = row.createCell(i++);
          cell.setCellType(CellType.STRING);
          cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          if ("errorMsg".equals(name[0])) {
            cell.setCellStyle(errorStyle);
          }
        }
      }
    }
    return workbook;
  }


  /***
   * 生成导出excel
   * @param sheetName     导出sheet页名称
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param list          导出数据list
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static HSSFWorkbook exportCustomExcel(String sheetName, List<String[]> headNameList, List list, List<Map<String, Object>> dropDownList) throws Exception {
    HSSFWorkbook workbook = new HSSFWorkbook();
    // 设置标题单元格样式
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    // 设置错误提示单元格样式
    HSSFCellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);

    // 创建sheet页
    HSSFSheet sheet = workbook.createSheet(sheetName);
    sheet.setDefaultColumnWidth(15);
    // 填充题头内容(首行)
    HSSFRow header = sheet.createRow(0);
    int i = 0;
    for (String[] name : headNameList) {
      HSSFCell cell = header.createCell(i++);
      cell.setCellValue(name[1]);
      cell.setCellStyle(headerStyle);
    }
    if (!CollectionUtils.isEmpty(list)) {
      // 从第二行开始为数据内容
      int j = 1;
      for (Object bean : list) {
        HSSFRow row = sheet.createRow(j++);
        i = 0;
        for (String[] name : headNameList) {
          HSSFCell cell = row.createCell(i++);
          cell.setCellType(CellType.STRING);

          // 共享部门
          if ("isShare".equals(name[0])) {
            String isShareKefu = "1".equals(BeanUtils.getProperty(bean, "isShareKefu")) ? "科技客服部 " : "";
            String isShareXiaoshou = "1".equals(BeanUtils.getProperty(bean, "isShareXiaoshou")) ? "销售部 " : "";
            String isShareToupu = "1".equals(BeanUtils.getProperty(bean, "isShareToupu")) ? "投普部 " : "";
            String isShareTouzhi = "1".equals(BeanUtils.getProperty(bean, "isShareTouzhi")) ? "投至部 " : "";

            StringBuilder builder = new StringBuilder();
            builder.append(isShareKefu).append(isShareXiaoshou).append(isShareToupu).append(isShareTouzhi);

            cell.setCellValue(builder.toString());
          } else {
            cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          }
          if ("errorMsg".equals(name[0])) {
            cell.setCellStyle(errorStyle);
          }
        }
      }
    }
    // 判断导入模板中是否含有下拉框
    if (!CollectionUtils.isEmpty(dropDownList)) {
      for (Map<String, Object> map : dropDownList) {
        HSSFDataValidation dataValidation = bindSelectItem((Integer) map.get("colNum"), (String[]) map.get("dataArr"), (String) map.get("errorMsg"));
        sheet.addValidationData(dataValidation);
      }
    }
    return workbook;
  }

  /***
   * 生成导出excel
   * @param sheetName     导出sheet页名称
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param list          导出数据list
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static HSSFWorkbook exportErrorExcel(String sheetName, List<String[]> headNameList, List list, List<String[]> rules) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    HSSFWorkbook workbook = new HSSFWorkbook();
    // 设置标题单元格样式
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    // 设置错误提示单元格样式
    HSSFCellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);

    // 创建sheet页
    HSSFSheet sheet = workbook.createSheet(sheetName);
    sheet.setDefaultColumnWidth(15);
    // 填充题头内容(首行)
    HSSFRow header = sheet.createRow(0);
    int i = 0;
    for (String[] name : headNameList) {
      HSSFCell cell = header.createCell(i++);
      cell.setCellValue(name[1]);
      cell.setCellStyle(headerStyle);
    }
    if (!CollectionUtils.isEmpty(list)) {
      // 从第二行开始为数据内容
      int j = 1;
      for (Object bean : list) {
        HSSFRow row = sheet.createRow(j++);
        i = 0;
        for (String[] name : headNameList) {
          HSSFCell cell = row.createCell(i++);
          cell.setCellType(CellType.STRING);
          cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          if ("errorMsg".equals(name[0])) {
            cell.setCellStyle(errorStyle);
          }
        }
      }
    }
    //添加校验规则
//        if(!CollectionUtils.isEmpty(rules)){
//            for ( int k = 0; k < rules.size(); k++) {
//                DataValidation dataValidation = bindRules(sheet, k, rules.get(k)[0], rules.get(k)[1], rules.get(k)[2]);
//                sheet.addValidationData(dataValidation);
//            }
//        }
    return workbook;
  }

  /**
   * excel转为inputStream
   */
  public static InputStream workBookInputStream(Workbook workbook) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      workbook.write(os);
    } catch (IOException e) {
      e.printStackTrace();
    }
    byte[] content = os.toByteArray();
    InputStream is = new ByteArrayInputStream(content);
    os.close();
    return is;
  }

  /***
   * 绑定输出下拉框
   * @param colNum    第colNum列为下拉框
   * @param dataArr   下拉框数据
   * @param errorMsg  错误提示
   * @return
   */
  private static HSSFDataValidation bindSelectItem(int colNum, String[] dataArr, String errorMsg) {
    // 设置第 1-50000 列的 第colNum 行为下拉列表
    CellRangeAddressList regions = new CellRangeAddressList(1, 50000, colNum, colNum);
    // 创建下拉列表数据
    DVConstraint constraint = DVConstraint.createExplicitListConstraint(dataArr);
    // 绑定
    HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
    // dataValidation.createPromptBox("标签名称不能超过10个文字", "标签名称不能超过10个文字");
    dataValidation.createErrorBox("操作提示", errorMsg);
    dataValidation.setShowErrorBox(true);
    dataValidation.setShowPromptBox(true);
    dataValidation.setEmptyCellAllowed(false);
    return dataValidation;
  }

  /***
   * 限定单元格输入范围
   * @param sheet     sheet页对象
   * @param colNum    对第colNum列数据进行限制
   * @param errorMsg  错误提示信息
   * @param min       输入范围最小值
   * @param max       输入范围最大值
   *
   *  // 填充校验提示 使用样例
   *  DataValidation dstDataValidation = bindRules(sheet, 1, "姓名不能超过20个文字或字符", "0", "20");
   *  sheet.addValidationData(dstDataValidation);
   * @return
   */
  private static DataValidation bindRules(HSSFSheet sheet, int colNum, String errorMsg, String min, String max) {
    DataValidationHelper helper = sheet.getDataValidationHelper();
    CellRangeAddressList dstAddrList = new CellRangeAddressList(1, 50000, colNum, colNum);// 限定规则单元格范围
    DataValidationConstraint dvc = helper.createNumericConstraint(DVConstraint.ValidationType.TEXT_LENGTH, DVConstraint.OperatorType.BETWEEN, min, max);
    DataValidation dstDataValidation = helper.createValidation(dvc, dstAddrList);
    dstDataValidation.createErrorBox("操作提示", errorMsg);
    dstDataValidation.setEmptyCellAllowed(true);
    dstDataValidation.setShowErrorBox(true);
    return dstDataValidation;
  }


  /***
   * 向指定excel文件写入内容
   * @param sheetIndex    基础数据Sheet页编号
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param list          导出数据list
   * @param targetFile    写入数据的目标文件
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static Workbook exportToFile(Integer sheetIndex, List<String[]> headNameList, List list, File targetFile) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
    Workbook workbook = getWorkbok(targetFile);
    // 设置标题单元格样式
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    Font font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    // 设置错误提示单元格样式
    CellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);

    // 获取基础数据sheet页
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    sheet.setDefaultColumnWidth(15);
    // 填充题头内容(首行)
    Row header = sheet.createRow(0);
    int i = 0;
    for (String[] name : headNameList) {
      Cell cell = header.createCell(i++);
      cell.setCellValue(name[1]);
      cell.setCellStyle(headerStyle);
    }
    if (!CollectionUtils.isEmpty(list)) {
      // 从第二行开始为数据内容
      int j = 1;
      for (Object bean : list) {
        Row row = sheet.createRow(j++);
        i = 0;
        for (String[] name : headNameList) {
          Cell cell = row.createCell(i++);
          cell.setCellType(CellType.STRING);
          cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          if ("errorMsg".equals(name[0])) {
            cell.setCellStyle(errorStyle);
          }
        }
      }
    }
    for (int n = workbook.getNumberOfSheets(); n > 0; n--) {
      workbook.getSheetAt(n - 1).setForceFormulaRecalculation(true);
    }
    return workbook;
  }

  /***
   * 向指定excel文件写入内容
   * @param sheetIndex    基础数据Sheet页编号
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param list          导出数据list
   * @param targetFile    写入数据的目标文件
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static Workbook exportToFile2(Workbook workbook, Integer sheetIndex, List<String[]> headNameList, List list, File targetFile) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
    // 设置标题单元格样式
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    Font font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    // 设置错误提示单元格样式
    CellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);

    // 获取基础数据sheet页
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    sheet.setDefaultColumnWidth(15);
    // 填充题头内容(首行)
    Row header = sheet.createRow(0);
    int i = 0;
    for (String[] name : headNameList) {
      Cell cell = header.createCell(i++);
      cell.setCellValue(name[1]);
      cell.setCellStyle(headerStyle);
    }
    if (!CollectionUtils.isEmpty(list)) {
      // 从第二行开始为数据内容
      int j = 1;
      for (Object bean : list) {
        Row row = sheet.createRow(j++);
        i = 0;
        for (String[] name : headNameList) {
          Cell cell = row.createCell(i++);
          cell.setCellType(CellType.STRING);
          cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          if ("errorMsg".equals(name[0])) {
            cell.setCellStyle(errorStyle);
          }
        }
      }
    }
    for (int n = workbook.getNumberOfSheets(); n > 0; n--) {
      workbook.getSheetAt(n - 1).setForceFormulaRecalculation(true);
    }
    return workbook;
  }

  /***
   * 向指定excel文件写入内容
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param dataMap
   * @param targetFile    写入数据的目标文件
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static Workbook exportToFileAsMonth(List<String[]> headNameList, Map<String, List<Map<String, Object>>> dataMap, File targetFile) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
    Workbook workbook = getWorkbok(targetFile);
    // 设置标题单元格样式
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    Font font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);
    headerStyle.setFillBackgroundColor(IndexedColors.AQUA.index);

    // 设置错误提示单元格样式
    CellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);
    int sheetNumbers = workbook.getNumberOfSheets();
    int sheetIndex = 0;
    for (Map.Entry<String, List<Map<String, Object>>> entry : dataMap.entrySet()) {
      // 获取基础数据sheet页
      String sheetName = entry.getKey().lastIndexOf("_") > 0 ? entry.getKey().substring(0, entry.getKey().lastIndexOf("_")) : entry.getKey();
      Sheet sheet = workbook.getSheetAt(sheetIndex);
      sheet.setDefaultColumnWidth(5);
      workbook.setSheetName(sheetIndex, sheetName);
      sheetIndex++;

      List<Map<String, Object>> dataList = entry.getValue();
      int rowIndex = 10;
      for (int i = 0; i < dataList.size(); i++) {
        List<String[]> headNameList_copy = new ArrayList<>(headNameList);
        headNameList_copy.add(0, new String[]{"title", (String) dataList.get(i).get("title")});
        rowIndex = fillTheBlank(rowIndex, sheet, headNameList_copy, headerStyle, dataList.get(i));
      }
    }

    for (int temp = sheetNumbers - 1; temp >= sheetIndex; temp--) {
      workbook.removeSheetAt(temp);
    }
    for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
      workbook.getSheetAt(index).setForceFormulaRecalculation(true);
    }

    return workbook;
  }

  /***
   *
   * @param rowIndex      创建的题头列序号
   * @param sheet         操作的sheet对象
   * @param headNameList  头信息以及属性
   * @param headerStyle   头样式
   * @param dataMap      数据内容
   * @return 返回填充信息后的头序列号
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static int fillTheBlank(int rowIndex, Sheet sheet, List<String[]> headNameList, CellStyle headerStyle, Map<String, Object> dataMap) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    List dataList = (List) dataMap.get("dataList");
    String itemName = (String) dataMap.get("title");

    // 填充题头内容(首行)
    Row header = sheet.createRow(rowIndex);

    int cellIndex1 = 0;
    for (String[] name : headNameList) {
      Cell cell = header.createCell(cellIndex1++);
      cell.setCellValue(name[1]);
      cell.setCellStyle(headerStyle);
    }
    if (!CollectionUtils.isEmpty(dataList)) {
      // 从第二行开始为数据内容
      int j = rowIndex + 1;
      for (Object bean : dataList) {
        Row row = sheet.createRow(j++);
        int cellIndex2 = 0;
        for (String[] name : headNameList) {
          Cell cell = row.createCell(cellIndex2++);
          cell.setCellType(CellType.NUMERIC);
          if (StringUtils.isNumeric(BeanUtils.getProperty(bean, name[0]))) {
            if (itemName.contains("二访率") || itemName.contains("成交比")) {
              cell.setCellValue(Double.parseDouble(BeanUtils.getProperty(bean, name[0])));
            } else {
              cell.setCellValue(Double.parseDouble(BeanUtils.getProperty(bean, name[0])));
            }
          } else {
            cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          }
          if (name[0].equals("leaderStaffNumber")) {
            if (BeanUtils.getProperty(bean, "staffNumber").equals(BeanUtils.getProperty(bean, "leaderStaffNumber"))) {
              cell.setCellType(CellType.STRING);
              cell.setCellValue(BeanUtils.getProperty(bean, "dutyName") == null ? "队长" : BeanUtils.getProperty(bean, "dutyName"));
            } else {
              cell.setCellValue("");
            }
          }
        }
      }
    }
    return CollectionUtils.isEmpty(dataList) ? rowIndex + 1 : dataList.size() + rowIndex + 1;
  }

  /**
   * 判断Excel的版本,获取Workbook
   */
  public static Workbook getWorkbok(File file) throws IOException {
    Workbook wb = null;
    FileInputStream in = new FileInputStream(file);
    if (file.getName().endsWith("xls")) {     //Excel&nbsp;2003
      wb = new HSSFWorkbook(in);
    } else if (file.getName().endsWith("xlsx")) {    // Excel 2007/2010
      wb = new XSSFWorkbook(in);
    }
    return wb;
  }

  /**
   * 复制文件
   *
   * @param srcPath 源文件绝对路径
   * @param destDir 目标文件所在目录
   * @return destPath 复制文件的文件路径
   */
  public static File copyFile(String srcPath, String destDir) {
    boolean flag = false;

    File srcFile = new File(srcPath);
    if (!srcFile.exists()) { // 源文件不存在
      System.out.println("源文件不存在");
      return null;
    }
    // 获取待复制文件的文件名(源文件+时间戳)
    Calendar calendar = Calendar.getInstance();
    String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator), srcPath.lastIndexOf(".")) + calendar.getTimeInMillis() + srcPath.substring(srcPath.lastIndexOf("."));
    String destPath = destDir + fileName;
    if (destPath.equals(srcPath)) { // 源文件路径和目标文件路径重复
      System.out.println("源文件路径和目标文件路径重复!");
      return null;
    }
    File destFile = new File(destPath);
    if (destFile.exists() && destFile.isFile()) { // 该路径下已经有一个同名文件
      System.out.println("目标目录下已有同名文件!");
      return null;
    }

    File destFileDir = new File(destDir);
    destFileDir.mkdirs();
    try {
      FileInputStream fis = new FileInputStream(srcPath);
      FileOutputStream fos = new FileOutputStream(destFile);
      byte[] buf = new byte[1024];
      int c;
      while ((c = fis.read(buf)) != -1) {
        fos.write(buf, 0, c);
      }
      fis.close();
      fos.close();

      flag = true;
    } catch (IOException e) {
      System.out.println(e);
    }

    if (flag) {
      System.out.println("复制文件成功!");
      return new File(destPath);
    }

    return null;
  }

  /***
   * 获取项目中的模板文件
   * @param path
   * @return
   */
  public static File getTemplateFile(String path) {
    String projectPath = ExcelUtil.class.getResource("/").getPath();
    File file = new File(projectPath + path);
    return file;
  }


  /***
   * 向指定excel文件写入内容
   * @param headNameList  标题数据，格式为：[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
   * @param list      list中元素为 Map  包含两个属性 name ， list
   * @param targetFile    写入数据的目标文件
   * @return
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static Workbook exportToFileAsMonth_XS(List<String[]> headNameList, List<Map> list, File targetFile) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
    Workbook workbook = getWorkbok(targetFile);
    // 设置标题单元格样式
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    Font font = workbook.createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    headerStyle.setFont(font);

    // 设置错误提示单元格样式
    CellStyle errorStyle = workbook.createCellStyle(); // 样式对象
    font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    errorStyle.setFont(font);
    workbook.getNumberOfSheets();
    int sheetIndex = 0;
    for (Map map : list) {
      // 获取基础数据sheet页
      workbook.setSheetName(sheetIndex, (String) map.get("name"));
      Sheet sheet = workbook.getSheetAt(sheetIndex);
      sheet.setDefaultColumnWidth(15);
      sheetIndex++;
      // 填充题头内容(首行)
      Row header = sheet.getRow(0);
      int i = 0;
      for (String[] name : headNameList) {
        Cell cell = header.getCell(i++);
        cell.setCellValue(name[1]);
        cell.setCellStyle(headerStyle);
      }
      // 日期为key对应的当日员工基础数据
      List dataList = (List) map.get("list");
      if (CollectionUtils.isEmpty(dataList)) {
        continue;
      }
      if (!CollectionUtils.isEmpty(dataList)) {
        // 从第二行开始为数据内容
        int j = 1;
        for (Object bean : dataList) {
          Row row = sheet.getRow(j++);
          i = 0;
          for (String[] name : headNameList) {
            Cell cell = row.createCell(i++);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(BeanUtils.getProperty(bean, name[0]));
          }
        }
      }
    }

    for (int temp = workbook.getNumberOfSheets() - 1; temp >= sheetIndex; temp--) {
      workbook.removeSheetAt(temp);
    }
    for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
      workbook.getSheetAt(index).setForceFormulaRecalculation(true);
    }

    return workbook;
  }

}

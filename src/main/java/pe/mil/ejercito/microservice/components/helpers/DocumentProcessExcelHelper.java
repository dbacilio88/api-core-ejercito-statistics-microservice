package pe.mil.ejercito.microservice.components.helpers;

import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;
import pe.mil.ejercito.microservice.dtos.response.GenerateDocumentResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * DocumentProcessTxtHelper
 * <p>
 * DocumentProcessTxtHelper class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 21/03/2024
 */

@Log4j2
@UtilityClass
public class DocumentProcessExcelHelper {

    public static Mono<GenerateDocumentComposition> doOnReadDocumentExcel(final GenerateDocumentComposition documentComposition) {
        return doOnCreateWorkbook(documentComposition)
                .publishOn(Schedulers.boundedElastic())
                .doOnTerminate(() -> {
                    if (Files.exists(documentComposition.getPathFile())) {
                        try {
                            Files.delete(documentComposition.getPathFile());
                        } catch (IOException e) {
                            String message = "Error al eliminar el archivo temporal 'temp.xlsx'";
                            log.error(message, e);
                            throw new CommonException(message, ResponseEnum.INTERNAL_SERVER_ERROR, null);
                        }
                    }
                })
                .doOnSuccess(success -> log.debug("doOnReadDocumentExcel"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnCreateWorkbook(final GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    try {
                        GenerateDocumentResponse documentResponse = new GenerateDocumentResponse();
                        Workbook workbook = WorkbookFactory.create(documentComposition.getFile());
                        int numberOfSheets = workbook.getNumberOfSheets();
                        for (int i = 0; i < numberOfSheets; i++) {
                            Sheet sheet = workbook.getSheetAt(i);
                            processSheetCombined(sheet, documentResponse, currentComposition);
                        }
                        workbook.close();
                        documentResponse.setFileName(currentComposition.getFileName());
                        currentComposition.setDocumentResponse(documentResponse);
                        return Mono.just(currentComposition);
                    } catch (IOException e) {
                        return Mono.error(() -> new CommonException("error of creation file excel process read", ResponseEnum.INTERNAL_SERVER_ERROR, null));
                    }
                })
                .doOnSuccess(success -> log.debug("doOnCreateWorkbook"))
                .doOnError(throwable -> log.error(throwable.getMessage()));

    }

    private void processSheetCombined(final Sheet sheet,
                                      final GenerateDocumentResponse documentResponse,
                                      final GenerateDocumentComposition documentComposition) {
        Set<String> headers = new HashSet<>();
        processExcel(sheet, documentResponse, documentComposition, headers, false, false);
        documentResponse.setHeaderCombined(convertirSetAString(headers));

    }

    private void processExcel(final Sheet sheet,
                              final GenerateDocumentResponse documentResponse,
                              final GenerateDocumentComposition documentComposition,
                              final Set<String> headers, final boolean isCombinedRow,
                              final boolean isCombinedCell) {

        int rowNum = 0;

        int combinedCount = countCellCombined(sheet, documentComposition.getCombinedCellNumber());
        do {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                for (Cell cell : row) {
                    if (isCellCombined(sheet, rowNum, cell.getColumnIndex())) {
                        String value = getCellCombinedValue(sheet, cell);
                        headers.add(value);
                    } else {
                        String value = getCellValueAsString(cell);
                        headers.add(value);
                    }

                }
            }
            rowNum++;
        } while (rowNum < combinedCount);

        int elements = sheet.getPhysicalNumberOfRows() - rowNum;
        List<String[]> list = new ArrayList<>();


        for (; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {

            Row row = sheet.getRow(rowNum);
            if (row != null) {
                int cellNum = 0;
                String[] cellData = new String[row.getPhysicalNumberOfCells()];
                for (Cell cell : row) {
                    if (isCellCombined(sheet, rowNum, cell.getColumnIndex())) {
                        cellData[cellNum] = getCellCombinedValue(sheet, cell);
                    } else {
                        cellData[cellNum] = getCellValueAsString(cell);
                    }
                    cellNum++;
                }
                list.add(cellData);
            }
        }
        documentResponse.setRowData(list);
    }


    private String getCellValueAsString(Cell cell) {
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    cellValue = String.valueOf(processNumericCell(cell));
                    break;
                case STRING:
                    cellValue = processStringCell(cell);
                    break;
                default:
                    processDefaultCell(cell);
                    cellValue = "";
            }
        }
        return cellValue;
    }

    private double processNumericCell(Cell cell) {
        return cell.getNumericCellValue();
    }

    private String processStringCell(Cell cell) {
        return cell.getStringCellValue();
    }

    private void processDefaultCell(Cell cell) {
        log.debug("no data o cell type blank {}", cell);

    }

    private String getCellCombinedValue(Sheet sheet, Cell cell) {
        int rowNum = cell.getRowIndex();
        int colNum = cell.getColumnIndex();
        for (CellRangeAddress rangeAddress : sheet.getMergedRegions()) {
            if (rangeAddress.isInRange(rowNum, colNum)) {
                Row row = sheet.getRow(rangeAddress.getFirstRow());
                Cell cellCombined = row.getCell(rangeAddress.getFirstColumn());
                return cellCombined.getStringCellValue();
            }
        }
        return null;
    }


    private int countCellCombined(Sheet sheet, int rows) {
        int count = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (isCellCombined(sheet, row.getRowNum(), cell.getColumnIndex())) {
                    count++;
                    if (count == rows) {
                        return count;
                    }
                }
            }
        }
        return 1;
    }

    private boolean isCellCombined(Sheet sheet, int rowNumber, int collNumber) {
        for (CellRangeAddress rangeAddress : sheet.getMergedRegions()) {
            if (rangeAddress.isInRange(rowNumber, collNumber)) {
                return true;
            }
        }
        return false;
    }

    private String convertirSetAString(Set<String> set) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}



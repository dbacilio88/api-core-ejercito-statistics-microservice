package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.dto.GenericResponse;
import com.bxcode.tools.loader.dto.ProcessResponse;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pe.mil.ejercito.microservice.dtos.ExcelDataDto;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;
import pe.mil.ejercito.microservice.dtos.response.GenerateDocumentResponse;
import pe.mil.ejercito.microservice.services.contracts.IGenerateDocumentService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * GenerateDocumentService
 * <p>
 * GenerateDocumentService class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BACSYSTEM APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author Bacsystem
 * @author bacsystem.sac@gmail.com
 * @since 19/03/2024
 */

@Log4j2
@Service
public class GenerateDocumentService extends ReactorServiceBase implements IGenerateDocumentService {
    protected GenerateDocumentService() {
        super("GenerateDocumentService");
    }

    @Override
    public Mono<ProcessResponse> doOnFindConfigurationsFiles(GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(this::doOnValidateExtension)
                .flatMap(this::doOnGenerateTemporalDocument)
                .flatMap(this::doOnProcessCreateDocument)
                .flatMap(this::doOnExecuteProcess)
                .flatMap(this::doOnProcessResponse)
                .doOnSuccess(success -> log.debug("MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnExecuteProcess(GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    switch (currentComposition.getContentType()) {
                        case "text":
                            return doOnProcessDocumentTxt(documentComposition);
                        case "application":
                            return doOnProcessDocumentExcel(documentComposition);
                        default:
                            return Mono.just(currentComposition);
                    }
                }).doOnSuccess(success -> log.debug("doOnExecuteProcess"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<GenerateDocumentComposition> doOnValidateExtension(final GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 1 do on validate extension");
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    currentComposition.setContentType(Objects.requireNonNull(currentComposition.getFilePart().headers().getContentType()).getType());
                    currentComposition.setFileName(currentComposition.getFilePart().filename());
                    return Mono.just(currentComposition);
                }).doOnSuccess(success -> log.debug("doOnValidateExtension"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnGenerateTemporalDocument(final GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 2 do on generate temporal document");
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    switch (currentComposition.getContentType()) {
                        case "text":
                            currentComposition.setTempFile("temp.txt");
                            break;
                        case "application":
                            currentComposition.setTempFile("temp.xlsx");
                            break;
                        default:
                            currentComposition.setContentType("temp.csv");
                    }

                    return Mono.just(currentComposition);
                }).doOnSuccess(success -> log.debug("doOnGenerateTemporalDocument"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnProcessCreateDocument(final GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 3 do on process create document");
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    Path outputPath = Paths.get(currentComposition.getTempFile());
                    File tempFile = outputPath.toFile();
                    return currentComposition.getFilePart().transferTo(tempFile).thenReturn(tempFile)
                            .flatMap(file -> {
                                currentComposition.setFile(file);
                                currentComposition.setPathFile(outputPath);
                                return Mono.just(currentComposition);
                            });
                })
                .doOnSuccess(success -> log.debug("doOnProcessCreateDocument"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnProcessDocumentExcel(final GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    try {
                        XSSFWorkbook workbook = new XSSFWorkbook(documentComposition.getFile());
                        int numberOfSheets = workbook.getNumberOfSheets();
                        List<ExcelDataDto> list = new ArrayList<>();

                        for (int n = 0; n < numberOfSheets; n++) {
                            XSSFSheet sheet = workbook.getSheetAt(n);
                            processSheet(sheet, list);
                        }

                        workbook.close();
                        GenerateDocumentResponse documentResponse = new GenerateDocumentResponse();
                        documentResponse.setName(currentComposition.getFileName());
                        documentResponse.setExcel(list);
                        currentComposition.setDocumentResponse(documentResponse);
                        return Mono.just(currentComposition);
                    } catch (IOException | InvalidFormatException e) {
                        return Mono.error(() -> new CommonException("IOException 2", ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnTerminate(() -> {
                    if (Files.exists(documentComposition.getPathFile())) {
                        try {
                            Files.delete(documentComposition.getPathFile());
                        } catch (IOException e) {
                            String message = "Error al eliminar el archivo temporal 'temp.xlsx'";
                            log.error(message, e);
                            throw new CommonException(message, ResponseEnum.INTERNAL_SERVER_ERROR);
                        }
                    }
                })

                .doOnSuccess(success -> log.debug("doOnProcessDocumentExcel"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private void processSheet(XSSFSheet sheet, List<ExcelDataDto> list) {

        int numberOfRows = sheet.getLastRowNum();
        for (int rowIndex = 0; rowIndex <= numberOfRows; rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            ExcelDataDto dto = new ExcelDataDto();
            if (row != null) {
                if (isHeaderRow(rowIndex)) {
                    dto.setName(row.getCell(0).getStringCellValue());
                    dto.setEdad(row.getCell(1).getStringCellValue());
                    processHeaderRow(row);
                } else {
                    dto.setName(row.getCell(0).getStringCellValue());
                    dto.setEdad(String.valueOf(row.getCell(1).getNumericCellValue()));
                    processDataRow(row);
                }
                list.add(dto);
            }
        }
    }

    private boolean isHeaderRow(int rowIndex) {
        // Aquí puedes implementar la lógica para determinar si la fila es la cabecera
        // Por ejemplo, puedes verificar si la fila está en la primera posición (index 0)
        return rowIndex == 0;
    }

    private void processHeaderRow(XSSFRow row) {
        int numberOfCells = row.getLastCellNum();
        for (int cellIndex = 0; cellIndex < numberOfCells; cellIndex++) {
            XSSFCell cell = row.getCell(cellIndex);
            processHeaderCell(cell);
        }
    }

    private void processHeaderCell(XSSFCell cell) {
        if (cell != null && cell.getCellType() == CellType.STRING) {
            log.debug("Header cell value: {}", cell.getStringCellValue());
        }
    }

    private void processDataRow(XSSFRow row) {
        int numberOfCells = row.getLastCellNum();
        for (int cellIndex = 0; cellIndex < numberOfCells; cellIndex++) {
            XSSFCell cell = row.getCell(cellIndex);
            processCell(cell);
        }
    }

    private void processCell(XSSFCell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    processNumericCell(cell);
                    break;
                case STRING:
                    processStringCell(cell);
                    break;
                default:
                    processDefaultCell(cell);
                    break;
            }
        }
    }

    private void processNumericCell(XSSFCell cell) {
        log.debug("# number cell {}", cell.getNumericCellValue());
    }

    private void processStringCell(XSSFCell cell) {
        log.debug("# string cell {}", cell.getStringCellValue());
    }

    private void processDefaultCell(XSSFCell cell) {
        log.debug("no data {}", cell);
    }


    private Mono<GenerateDocumentComposition> doOnProcessDocumentTxt(final GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    currentComposition.setTempFile("temp.txt");
                    return Mono.just(currentComposition);
                }).doOnSuccess(success -> log.debug("doOnProcessDocumentExcel"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    public Mono<ProcessResponse> doOnProcessResponse(GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 4 do on process response");
        return Mono.just(documentComposition)
                .flatMap(processResponse -> {
                    final GenericResponse<GenerateDocumentResponse> response = new GenericResponse<>(processResponse.getDocumentResponse());
                    return Mono.just(ProcessResponse.success(response));
                })
                .doOnSuccess(success -> log.debug("doOnProcessResponse"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

}



package pe.mil.ejercito.microservice.components.enums;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ExtensionType
 * <p>
 * ExtensionType enum.
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
@Getter
public enum ExtensionType {

    EXCEL_XLSX("xlsx", "temp.xlsx"),
    WORD("", ""),
    PPT("", ""),
    PDF("", ""),
    TXT("", ""),
    CSV("", "");
    private final String code;
    private final String value;

    private static final Map<String, ExtensionType> MAP_ENUM = new HashMap<>();

    ExtensionType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    static {
        for (ExtensionType et : EnumSet.allOf(ExtensionType.class)) {
            MAP_ENUM.put(et.getCode(), et);
        }
    }

    public static Optional<ExtensionType> findByValue(String code) {
        log.info("value {}-{}", code, StringUtils.isNoneBlank(code));
        return StringUtils.isNoneBlank(code) ? Optional.of(MAP_ENUM.get(code)) : Optional.empty();
    }

}

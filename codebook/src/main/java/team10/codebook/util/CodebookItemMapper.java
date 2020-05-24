package team10.codebook.util;

import team10.codebook.models.*;
import team10.codebook.models.dto.CodebookItemDTO;

public class CodebookItemMapper {
    public static CodebookItemDTO toDTO(Brand item) {
        CodebookItemDTO retVal = new CodebookItemDTO();
        retVal.setId(item.getId());
        retVal.setName(item.getName());
        return retVal;
    }
    public static CodebookItemDTO toDTO(Model item) {
        CodebookItemDTO retVal = new CodebookItemDTO();
        retVal.setId(item.getId());
        retVal.setName(item.getName());
        return retVal;
    }
    public static CodebookItemDTO toDTO(CarClass item) {
        CodebookItemDTO retVal = new CodebookItemDTO();
        retVal.setId(item.getId());
        retVal.setName(item.getName());
        return retVal;
    }
    public static CodebookItemDTO toDTO(Fuel item) {
        CodebookItemDTO retVal = new CodebookItemDTO();
        retVal.setId(item.getId());
        retVal.setName(item.getType());
        return retVal;
    }
    public static CodebookItemDTO toDTO(Transmission item) {
        CodebookItemDTO retVal = new CodebookItemDTO();
        retVal.setId(item.getId());
        retVal.setName(item.getType());
        return retVal;
    }
}

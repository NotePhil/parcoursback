package cmr.notep.utile.serialiser;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JacksonHelper {
    public static ObjectMapper objectMapper = addDefaultPrettyPrinterToObjectMapper();
    private static ObjectMapper addDefaultPrettyPrinterToObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter dpp = new DefaultPrettyPrinter();
        dpp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        dpp.indentObjectsWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.setDefaultPrettyPrinter(dpp);
    }
    public static <T> T objetFromJson(String json, Class<T> clazz) throws ParcoursException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR ,"erreur lors du parsing " + json,e);
        }
    }
    public static String marshall(Object source) throws ParcoursException {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (IOException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR ,"erreur lors du parsing ",e);
        }
    }

    public static <T> List<T> unMarshallList(String source, Class<T> clazz) throws ParcoursException {
        try {
            return objectMapper.readValue(source, new TypeReference<List<T>>() {});
        } catch (IOException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR ,"erreur lors du parsing ",e);
        }
    }
}

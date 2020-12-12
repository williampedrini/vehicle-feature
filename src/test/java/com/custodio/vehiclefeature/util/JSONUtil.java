package com.custodio.vehiclefeature.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;

/**
 * Class responsible for manipulating the data related to a JSON object.
 */
public final class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(NON_EMPTY);
        MAPPER.setVisibility(MAPPER.getVisibilityChecker().withFieldVisibility(ANY));
    }

    private JSONUtil() {}

    /**
     * Read a certain JSON file and map its value to a certain bean type.
     *
     * @param path The of the file to be read.
     * @param type The {@link JavaType} used for the conversion.
     * @return The representation of the file content as a bean.
     */
    public static <T> T fileToBean(final String path, final Class<T> type) {
        try {
            final var file = new ClassPathResource(path).getFile();
            return MAPPER.readValue(file, defaultInstance().constructType(type));
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Read a certain JSON file and map its value to a certain bean type.
     *
     * @param path The of the file to be read.
     * @param type The {@link TypeReference} used for the conversion.
     * @return The representation of the file content as a bean.
     */
    public static <T> T fileToBean(final String path, final TypeReference<T> type) {
        try {
            final var file = new ClassPathResource(path).getFile();
            return MAPPER.readValue(file, defaultInstance().constructType(type));
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }
}

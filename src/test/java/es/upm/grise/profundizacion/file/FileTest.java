package es.upm.grise.profundizacion.file;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.exceptions.InvalidContentException;
import es.upm.grise.profundizacion.exceptions.WrongFileTypeException;

public class FileTest {

    @Test
    void addProperty_appendsContent() throws Exception {
        File file = new File();
        file.setType(FileType.PROPERTY);

        file.addProperty("A=B".toCharArray());

        assertEquals(List.of('A', '=', 'B'), file.getContent());
    }

    @Test
    void addProperty_null_throwsInvalidContentException() {
        File file = new File();
        file.setType(FileType.PROPERTY);

        InvalidContentException ex = assertThrows(
                InvalidContentException.class,
                () -> file.addProperty(null)
        );
        assertEquals(InvalidContentException.class, ex.getClass());
    }

    @Test
    void addProperty_image_throwsWrongFileTypeException() {
        File file = new File();
        file.setType(FileType.IMAGE);

        WrongFileTypeException ex = assertThrows(
                WrongFileTypeException.class,
                () -> file.addProperty("A=B".toCharArray())
        );
        assertEquals(WrongFileTypeException.class, ex.getClass());
    }

    @Test
    void getCRC32_empty_returnsZero() {
        File file = new File();

        assertEquals(0L, file.getCRC32());
    }
}

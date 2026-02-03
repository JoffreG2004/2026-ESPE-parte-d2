Aquí tienes una propuesta de configuración de pruebas JUnit y casos para addProperty() y getCRC32() según la especificación. No tengo herramientas de edición habilitadas, así que te dejo los cambios en bloques.

Actualiza las dependencias en pom.xml:
<!-- ...existing code... -->
  <dependencies>
    <!-- ...existing code... -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-engine</artifactId>
      <version>5.13.4</version>
      <scope>test</scope>
    </dependency>
    <!-- ...existing code... -->
  </dependencies>
<!-- ...existing code... -->

Reemplaza el contenido de FileTest.java por:

package es.upm.grise.profundizacion.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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

        assertThrows(InvalidContentException.class, () -> file.addProperty(null));
    }

    @Test
    void addProperty_image_throwsWrongFileTypeException() {
        File file = new File();
        file.setType(FileType.IMAGE);

        assertThrows(WrongFileTypeException.class, () -> file.addProperty("A=B".toCharArray()));
    }

    @Test
    void addProperty_appendsToExistingContent() throws Exception {
        File file = new File();
        file.setType(FileType.PROPERTY);

        file.addProperty("A=B".toCharArray());
        file.addProperty(";C=D".toCharArray());

        assertEquals(List.of('A', '=', 'B', ';', 'C', '=', 'D'), file.getContent());
    }

    @Test
    void getCRC32_empty_returnsZero() {
        File file = new File();

        assertEquals(0L, file.getCRC32());
    }
}

Si quieres, también puedo añadir pruebas para getCRC32() con contenido no vacío, pero el diseño actual de File.getCRC32() y FileUtils no permite inyectar un CRC esperado sin cambios adicionales.


solo de lo que esta en las instrucciones

Listo. Solo pruebas de lo indicado en las instrucciones (sin cambios extra). Reemplaza FileTest.java con:

package es.upm.grise.profundizacion.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.exceptions.InvalidContentException;
import es.upm.grise.profundizacion.exceptions.WrongFileTypeException;

public class FileTest {

    @Test
    void addProperty_appendsContent() throws Exception {
        File file = new File();
        file.setType(FileType.PROPERTY);

        file.addProperty("KEY=VALUE".toCharArray());

        assertEquals(List.of('K','E','Y','=','V','A','L','U','E'), file.getContent());
    }

    @Test
    void addProperty_null_throwsInvalidContentException() {
        File file = new File();
        file.setType(FileType.PROPERTY);

        assertThrows(InvalidContentException.class, () -> file.addProperty(null));
    }

    @Test
    void addProperty_image_throwsWrongFileTypeException() {
        File file = new File();
        file.setType(FileType.IMAGE);

        assertThrows(WrongFileTypeException.class, () -> file.addProperty("A=B".toCharArray()));
    }

    @Test
    void getCRC32_empty_returnsZero() {
        File file = new File();

        assertEquals(0L, file.getCRC32());
    }
}

me da probelmas revisale

 Pega este contenido completo (así evitas errores de “resultado ignorado” en assertThrows() y te quedas solo con lo pedido en las instrucciones):

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

Con eso, queda limpio y alineado con las instrucciones.
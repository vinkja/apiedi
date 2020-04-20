package ch.ibw.appl.todo.server.functional;

import ch.ibw.appl.todo.server.angebot.model.Angebot;
import ch.ibw.appl.todo.server.ausgaben.model.Ausgabe;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.functional.shared.FunctionalTest;
import ch.ibw.appl.todo.server.shared.service.JSONSerializer;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AusgabeTest extends FunctionalTest {

  @Test
  public void notAcceptable() throws HttpClientException {
    GetMethod method = httpClient.get("/ausgaben", false);
    HttpResponse response = httpClient.execute(method);

    assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.code());
  }

  @Test
  public void get_ausgaben() {
    HttpResponse response = executeGet("/ausgaben");
//    HttpResponse response = executeGet("/todo/items", "text/csv");

    assertEquals(HttpStatus.OK_200, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());

    List<Ausgabe> ausgaben = new JSONSerializer().deserialize(body, new TypeReference<List<Ausgabe>>() {});
    assertEquals("Miete", ausgaben.get(0).bemerkung);
  }

  @Test
  public void get_byId() {
    HttpResponse response = executeGet("/ausgaben/2");

    assertEquals(HttpStatus.OK_200, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());

    Ausgabe ausgabe = new JSONSerializer().deserialize(body, new TypeReference<Ausgabe>() {});
    assertEquals("Telefon", ausgabe.bemerkung);
  }

  @Test
  public void get_byId_notFound() {
    HttpResponse response = executeGet("/ausgaben/3");

    assertEquals(HttpStatus.NOT_FOUND_404, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    assertEquals("", body);
  }

  @Test
  public void create_ausgabe() {
    Ausgabe ausgabe = Ausgabe.create("2020-03-03", "schissdreck", 500);
    HttpResponse response = executePost("/ausgaben", ausgabe);

    assertEquals(HttpStatus.CREATED_201, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    ModelId id = new JSONSerializer().deserialize(body, new TypeReference<ModelId>() {});
    assertNotNull(id);


    HttpResponse response2 = executeGet("/ausgaben/" + id.id);

    assertEquals(HttpStatus.OK_200, response2.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body2 = new String(response2.body());
    Ausgabe ausgabe2 = new JSONSerializer().deserialize(body2, new TypeReference<Ausgabe>() {});
    assertEquals("schissdreck", ausgabe2.bemerkung);
  }

  @Test
  public void create_ausgabe_validationFailed() {
    Ausgabe ausgabe = Ausgabe.create("", "", 0);
    HttpResponse response = executePost("/ausgaben", ausgabe);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    assertTrue(body.contains("message"));
  }
}

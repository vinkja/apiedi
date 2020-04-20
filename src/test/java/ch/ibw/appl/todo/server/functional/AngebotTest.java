package ch.ibw.appl.todo.server.functional;

import ch.ibw.appl.todo.server.angebot.model.Angebot;
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

public class AngebotTest extends FunctionalTest {

  @Test
  public void notAcceptable() throws HttpClientException {
    GetMethod method = httpClient.get("/angebote", false);
    HttpResponse response = httpClient.execute(method);

    assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.code());
  }

  @Test
  public void get_angebote() {
    HttpResponse response = executeGet("/angebote");
//    HttpResponse response = executeGet("/todo/items", "text/csv");

    assertEquals(HttpStatus.OK_200, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());

    List<Angebot> angebote = new JSONSerializer().deserialize(body, new TypeReference<List<Angebot>>() {});
    assertEquals(new Float(50), angebote.get(0).betrag, 0);
  }

  @Test
  public void get_byId() {
    HttpResponse response = executeGet("/angebote/2");

    assertEquals(HttpStatus.OK_200, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());

    Angebot angebot = new JSONSerializer().deserialize(body, new TypeReference<Angebot>() {});
    assertEquals(new Float(85), angebot.betrag, 0);
  }

  @Test
  public void get_byId_notFound() {
    HttpResponse response = executeGet("/angebote/4");

    assertEquals(HttpStatus.NOT_FOUND_404, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    assertEquals("", body);
  }

  @Test
  public void create_angebot() {
    Angebot angebot = Angebot.create("Extra", 110);
    HttpResponse response = executePost("/angebote", angebot);

    assertEquals(HttpStatus.CREATED_201, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    ModelId id = new JSONSerializer().deserialize(body, new TypeReference<ModelId>() {});
    assertNotNull(id);


    HttpResponse response2 = executeGet("/angebote/" + id.id);

    assertEquals(HttpStatus.OK_200, response2.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body2 = new String(response2.body());
    Angebot angebot2 = new JSONSerializer().deserialize(body2, new TypeReference<Angebot>() {});
    assertEquals(new Float(110), angebot2.betrag, 0);
  }

  @Test
  public void create_angebot_validationFailed() {
    Object angebot = Angebot.create("", 0);
    HttpResponse response = executePost("/angebote", angebot);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    System.out.println(body);
    assertTrue(body.contains("message"));
  }
}

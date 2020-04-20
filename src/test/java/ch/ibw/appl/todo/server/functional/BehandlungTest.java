package ch.ibw.appl.todo.server.functional;

import ch.ibw.appl.todo.server.shared.service.JSONSerializer;
import ch.ibw.appl.todo.server.functional.shared.FunctionalTest;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.behandlungen.model.Behandlung;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BehandlungTest extends FunctionalTest {

  @Test
  public void notAcceptable() throws HttpClientException {
    GetMethod method = httpClient.get("/behandlungen", false);
    HttpResponse response = httpClient.execute(method);

    assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.code());
  }

  @Test
  public void get_behandlungen() {
    HttpResponse response = executeGet("/behandlungen");
//    HttpResponse response = executeGet("/todo/items", "text/csv");

    assertEquals(HttpStatus.OK_200, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());

    List<Behandlung> behandlungen = new JSONSerializer().deserialize(body, new TypeReference<List<Behandlung>>() {});
    assertEquals("2015-08-08", behandlungen.get(0).date);
  }

  @Test
  public void get_byId() {
    HttpResponse response = executeGet("/behandlungen/2");

    assertEquals(HttpStatus.OK_200, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());

    Behandlung behandlung = new JSONSerializer().deserialize(body, new TypeReference<Behandlung>() {});
    assertEquals("2015-08-09", behandlung.date);
  }

  @Test
  public void get_byId_notFound() {
    HttpResponse response = executeGet("/behandlungen/42");

    assertEquals(HttpStatus.NOT_FOUND_404, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    assertEquals("", body);
  }

  @Test
  public void create_behandlung() {
    Behandlung behandlung = Behandlung.create("2010-08-08", 3, 15);
    HttpResponse response = executePost("/behandlungen", behandlung);

    assertEquals(HttpStatus.CREATED_201, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    ModelId id = new JSONSerializer().deserialize(body, new TypeReference<ModelId>() {});
    assertNotNull(id);

    HttpResponse response2 = executeGet("/behandlungen/" + id.id);

    assertEquals(HttpStatus.OK_200, response2.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body2 = new String(response2.body());
    Behandlung behandlung2 = new JSONSerializer().deserialize(body2, new TypeReference<Behandlung>() {});
    assertEquals(new Float(1350), behandlung2.summe, 0);
  }

  @Test
  public void create_behandlung_validationFailed() {
    Object behandlung = Behandlung.create("", 0, 0);
    HttpResponse response = executePost("/behandlungen", behandlung);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.code());
    assertEquals("application/json", response.headers().get("Content-Type").get(0));

    String body = new String(response.body());
    assertTrue(body.contains("message"));
  }
}

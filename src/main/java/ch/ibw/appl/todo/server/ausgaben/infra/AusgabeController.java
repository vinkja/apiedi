package ch.ibw.appl.todo.server.ausgaben.infra;

import ch.ibw.appl.todo.server.ausgaben.model.Ausgabe;
import ch.ibw.appl.todo.server.ausgaben.service.AusgabeService;
import ch.ibw.appl.todo.server.shared.service.JSONSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import spark.Service;

public class AusgabeController {
  private AusgabeService ausgabeService;

  public AusgabeController(Boolean isTest) {
    ausgabeService = new AusgabeService(new AusgabeSQL2ORepository(isTest));
  }

  public void createRoutes(Service server) {
    JSONSerializer jsonSerializer = new JSONSerializer();

    server.get("/ausgaben", "application/json",
            (request, response) -> {
              response.type("application/json");
              return ausgabeService.all();
            },
            jsonSerializer::serialize);

//    server.get("/todo/items", "text/csv",
//            (request, response) ->  todoItemService.all(),
//            model -> null/*make csv*/);

    server.get("/ausgaben/:id", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      return ausgabeService.getById(id);
    }, jsonSerializer::serialize);

    server.post("/ausgaben", (request, response) -> {
      Ausgabe ausgabe = jsonSerializer.deserialize(request.body(), new TypeReference<Ausgabe>() {});
      response.status(HttpStatus.CREATED_201);
      return ausgabeService.create(ausgabe);
    }, jsonSerializer::serialize);
  }
}

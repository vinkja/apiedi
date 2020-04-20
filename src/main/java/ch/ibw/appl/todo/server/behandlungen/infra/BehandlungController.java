package ch.ibw.appl.todo.server.behandlungen.infra;

import ch.ibw.appl.todo.server.angebot.infra.AngebotSQL2ORepository;
import ch.ibw.appl.todo.server.behandlungen.service.BehandlungService;
import ch.ibw.appl.todo.server.behandlungen.model.Behandlung;
import ch.ibw.appl.todo.server.shared.service.JSONSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import spark.Service;

public class BehandlungController {
  private BehandlungService ausgabeService;

  public BehandlungController(Boolean isTest) {
    ausgabeService = new BehandlungService(new BehandlungSQL2ORepository(isTest), new AngebotSQL2ORepository(isTest));
  }

  public void createRoutes(Service server) {
    JSONSerializer jsonSerializer = new JSONSerializer();

    server.get("/behandlungen", "application/json",
            (request, response) -> {
              response.type("application/json");
              return ausgabeService.all();
            },
            jsonSerializer::serialize);

//    server.get("/todo/items", "text/csv",
//            (request, response) ->  todoItemService.all(),
//            model -> null/*make csv*/);

    server.get("/behandlungen/:id", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      return ausgabeService.getById(id);
    }, jsonSerializer::serialize);

    server.post("/behandlungen", (request, response) -> {
      Behandlung behandlung = jsonSerializer.deserialize(request.body(), new TypeReference<Behandlung>() {});
      response.status(HttpStatus.CREATED_201);
      return ausgabeService.create(behandlung);
    }, jsonSerializer::serialize);
  }
}

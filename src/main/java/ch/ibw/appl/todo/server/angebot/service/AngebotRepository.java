package ch.ibw.appl.todo.server.angebot.service;

import ch.ibw.appl.todo.server.angebot.model.Angebot;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.shared.service.Repository;

import java.util.List;

public interface AngebotRepository<T extends Angebot> extends Repository<T> {
  List<T> all();
  ModelId add(T obj);
  T get(int id);
  T findByDescription(String description);

}

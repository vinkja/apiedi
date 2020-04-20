package ch.ibw.appl.todo.server.ausgaben.service;

import ch.ibw.appl.todo.server.ausgaben.model.Ausgabe;
import ch.ibw.appl.todo.server.behandlungen.model.Behandlung;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.shared.service.Repository;

import java.util.List;

public interface AusgabeRepository<T extends Ausgabe> extends Repository<T> {
  List<T> all();
  ModelId add(T obj);
  T get(int id);
  Object findByDescription(String description);

}

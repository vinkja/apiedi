package ch.ibw.appl.todo.server.ausgaben.service;

import ch.ibw.appl.todo.server.ausgaben.model.Ausgabe;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.behandlungen.service.ValidationError;

import java.util.List;

public class AusgabeService {
  private final AusgabeRepository<Ausgabe> ausgabeRepository;


  public AusgabeService(AusgabeRepository<Ausgabe> ausgabeRepository) {
    this.ausgabeRepository = ausgabeRepository;
  }

  public List<Ausgabe> all() {
    return ausgabeRepository.all();
  }

  public ModelId create(Ausgabe ausgabe) {
    if(ausgabe.date == null){
      throw new ValidationError("Date can not be empty");
    }
    if (ausgabe.bemerkung.isEmpty()) {
      throw new ValidationError("Bemerkung can not be empty");
    }
    if (ausgabe.betrag == 0) {
      throw new ValidationError("Betrag can not be empty");
    }

    return ausgabeRepository.add(ausgabe);
  }

  public Ausgabe getById(int id) {
    return ausgabeRepository.get(id);
  }
}

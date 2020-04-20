package ch.ibw.appl.todo.server.angebot.service;

import ch.ibw.appl.todo.server.angebot.model.Angebot;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.behandlungen.service.ValidationError;

import java.util.List;

public class AngebotService {
  private final AngebotRepository<Angebot> angebotRepo;

  public AngebotService(AngebotRepository<Angebot> angebotRepo) {
    this.angebotRepo = angebotRepo;
  }

  public List<Angebot> all() {
    return angebotRepo.all();
  }

  public ModelId create(Angebot angebot) {
    if(angebot.behart.isEmpty()){
      throw new ValidationError("Behandlungs Art can not be empty");
    }
    if (angebot.betrag == 0) {
      throw new ValidationError("Betrag can not be empty");
    }

    return angebotRepo.add(angebot);
  }

  public Angebot getById(int id) {
    return angebotRepo.get(id);
  }
}

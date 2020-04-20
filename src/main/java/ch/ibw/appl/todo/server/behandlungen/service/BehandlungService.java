package ch.ibw.appl.todo.server.behandlungen.service;

import ch.ibw.appl.todo.server.angebot.model.Angebot;
import ch.ibw.appl.todo.server.angebot.service.AngebotRepository;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.behandlungen.model.Behandlung;

import java.util.List;

public class BehandlungService {
  private final BehandlungRepository<Behandlung> behandlungRepo;
  private final AngebotRepository<Angebot> angebotRepo;

  public BehandlungService(BehandlungRepository<Behandlung> behandlungRepo, AngebotRepository<Angebot> angebotRepo) {
    this.behandlungRepo = behandlungRepo;
    this.angebotRepo = angebotRepo;
  }

  public List<Behandlung> all() {
    return behandlungRepo.all();
  }

  public ModelId create(Behandlung behandlung) {
    if(behandlung.date == null){
      throw new ValidationError("Date can not be empty");
    }
    if (behandlung.angebotid == 0) {
      throw new ValidationError("AngebotId can not be empty");
    }
    if (behandlung.anzahl == 0) {
      throw new ValidationError("Anzahl can not be empty");
    }

    behandlung.summe = angebotRepo.get(behandlung.angebotid).betrag * behandlung.anzahl;
    return behandlungRepo.add(behandlung);
  }

  public Behandlung getById(int id) {
    return behandlungRepo.get(id);
  }
}

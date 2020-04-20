package ch.ibw.appl.todo.server.behandlungen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Behandlung {
  public String date;
  public int angebotid;
  public int anzahl;
  public float summe;

  @Id
  @GeneratedValue
  public Long id;

  public static Behandlung create(String date, int angebotid, int anzahl) {
    Behandlung behandlung = new Behandlung();
    behandlung.date = date;
    behandlung.angebotid = angebotid;
    behandlung.anzahl = anzahl;
    return behandlung;
  }

  @Override
  public String toString() {
    return "Behandlung{" +
            "date=" + date +
            ", angebotid=" + angebotid +
            ", anzahl=" + anzahl +
            ", summe=" + summe +
            ", id=" + id +
            '}';
  }
}

package ch.ibw.appl.todo.server.ausgaben.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ausgabe {
  public String date;
  public String bemerkung;
  public float betrag;

  @Id
  @GeneratedValue
  public Long id;

  public static Ausgabe create(String date, String bemerkung, float betrag) {
    Ausgabe ausgabe = new Ausgabe();
    ausgabe.date = date;
    ausgabe.bemerkung = bemerkung;
    ausgabe.betrag = betrag;
    return ausgabe;
  }

  @Override
  public String toString() {
    return "Ausgabe{" +
            "date='" + date + '\'' +
            ", bemerkung='" + bemerkung + '\'' +
            ", betrag=" + betrag +
            ", id=" + id +
            '}';
  }
}

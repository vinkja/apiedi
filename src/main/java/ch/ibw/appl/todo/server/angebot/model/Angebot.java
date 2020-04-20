package ch.ibw.appl.todo.server.angebot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Angebot {
    public String behart;
    public float betrag;

    @Id
    @GeneratedValue
    public Long id;

    public static Angebot create(String behart, float betrag) {
        Angebot angebot = new Angebot();
        angebot.behart = behart;
        angebot.betrag = betrag;
        return angebot;
    }

    @Override
    public String toString() {
        return "Angebot{" +
                "behart='" + behart + '\'' +
                ", betrag=" + betrag +
                ", id=" + id +
                '}';
    }
}

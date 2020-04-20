package ch.ibw.appl.todo.server.angebot.infra;

import ch.ibw.appl.todo.server.angebot.model.Angebot;
import ch.ibw.appl.todo.server.angebot.service.AngebotRepository;
import ch.ibw.appl.todo.server.ausgaben.model.Ausgabe;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AngebotSQL2ORepository implements AngebotRepository<Angebot> {

  private final Sql2o sql2o;

  public AngebotSQL2ORepository(boolean isTest) {
    if(isTest){
      sql2o = new Sql2o("jdbc:hsqldb:mem:apiedi", "root", "");
      try(Connection conn = sql2o.open()){
        executeFile(conn, "C:\\Users\\mvink\\Documents\\Applikations-Entwicklung\\Privat\\A-PiediApp\\todomvc-java-server\\src\\main\\resources\\META-INF\\createtableAngebot.sql");
      }
    }else{
      sql2o = new Sql2o("jdbc:mysql://localhost:3306/apiedi", "root", "");
    }
  }

  private void executeFile(Connection conn, String s) {
    String path = s;
    String content;
    try {
      content = new String(Files.readAllBytes(Paths.get(path)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    for(String line : content.split(";")){
      if(!line.trim().isEmpty()){
        conn.createQuery(line).executeUpdate();
      }
    }
  }

  @Override
  public List<Angebot> all() {
    try(Connection conn = sql2o.open()){
      return conn.createQuery("select * from angebot").executeAndFetch(Angebot.class);
    }
  }

  @Override
  public ModelId add(Angebot angebot) {
    try(Connection conn = sql2o.open()){
      Query preparedStatement = conn.createQuery("insert into angebot (behart, betrag) values (:behart, :betrag)", true).bind(angebot);
      int newId = Integer.parseInt(preparedStatement.executeUpdate().getKey().toString());
      System.out.println(newId);
      return ModelId.create(newId);
    }
  }

  @Override
  public Angebot get(int id) {
    List<Angebot> angebote = all();
    for (Angebot angebot : angebote){
      if (angebot.id == id) {
        return angebot;
      }
    }
    return null;
  }

  @Override
  public Angebot findByDescription(String description) {
    return null;
  }

}

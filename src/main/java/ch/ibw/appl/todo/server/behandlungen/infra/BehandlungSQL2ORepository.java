package ch.ibw.appl.todo.server.behandlungen.infra;

import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import ch.ibw.appl.todo.server.behandlungen.model.Behandlung;
import ch.ibw.appl.todo.server.behandlungen.service.BehandlungRepository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BehandlungSQL2ORepository implements BehandlungRepository<Behandlung> {

  private final Sql2o sql2o;

  public BehandlungSQL2ORepository(boolean isTest) {
    if(isTest){
      sql2o = new Sql2o("jdbc:hsqldb:mem:apiedi", "root", "");
      try(Connection conn = sql2o.open()){
        executeFile(conn, "C:\\Users\\mvink\\Documents\\Applikations-Entwicklung\\Privat\\A-PiediApp\\todomvc-java-server\\src\\main\\resources\\META-INF\\createtableBehandlung.sql");
        executeFile(conn, "C:\\Users\\mvink\\Documents\\Applikations-Entwicklung\\Privat\\A-PiediApp\\todomvc-java-server\\src\\main\\resources\\META-INF\\testdata.sql");
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
  public List<Behandlung> all() {
    try(Connection conn = sql2o.open()){
      return conn.createQuery("select * from behandlung").executeAndFetch(Behandlung.class);
    }
  }

  @Override
  public ModelId add(Behandlung behandlung) {
    try(Connection conn = sql2o.open()){
      Query preparedStatement = conn.createQuery("insert into behandlung (date, angebotid, anzahl, summe) values (:date, :angebotid, :anzahl, :summe)", true).bind(behandlung);
      int newId = Integer.parseInt(preparedStatement.executeUpdate().getKey().toString());
      return ModelId.create(newId);

    }
  }

  @Override
  public Behandlung get(int id) {
    List<Behandlung> behandlungen = all();
    for (Behandlung behandlung : behandlungen){
      if (behandlung.id == id) {
        return behandlung;
      }
    }
    return null;
  }

  @Override
  public Behandlung findByDescription(String description) {
    return null;
  }

  public float summe(int angebotid, int anzahl) {
    return 0;
  }
}

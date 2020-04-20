package ch.ibw.appl.todo.server.ausgaben.infra;

import ch.ibw.appl.todo.server.ausgaben.model.Ausgabe;
import ch.ibw.appl.todo.server.ausgaben.service.AusgabeRepository;
import ch.ibw.appl.todo.server.behandlungen.model.ModelId;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AusgabeSQL2ORepository implements AusgabeRepository<Ausgabe> {

    private final Sql2o sql2o;

    public AusgabeSQL2ORepository(boolean isTest) {
        if(isTest){
            sql2o = new Sql2o("jdbc:hsqldb:mem:apiedi", "root", "");
            try(Connection conn = sql2o.open()){
                executeFile(conn, "C:\\Users\\mvink\\Documents\\Applikations-Entwicklung\\Privat\\A-PiediApp\\todomvc-java-server\\src\\main\\resources\\META-INF\\createtableAusgaben.sql");
                executeFile(conn, "C:\\Users\\mvink\\Documents\\Applikations-Entwicklung\\Privat\\A-PiediApp\\todomvc-java-server\\src\\main\\resources\\META-INF\\testdataAusgaben.sql");
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
    public List<Ausgabe> all() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from ausgabe").executeAndFetch(Ausgabe.class);
        }
    }

    @Override
    public ModelId add(Ausgabe ausgabe) {
        try(Connection conn = sql2o.open()){
            Query preparedStatement = conn.createQuery("insert into ausgabe (date, bemerkung, betrag) values (:date, :bemerkung, :betrag)", true).bind(ausgabe);
            int newId = Integer.parseInt(preparedStatement.executeUpdate().getKey().toString());
            return ModelId.create(newId);

        }
    }

    @Override
    public Ausgabe get(int id) {
        List<Ausgabe> ausgaben = all();
        for (Ausgabe ausgabe : ausgaben){
            if (ausgabe.id == id) {
                return ausgabe;
            }
        }
        return null;
    }

    @Override
    public Ausgabe findByDescription(String description) {
        return null;
    }
}

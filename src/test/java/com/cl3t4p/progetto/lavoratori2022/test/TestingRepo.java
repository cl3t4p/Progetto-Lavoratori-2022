package com.cl3t4p.progetto.lavoratori2022.test;

import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.repo.MainRepo;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class TestingRepo {
    private MainRepo repo;
    private EmbeddedPostgres db;
    public static TestingRepo instance = null;
    public static TestingRepo getInstance() {
        if (instance == null)
            instance = new TestingRepo();
        return instance;
    }

    @SneakyThrows
    private TestingRepo() {
        setupTempDB();
    }


    @SneakyThrows
    private void setupTempDB() {
        db = EmbeddedPostgres.builder()
                .start();
        URL url = TestingRepo.class.getResource("/schema.sql");
        assert url != null;
        String schema = new String(Files.readAllBytes(Path.of(url.toURI())));
        db.getPostgresDatabase().getConnection().createStatement().execute(schema);
        repo = new PostDriver(db.getPostgresDatabase());
    }
}

package fr.univtours.projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.univtours.projet.data.InitializationImpl;


@SpringBootApplication
public class HtdPlacesMain implements CommandLineRunner {
    @Autowired
    InitializationImpl dataInit;

    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(HtdPlacesMain.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
    	dataInit.initUsers();
        dataInit.initEvents();
    }
}

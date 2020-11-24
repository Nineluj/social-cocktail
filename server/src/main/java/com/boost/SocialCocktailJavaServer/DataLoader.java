package com.boost.SocialCocktailJavaServer;

import com.boost.SocialCocktailJavaServer.models.Bartender;
import com.boost.SocialCocktailJavaServer.models.Glass;
import com.boost.SocialCocktailJavaServer.repositories.BartenderRepository;
import com.boost.SocialCocktailJavaServer.repositories.GlassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Component
public class DataLoader implements ApplicationRunner {
    private BartenderRepository bartenderRepository;
    private GlassRepository glassRepository;

    @Autowired
    public DataLoader(BartenderRepository bartenderRepository, GlassRepository glassRepository) {
        this.bartenderRepository = bartenderRepository;
        this.glassRepository = glassRepository;
    }

    public void run(ApplicationArguments args) {
        if (bartenderRepository.findByUsername("admin") == null) {
            Bartender adminAccount = new Bartender();
            adminAccount.setUsername("admin");
            adminAccount.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
            adminAccount.setVerified(true);
            adminAccount.setAdmin(true);
            adminAccount.setId(0);
            bartenderRepository.save(adminAccount);
        }

        if (glassRepository.count() == 0) {
            // load data from file
            try {
                String[] lines = new String[2];
                // open input stream test.txt for reading purpose.
                BufferedReader br = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat("/glassdata.txt")));

                while ((lines[0] = br.readLine()) != null) {
                    lines[1] = br.readLine();

                    if (lines[0].equals("") || lines[1].equals("")) {
                        break;
                    }
                    glassRepository.save(new Glass(lines[0], lines[1]));
                    lines[0] = "";
                    lines[1] = "";
                }

                br.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
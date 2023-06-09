package coursework.cashflow.utils;

import coursework.cashflow.models.dao.Category;
import coursework.cashflow.models.dao.Currency;
import coursework.cashflow.models.dao.Tag;
import coursework.cashflow.models.dao.Users;
import coursework.cashflow.repositories.CategoryRepo;
import coursework.cashflow.repositories.CurrencyRepo;
import coursework.cashflow.repositories.TagRepo;
import coursework.cashflow.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Configuration
@EnableConfigurationProperties
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application")
public class LoadDatabase {

    private final UserRepo userRepo;
    private final DataSource dataSource;
    private final Token token;
//    /**
//     * need to change(delete file for init)
//     * to prevent second initialization if rerun spring
//     */
//    private void deletePreloadInfo() {
//        File file = new File("src/main/resources/application.yaml");
//        try (PrintWriter writer = new PrintWriter(file)) {
//            writer.print("");
//        } catch (java.io.FileNotFoundException e) {
//            System.err.println("Can't free file");
//        }
//    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void loadData() {
//        ResourceDatabasePopulator resourceDatabasePopulator =
//                new ResourceDatabasePopulator(false, false,
//                        "UTF-8", new ClassPathResource("sql/insert_test.sql"));
//        resourceDatabasePopulator.execute(dataSource);
//        Users testUser = userRepo.findByLogin("oarnopolin");
//        testUser.setToken(token.getJWTToken(testUser.getLogin()));
//        testUser.setPassword(BCrypt.hashpw(testUser.getPassword(), BCrypt.gensalt()));
//        userRepo.save(testUser);
//    }
}

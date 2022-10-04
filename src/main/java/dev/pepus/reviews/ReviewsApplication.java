package dev.pepus.reviews;

import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.RoleRepository;
import dev.pepus.reviews.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ReviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewsApplication.class, args);
    }
//
//    @Bean
//    public CommandLineRunner run(UserRepository repository, RoleRepository roleRepository) {
//        return (args -> {
//            ChangeRole(repository, roleRepository);
//        });
//    }
//
//    private void ChangePasswords(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        User peps = userRepository.findUserByUsername("mrsadpepe").get();
//        User liz = userRepository.findUserByUsername("lizardwizard").get();
//        User k0r = userRepository.findUserByUsername("mwek0rae").get();
//        User cef = userRepository.findUserByUsername("cefira154").get();
//        peps.setPassword(bCryptPasswordEncoder.encode("Fightin4Lyf"));
//        liz.setPassword(bCryptPasswordEncoder.encode("Yakuzin'"));
//        k0r.setPassword(bCryptPasswordEncoder.encode("Katyushka"));
//        cef.setPassword(bCryptPasswordEncoder.encode("Dashk"));
//        userRepository.save(peps);
//        userRepository.save(liz);
//        userRepository.save(k0r);
//        userRepository.save(cef);
//    }
//
//    private void ChangeRole(UserRepository userRepository, RoleRepository roleRepository) {
//        User peps = userRepository.findUserByUsername("mrsadpepe").get();
//        peps.setRole(roleRepository.findByName("admin").get());
//        userRepository.save(peps);
//    }
}

package christel.mvele.nutrisportBackend;

import christel.mvele.nutrisportBackend.model.Aliment;
import christel.mvele.nutrisportBackend.model.Recette;
import christel.mvele.nutrisportBackend.model.Role;
import christel.mvele.nutrisportBackend.repository.AlimentRepository;
import christel.mvele.nutrisportBackend.repository.RecetteRepository;
import christel.mvele.nutrisportBackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class NutrisportBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutrisportBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository,
									RecetteRepository recetteRepository,
									AlimentRepository alimentRepository){
		return args -> {
           if(roleRepository.findByName("ADMIN").isEmpty()){
			   roleRepository.save(
					   Role.builder()
							   .name("ADMIN")
							   .createdDate(LocalDate.now())
							   .build());

		   }

			if(roleRepository.findByName("COACH").isEmpty()){
				roleRepository.save(
						Role.builder()
								.name("COACH")
								.createdDate(LocalDate.now())
								.build());

			}

			if(recetteRepository.findByNom("AAA").isEmpty()){
				recetteRepository.save(
						Recette.builder()
								.nom("AAA")
								.description("AAA")
								.build());

			}

			if(alimentRepository.findByNom("BBB").isEmpty()){
				alimentRepository.save(
						Aliment.builder()
								.nom("BBB")
								.gras(1.0)
								.proteines(13.7)
								.glucides(5.54)
								.build());

			}
        };
	}

}

package narayan.banka.prospects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import narayan.banka.prospects.entity.Prospect;
import narayan.banka.prospects.repo.ProspectRepo;
import reactor.core.publisher.Flux;

/**
 * 
 * @author narayana
 *
 */
@SpringBootApplication
public class ProspectsApp {

	public static void main(String[] args) {
		SpringApplication.run(ProspectsApp.class, args);
	}

	@Bean
	CommandLineRunner init(ProspectRepo repo) {
		return args -> {
			Flux<Prospect> prospectsFlux = Flux.just(new Prospect("siva", 27), new Prospect("narayana", 26))
					.flatMap(repo::save);

			prospectsFlux
				.thenMany(repo.findAll())
				.subscribe(System.out::println);
		};
	}
}

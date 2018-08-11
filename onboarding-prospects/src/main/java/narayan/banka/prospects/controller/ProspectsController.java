package narayan.banka.prospects.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import narayan.banka.prospects.entity.Prospect;
import narayan.banka.prospects.repo.ProspectRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * Prospect Controller
 * 
 * @author narayana
 *
 */
@RestController
@RequestMapping("/v1/onboarding/prospects")
public class ProspectsController {

	private final ProspectRepo repo;

	public ProspectsController(ProspectRepo repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public Flux<Prospect> fetchAllProspects() {
		return repo.findAll();
	}
	
	@GetMapping("{id}")
	public Mono<ResponseEntity<Prospect>> fetchProspect(@PathVariable String id) {
		return repo.findById(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Prospect> saveProspect(@RequestBody Prospect prospect) {
		return repo.save(prospect);
	}
	
	@PutMapping("{id}")
	public Mono<ResponseEntity<Prospect>> updateProspect(@PathVariable String id,  @RequestBody Prospect prospect) {
		return repo.findById(id)
				.flatMap(existingProspect -> {
					existingProspect.setName(prospect.getName());
					existingProspect.setAge(prospect.getAge());
					return repo.save(existingProspect);
				})
				.map(updatedProspect -> ResponseEntity.ok(updatedProspect))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> deleteProspect(@PathVariable String id) {
		return repo.findById(id)
				.flatMap(existingProspect -> 
					repo.delete(existingProspect)
						.then(Mono.just(ResponseEntity.ok().<Void>build()))
						)
						.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping
	public Mono<Void> deleteAllProspects() {
		return repo.deleteAll();
	}

}

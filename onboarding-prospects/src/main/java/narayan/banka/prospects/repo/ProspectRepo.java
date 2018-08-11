package narayan.banka.prospects.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import narayan.banka.prospects.entity.Prospect;

/**
 * 
 * @author narayana
 *
 */
public interface ProspectRepo extends ReactiveMongoRepository<Prospect, String> {

}

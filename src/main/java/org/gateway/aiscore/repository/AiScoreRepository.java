package org.gateway.aiscore.repository;

import org.gateway.aiscore.model.AiScoreData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiScoreRepository extends MongoRepository<AiScoreData, String>{
	@Override
	<S extends AiScoreData> @NotNull List<S> saveAll(@NotNull Iterable<S> entities);
}

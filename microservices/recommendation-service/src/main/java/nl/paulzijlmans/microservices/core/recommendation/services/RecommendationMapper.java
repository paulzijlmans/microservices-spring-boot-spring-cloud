package nl.paulzijlmans.microservices.core.recommendation.services;

import nl.paulzijlmans.api.core.recommendation.Recommendation;
import nl.paulzijlmans.microservices.core.recommendation.persistence.RecommendationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {


    @Mapping(target = "rate", source = "entity.rating")
    @Mapping(target = "serviceAddress", ignore = true)
    Recommendation entityToApi(RecommendationEntity entity);


    @Mapping(target = "rating", source = "api.rate")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    RecommendationEntity apiToEntity(Recommendation api);

    List<Recommendation> entityListToApiList(List<RecommendationEntity> entity);

    List<RecommendationEntity> apiListToEntityList(List<Recommendation> api);
}

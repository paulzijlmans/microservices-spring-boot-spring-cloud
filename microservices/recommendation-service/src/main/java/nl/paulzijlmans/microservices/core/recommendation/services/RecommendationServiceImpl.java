package nl.paulzijlmans.microservices.core.recommendation.services;

import java.util.List;

import nl.paulzijlmans.api.core.recommendation.Recommendation;
import nl.paulzijlmans.api.core.recommendation.RecommendationService;
import nl.paulzijlmans.api.exceptions.InvalidInputException;
import nl.paulzijlmans.microservices.core.recommendation.persistence.RecommendationEntity;
import nl.paulzijlmans.microservices.core.recommendation.persistence.RecommendationRepository;
import nl.paulzijlmans.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository repository;

    private final RecommendationMapper mapper;

    private final ServiceUtil serviceUtil;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository repository, RecommendationMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Recommendation createRecommendation(Recommendation body) {
        try {
            RecommendationEntity entity = mapper.apiToEntity(body);
            RecommendationEntity newEntity = repository.save(entity);

            LOG.debug("createRecommendation: created a recommendation entity: {}/{}", body.productId(), body.recommendationId());
            return mapper.entityToApi(newEntity);

        } catch (DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.productId() + ", Recommendation Id:" + body.recommendationId());
        }
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        List<RecommendationEntity> entityList = repository.findByProductId(productId);
        List<Recommendation> list = entityList.stream().map(e -> new Recommendation(productId, e.getRecommendationId(), e.getAuthor(), e.getRating(), e.getContent(), serviceUtil.getServiceAddress())).toList();

        LOG.debug("getRecommendations: response size: {}", list.size());

        return list;
    }

    @Override
    public void deleteRecommendations(int productId) {
        LOG.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
        repository.deleteAll(repository.findByProductId(productId));
    }
}

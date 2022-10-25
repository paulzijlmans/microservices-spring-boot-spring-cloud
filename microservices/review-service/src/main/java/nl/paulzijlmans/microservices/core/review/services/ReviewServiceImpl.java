package nl.paulzijlmans.microservices.core.review.services;

import nl.paulzijlmans.api.core.review.Review;
import nl.paulzijlmans.api.core.review.ReviewService;
import nl.paulzijlmans.api.exceptions.InvalidInputException;
import nl.paulzijlmans.microservices.core.review.persistence.ReviewEntity;
import nl.paulzijlmans.microservices.core.review.persistence.ReviewRepository;
import nl.paulzijlmans.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository repository;

    private final ReviewMapper mapper;

    private final ServiceUtil serviceUtil;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Review createReview(Review body) {
        try {
            ReviewEntity entity = mapper.apiToEntity(body);
            ReviewEntity newEntity = repository.save(entity);

            LOG.debug("createReview: created a review entity: {}/{}", body.productId(), body.reviewId());
            return mapper.entityToApi(newEntity);

        } catch (DataIntegrityViolationException dive) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.productId() + ", Review Id:" + body.reviewId());
        }
    }

    @Override
    public List<Review> getReviews(int productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        List<ReviewEntity> entityList = repository.findByProductId(productId);
        List<Review> list = entityList.stream().map(e -> new Review(productId, e.getReviewId(), e.getAuthor(), e.getSubject(), e.getContent(), serviceUtil.getServiceAddress())).toList();

        LOG.debug("getReviews: response size: {}", list.size());

        return list;
    }

    @Override
    public void deleteReviews(int productId) {
        LOG.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);
        repository.deleteAll(repository.findByProductId(productId));
    }
}

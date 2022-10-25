package nl.paulzijlmans.microservices.core.product.service;

import nl.paulzijlmans.api.core.product.Product;
import nl.paulzijlmans.api.core.product.ProductService;
import nl.paulzijlmans.api.exceptions.InvalidInputException;
import nl.paulzijlmans.api.exceptions.NotFoundException;
import nl.paulzijlmans.microservices.core.product.persistence.ProductEntity;
import nl.paulzijlmans.microservices.core.product.persistence.ProductRepository;
import nl.paulzijlmans.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Product createProduct(Product body) {
        try {
            ProductEntity entity = mapper.apiToEntity(body);
            ProductEntity newEntity = repository.save(entity);

            LOG.debug("createProduct: entity created for productId: {}", body.productId());
            return mapper.entityToApi(newEntity);

        } catch (DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.productId());
        }
    }

    @Override
    public Product getProduct(int productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        ProductEntity entity = repository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));

        Product response = new Product(productId, entity.getName(), entity.getWeight(), serviceUtil.getServiceAddress());

        LOG.debug("getProduct: found productId: {}", response.productId());

        return response;
    }

    @Override
    public void deleteProduct(int productId) {
        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
        repository.findByProductId(productId).ifPresent(repository::delete);
    }
}

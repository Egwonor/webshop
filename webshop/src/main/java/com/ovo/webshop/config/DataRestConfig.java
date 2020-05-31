package com.ovo.webshop.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.ovo.webshop.entity.Product;
import com.ovo.webshop.entity.ProductCategory;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	
	@Autowired
	public DataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
        
		HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.POST};
		
		config.getExposureConfiguration().forDomainType(Product.class)
		                                 .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
		                                 .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		config.getExposureConfiguration()
		                               .forDomainType(ProductCategory.class)
                                       .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                                       .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		exposeIds(config);
	}


	private void exposeIds(RepositoryRestConfiguration config) {
	
		
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		List<Class> entityClasses = new ArrayList<>();
		
		for (EntityType tempEntity: entities) {
			entityClasses.add(tempEntity.getJavaType());		
			
			Class[] domainTypes = entityClasses.toArray(new Class[0]);
			config.exposeIdsFor(domainTypes);
			
		}
	}

}

package com.sharingif.cube.spring.boot.core.autoconfigure.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sharingif.cube.beans.factory.config.ExtendedPropertyPlaceholderConfigurer;
import com.sharingif.cube.components.sequence.ISequenceHandler;
import com.sharingif.cube.components.sequence.SequenceHandlerImpl;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.bind.support.ConfigurableBindingInitializer;

/**
 * CoreComponentsAutoconfigure 
 * 
 * 2017年5月1日 下午6:15:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class CoreComponentsAutoconfigure {
	
	@Bean(name="sequenceHandler")
	public ISequenceHandler createSequenceHandler() {
		SequenceHandlerImpl sequenceHandler = new SequenceHandlerImpl();
		
		return sequenceHandler;
	}
	
	@Bean(name="conversionService")
	public FormattingConversionServiceFactoryBean createConversionService() {
		FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();
		return conversionService;
	}
	
	@Bean(name="validator")
	public Validator createValidator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean(name="commonAnnotationBeanPostProcessor")
	public CommonAnnotationBeanPostProcessor createCommonAnnotationBeanPostProcessor() {
		CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor = new CommonAnnotationBeanPostProcessor();
		commonAnnotationBeanPostProcessor.setFallbackToDefaultTypeMatch(false);
		
		return commonAnnotationBeanPostProcessor;
	}
	
	@Bean(name = "bindingInitializer")
	public BindingInitializer create(Validator validator, ConversionService conversionService) {
		ConfigurableBindingInitializer bindingInitializer = new ConfigurableBindingInitializer();
		bindingInitializer.setValidator(validator);
		bindingInitializer.setConversionService(conversionService);
		
		return bindingInitializer;
	}
	
	@Bean(name="messageSource")
	public ResourceBundleMessageSource createMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding(CubeConfigure.DEFAULT_ENCODING);
		messageSource.setBasenames(
				"config.i18n.exception.DefaultValidationMessages"
				,"config.i18n.exception.Cube"
				,"config.i18n.exception.Business"
				,"config.i18n.exception.Runtime"
				,"config.i18n.exception.Validation"
				,"config.i18n.exception.Security"
				,"config.i18n.exception.ValidationForm"
				,"config.i18n.constants.Constants"
				,"config.i18n.dictionary.Dictionary"
				);
		
		return messageSource;
	}
	
	@Bean(name="commonProperties")
	public static List<Resource> createCommonProperties() {
		List<Resource> commonProperties = new ArrayList<Resource>(2);
		commonProperties.add(new ClassPathResource("config/app/CubeConfigure.properties"));
		
		return commonProperties;
	}
	
	@Bean(name="devPropertyPlaceholderConfigurer")
	@Profile("DEV")
	public static PropertyPlaceholderConfigurer createDevPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(createCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(CubeConfigure.DEFAULT_ENCODING);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_DEV.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
	@Bean(name="testPropertyPlaceholderConfigurer")
	@Profile("TEST")
	public static PropertyPlaceholderConfigurer createTestPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(createCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(CubeConfigure.DEFAULT_ENCODING);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_TEST.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
	@Bean(name="prodPropertyPlaceholderConfigurer")
	@Profile("PROD")
	public static PropertyPlaceholderConfigurer createProdPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(createCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(CubeConfigure.DEFAULT_ENCODING);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_PROD.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
}

//package com.antplatform.admin.common.config;
//
//import org.hibernate.validator.HibernateValidator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
//
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//
///**
// * @author: maoyan
// * @date: 2022/5/7 09:33:32
// * @description:
// */
//@Configuration
//public class ValidatorConfiguration {
//
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
//        //设置validator模式为快速失败
//        postProcessor.setValidator(validator());
//        return postProcessor;
//    }
//
//    @Bean
//    public Validator validator(){
//
//        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
//                .configure()
//                .failFast(true)
//                .buildValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//
//        return validator;
//    }
//}

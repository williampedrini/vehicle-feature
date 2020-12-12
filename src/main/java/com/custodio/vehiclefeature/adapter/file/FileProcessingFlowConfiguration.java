package com.custodio.vehiclefeature.adapter.file;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.transaction.DefaultTransactionSynchronizationFactory;
import org.springframework.integration.transaction.ExpressionEvaluatingTransactionSynchronizationProcessor;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.transaction.TransactionManager;

import java.io.File;

import static org.springframework.integration.dsl.IntegrationFlows.from;
import static org.springframework.integration.dsl.Pollers.fixedDelay;
import static org.springframework.integration.file.dsl.Files.inboundAdapter;

@Configuration
@EnableConfigurationProperties(FileProcessingFlowProperties.class)
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class FileProcessingFlowConfiguration {
    static final String INPUT_CHANNEL = "raw-information";

    private final ApplicationContext applicationContext;
    private final FileProcessingFlowProperties properties;

    @Bean
    File errorDirectory() {
        return makeDirectory(properties.getDirectory().getError());
    }

    @Bean
    File inputDirectory() {
        return makeDirectory(properties.getDirectory().getInput());
    }

    @Bean
    File outputDirectory() {
        return makeDirectory(properties.getDirectory().getOutput());
    }

    @Bean
    IntegrationFlow processFileFlow(final TransactionManager transactionManager) {
        final var adapter = inboundAdapter(inputDirectory())
                                    .preventDuplicates(true);

        final var poller = fixedDelay(properties.getPollerDelay())
                                   .transactionSynchronizationFactory(transactionSynchronizationFactory())
                                   .transactional(transactionManager);

        return from(adapter, endpointConfigurer -> endpointConfigurer.poller(poller))
                       .channel(INPUT_CHANNEL)
                       .get();
    }

    @Bean
    TransactionSynchronizationFactory transactionSynchronizationFactory() {
        final var parser = new SpelExpressionParser();
        final var syncProcessor = new ExpressionEvaluatingTransactionSynchronizationProcessor();
        syncProcessor.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());

        final var afterCommitExpression = "payload.renameTo(new java.io.File(@outputDirectory.path  + T(java.io.File).separator + payload.name))";
        syncProcessor.setAfterCommitExpression(parser.parseExpression(afterCommitExpression));

        final var afterRollbackExpression = "payload.renameTo(new java.io.File(@errorDirectory.path + T(java.io.File).separator + payload.name))";
        syncProcessor.setAfterRollbackExpression(parser.parseExpression(afterRollbackExpression));

        return new DefaultTransactionSynchronizationFactory(syncProcessor);
    }

    private static File makeDirectory(final String path) {
        final var file = new File(path);
        file.mkdirs();
        return file;
    }
}

package com.midas.app.config;

import com.midas.app.activities.AccountActivity;
import com.midas.app.activities.AccountActivityImpl;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.workflows.CreateAccountWorkflow;
import com.midas.app.workflows.CreateWorkFlowImpl;
import com.midas.app.workflows.UpdateAccountWorkFlowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class TemporalConfig {
  private String temporalServiceAddress = "127.0.0.1:7233";

  private String temporalNamespace = "default";

  @Bean
  public WorkflowServiceStubs workflowServiceStubs() {
    return WorkflowServiceStubs.newInstance(
        WorkflowServiceStubsOptions.newBuilder().setTarget(temporalServiceAddress).build());
  }

  @Bean
  public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
    return WorkflowClient.newInstance(
        workflowServiceStubs,
        WorkflowClientOptions.newBuilder().setNamespace(temporalNamespace).build());
  }

  @Bean
  public WorkerFactory workerFactory(WorkflowClient workflowClient) {
    return WorkerFactory.newInstance(workflowClient);
  }

  @Bean
  public Worker worker(WorkerFactory workerFactory, AccountActivity accountActivity) {

    Worker worker = workerFactory.newWorker(CreateAccountWorkflow.QUEUE_NAME);
    worker.registerWorkflowImplementationTypes(
        CreateWorkFlowImpl.class, UpdateAccountWorkFlowImpl.class);
    worker.registerActivitiesImplementations(accountActivity);
    workerFactory.start();
    return worker;
  }

  @Bean
  public AccountActivity accountActivity(
      AccountRepository accountRepository, PaymentProvider paymentProvider) {
    return new AccountActivityImpl(accountRepository, paymentProvider);
  }
}

package com.midas.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MidasApplication {

  public static void main(String[] args) {

    /*ConfigurableApplicationContext appContext = SpringApplication.run(MidasApplication.class, args);
    WorkerFactory factory = appContext.getBean(WorkerFactory.class);
    AccountActivity signUpActivity = appContext.getBean(AccountActivity.class);
    Worker worker = factory.newWorker(CreateAccountWorkflow.QUEUE_NAME);
    worker.registerWorkflowImplementationTypes(CreateWorkFlowImpl.class);
    worker.registerActivitiesImplementations(signUpActivity);
    factory.start();*/
    SpringApplication.run(MidasApplication.class, args);
  }
}

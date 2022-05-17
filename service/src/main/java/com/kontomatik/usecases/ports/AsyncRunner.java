package com.kontomatik.usecases.ports;

import org.springframework.stereotype.Service;

@Service
public class AsyncRunner {

  public void run(Runnable runnable) {
    new Thread(runnable).start();
  }

}

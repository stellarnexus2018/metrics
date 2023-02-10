package ru.master.metrics.service.impl;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.master.metrics.service.EngineService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class EngineServiceImpl implements EngineService {
  private AtomicInteger cnt;

  public EngineServiceImpl(MeterRegistry meter) {
    cnt = new AtomicInteger();
    meter.gauge("popper", cnt);
  }

  @Timed("updatingOne")
  public int updateOne(int min, int max) {
    int rand = ThreadLocalRandom.current().nextInt(min, max);
    cnt.set(rand);

    log.info("updateOne: установлен = {}", rand);

    return rand;
  }
}
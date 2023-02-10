package ru.master.metrics.scheduler.impl;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.master.metrics.properties.RndProps;
import ru.master.metrics.scheduler.SequentScheduler;
import ru.master.metrics.service.EngineService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SequentSchedulerImpl implements SequentScheduler {
  private final EngineService engine;
  private final RndProps range;

  @Timed("updatingCntScheduler")
  @Scheduled(fixedDelay = 11_000)
  public void updateCnt() {
    final int val = engine.updateOne(range.getMinrange(), range.getMaxrange());
    log.info("Scheduler: val = {}", val);
  }
}
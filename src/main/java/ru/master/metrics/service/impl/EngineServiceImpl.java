package ru.master.metrics.service.impl;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.master.metrics.service.EngineService;
import io.micrometer.core.instrument.Gauge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Service
@Slf4j
public class EngineServiceImpl implements EngineService {
  private final AtomicInteger cntAct;
  private final AtomicInteger cntMail;
  private final AtomicInteger cntPolicy;
  private final AtomicLong signerEndDate;

  public Supplier<Number> fetchSignerEndDate() {
    return ()-> signerEndDate;
  }

  public Supplier<Number> fetchActCount() {
    return ()-> cntAct;
  }

  public Supplier<Number> fetchMailCount() {
    return ()-> cntMail;
  }

  public Supplier<Number> fetchPolicyCount() {
    return ()-> cntPolicy;
  }

  public EngineServiceImpl(MeterRegistry meter) {
    //meter.gauge("signerEndDate", signerEndDate);
    cntAct = new AtomicInteger();
    cntMail = new AtomicInteger();
    cntPolicy = new AtomicInteger();
    signerEndDate = new AtomicLong();
    signerEndDate.set(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

    Gauge.builder("server.sign.user.end", fetchSignerEndDate()).
        tag("certuser", "dgorshkov").
        description("дата окончания действия сертификата").
        register(meter);

    Gauge.builder("server.sign.doc.act", fetchActCount()).
        tag("doctype", "act").
        description("количество подписанных актов на сервере").
        register(meter);

    Gauge.builder("server.sign.doc.mail", fetchMailCount()).
        tag("doctype", "mail").
        description("количество подписанных писем на сервере").
        register(meter);

    Gauge.builder("server.sign.doc.policy", fetchPolicyCount()).
        tag("doctype", "policy").
        description("количество подписанных полисов на сервере").
        register(meter);
  }

  @Timed("updatingOne")
  public int updateOne(int min, int max) {
    //int rand = ThreadLocalRandom.current().nextInt(min, max);
    int rand;

    rand = getRandom(min, max);
    cntAct.set(rand);
    log.info("cntAct: установлен = {}", rand);

    rand = getRandom(min, max);
    cntMail.set(rand);
    log.info("cntMail: установлен = {}", rand);

    rand = getRandom(min, max);
    cntPolicy.set(rand);
    log.info("cntPolicy: установлен = {}", rand);

    return rand;
  }

  public static int getRandom(int min, int max) {
    max -= min;
    return (int) (Math.random() * ++max) + min;
  }
}
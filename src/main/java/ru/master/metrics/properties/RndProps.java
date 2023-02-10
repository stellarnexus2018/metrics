package ru.master.metrics.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rnd")
public class RndProps {
  private int minrange;
  private int maxrange;
}

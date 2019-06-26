# Srping boot metric
Riot Metric integration project for spring boot


# Usage

```
<dependency>
    <groupId>com.conch</groupId>
    <artifactId>metrics</artifactId>
    <version>1.8.4</version>
</dependency>
```

Add following config in spring application.yml or properties

```
metrics:
  enabled: true  # metrics plugin autoconfigured only when true
  controller:
    enabled: false # not implemented.. keep it false
  jvm:  
    enabled: true # true to enable JVM metric
    memory-metrics-name: memory  # optional (delete line to disable memory metric)
    gc-metrics-name: gc # optional (delete line to disable garbage collection metric)
    thread-states-metrics-name: thread-states # optional (delete line to disable thread  metric)
    fd-usage-metrics-name: fd-usage # optional (delete line to disable file descriptor collection metric)
    cpu-usage-metrics-name: cpu-usage # optional (delete line to disable cpu collection metric)
```   

# Metric Annotation

Add @Metrics annotation in public method
```
  @Metrics(type = MetricsType.TIMER, reservoirConstrcutorParams = {"1", "MINUTES"}, reservoir = ReservoirType.SLIDING_TIME_WINDOW)
```

Metric Type
```
public enum MetricsType {
    COUNT,
    METER,
    HISTOGRAM,
    TIMER;
```

Reservoir Type

```
    EXPONENTIAL_DECAY
    SLIDING_TIME_WINDOW
    UNIFORM
    SLIDING_WINDOW
```

Refer http://metrics.dropwizard.io/4.0.0/manual/core.html for Reservoir characteristics


# TODO

metrcs.controller not implemented!!! looking for contributor


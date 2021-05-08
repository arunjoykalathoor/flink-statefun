/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.statefun.examples.datastream;

import com.twitter.chill.protobuf.ProtobufSerializer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.util.PrintSinkOutputWriter;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.CheckpointingOptions;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.statefun.flink.common.protobuf.ProtobufTypeSerializer;
import org.apache.flink.statefun.flink.core.StatefulFunctionsConfig;
import org.apache.flink.statefun.flink.core.message.MessageFactoryType;
import org.apache.flink.statefun.flink.core.message.RoutableMessage;
import org.apache.flink.statefun.flink.core.message.RoutableMessageBuilder;
import org.apache.flink.statefun.flink.datastream.StatefulFunctionDataStreamBuilder;
import org.apache.flink.statefun.flink.datastream.StatefulFunctionEgressStreams;
import org.apache.flink.statefun.sdk.Context;
import org.apache.flink.statefun.sdk.FunctionType;
import org.apache.flink.statefun.sdk.Message;
import org.apache.flink.statefun.sdk.StatefulFunction;
import org.apache.flink.statefun.sdk.annotations.Persisted;
import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.state.PersistedValue;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.util.Collector;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class WordCount {

  private static final FunctionType WORD_COUNT_FUNCTION_TYPE = new FunctionType("wordcountjob",
      "wordcount");
  private static final FunctionType SINK_FUNCTION_TYPE = new FunctionType("wordcountjob",
      "sink");
  private static final EgressIdentifier<Message> EGRESS_OUT =
      new EgressIdentifier<>("wordcount", "out", Message.class);

  public static void main(String... args) throws Exception {

    // -----------------------------------------------------------------------------------------
    // obtain the stream execution env and create some data streams
    // -----------------------------------------------------------------------------------------

    Logger rootLogger = Logger.getRootLogger();
    rootLogger.setLevel(Level.INFO);

    //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    //env.enableCheckpointing(100);
    Configuration conf = new Configuration();
    conf.setString(ConfigConstants.JOB_MANAGER_WEB_LOG_PATH_KEY, "/tmp");
    conf.setString(ConfigConstants.TASK_MANAGER_LOG_PATH_KEY, "/tmp");
    conf.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true);
    conf.setString(CheckpointingOptions.STATE_BACKEND, "remoteHeap");
    // conf.setString(CheckpointingOptions.STATE_BACKEND,"filesystem");

    conf.setInteger(RestOptions.PORT, 8050);

    StreamExecutionEnvironment env =
        StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
    //  env.enableCheckpointing(30000, CheckpointingMode.EXACTLY_ONCE);

    env.getConfig()
        .registerTypeWithKryoSerializer(ReduceOperatorState.class, ProtobufSerializer.class);
    env.setParallelism(1);
    env.getConfig().enableSysoutLogging();
    //    env.getConfig().setUseDynamicPartitioning(true);
    //    env.getConfig().setDispatchRebalanceEventInterval(10000);
    //    env.setParallelism(1);
    StatefulFunctionsConfig statefunConfig = StatefulFunctionsConfig.fromEnvironment(env);
    statefunConfig.setFactoryType(MessageFactoryType.WITH_KRYO_PAYLOADS);

    //Kafka
    //zookeeper-server-start.bat ../../config/zookeeper.properties
    //kafka-server-start.bat  ../../config/server.properties
    //kafka-topics.bat --create --topic sentences --bootstrap-server localhost:9092
    //kafka-console-producer.bat --topic sentences --bootstrap-server localhost:9092
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", "localhost:9092");
    properties.setProperty("group.id", "test");
    System.out.print(env.getConfig());
    DataStream<RoutableMessage> names =
        env.addSource(new TextLineSource()).setParallelism(1)
            .partitionCustom(new VectorIndexModuloPartitioner(0), key -> key) //For predictable partitioning
            // env.addSource(new FlinkKafkaConsumer<>("sentences", new SimpleStringSchema(), properties))
            .flatMap(new SentenceSplitFunction()).setParallelism(2);

    StatefulFunctionDataStreamBuilder builder =
        StatefulFunctionDataStreamBuilder.builder("wordcount")
            .withDataStreamAsIngress(names)
            .withFunctionProvider(WORD_COUNT_FUNCTION_TYPE, unused -> new WordCountFunction())
            .withEgressId(EGRESS_OUT);

    StatefulFunctionEgressStreams out = builder.withConfiguration(statefunConfig).build(env);

    DataStream<Message> output = out.getDataStreamForEgressId(EGRESS_OUT);

    output.addSink(new StatefulPrintOperator()).setParallelism(1);

    System.out.println("Flink Plan " + env.getExecutionPlan());

    env.execute();
  }

  public static class VectorIndexModuloPartitioner implements Partitioner<Message> {

    int index;

    public VectorIndexModuloPartitioner(int index) {
      this.index = index;
    }

    @Override
    public int partition(Message key, int numPartitions) {
      return key.getTimeVector().get(index)
          % numPartitions; //TODO  data may not be partioned evenly when not used after Index 0;
    }
  }

  private static final class SentenceSplitFunction extends
      RichFlatMapFunction<Message, RoutableMessage> implements CheckpointedFunction {

    int indexOfTask;
    private ListState<MapOperatorState> state;

    int operatorIndex;

    private int nOperators = 5;


    private VectorClock vectorClock;

    @Override
    public void flatMap(Message textLine, Collector<RoutableMessage> out) throws Exception {
      vectorClock.updateClock(textLine.getTimeVector());
      Arrays.stream(textLine.getData().split("\\s")).forEach(word -> {
            //System.out.println(Arrays.toString(vectorClock.getCurrentTime().toArray(new Integer[0])));
            out.collect(RoutableMessageBuilder.builder()
                .withTargetAddress(WORD_COUNT_FUNCTION_TYPE, "ALL")//name.getData()
                .withMessageBody(new Message(word, vectorClock.getCurrentTime()))
                .build());
            List<MapOperatorState> operatorStates = new ArrayList<>();
            operatorStates
                .add(MapOperatorState.newBuilder().addAllVTimestamp(vectorClock.getCurrentTime())
                    .build());
            try {
              state.update(operatorStates);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
      );
    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
      indexOfTask = getRuntimeContext().getIndexOfThisSubtask();
      operatorIndex = indexOfTask + 1;
      state = context.getOperatorStateStore().getListState(new ListStateDescriptor<>(
          "map" + indexOfTask,
          new ProtobufTypeSerializer<>(MapOperatorState.class)));
      Iterable<MapOperatorState> mapOperatorStates = state.get();
      if (mapOperatorStates == null || !mapOperatorStates.iterator().hasNext()) {
        vectorClock = new VectorClock(nOperators, operatorIndex);
        List<MapOperatorState> operatorStates = new ArrayList<>();
        operatorStates
            .add(MapOperatorState.newBuilder().addAllVTimestamp(vectorClock.getCurrentTime())
                .build());
      }
    }
  }

  private static final class WordCountFunction implements StatefulFunction {

    @Persisted
    private final PersistedValue<ReduceOperatorState> wordCountState = PersistedValue
        .of("count", ReduceOperatorState.class);

    final int operatorIndex = 3;

    private int nOperators = 5;

    private transient VectorClock clock;

    @Override
    public void invoke(Context context, Object input) {
      if (wordCountState.get() == null) {
        clock = new VectorClock(nOperators, operatorIndex);
        wordCountState.set(ReduceOperatorState.newBuilder().setWordCount(0)
            .addAllVTimestamp(clock.getCurrentTime()).build());
      }
      clock = new VectorClock(wordCountState.get().getVTimestampList(), operatorIndex);
      Message message = (Message) input;
      System.out
          .println("Message " + Arrays.toString(message.getTimeVector().toArray(new Integer[0])));
      clock.updateClock(message.getTimeVector());
      int wordcount = wordCountState.get().getWordCount() + 1;

      context.send(EGRESS_OUT, new Message(String
          .format("(%s,%d)", message.getData(),
              wordcount),
          clock.getCurrentTime()));
      // System.out.println(Arrays.toString(clock.getCurrentTime().toArray(new Integer[0])));
      wordCountState.set(
          ReduceOperatorState.newBuilder().setWordCount(wordcount)
              .addAllVTimestamp(clock.getCurrentTime()).build());
    }

  }

  private static final class StatefulPrintOperator extends RichSinkFunction<Message> implements
      CheckpointedFunction {

    private VectorClock clock;
    private int nOperators = 5;

    private int operatorIndex = 4;

    private transient ListState<SinkOperatorState> state;

    private PrintSinkOutputWriter<String> writer = null;

    public StatefulPrintOperator() {
      writer = new PrintSinkOutputWriter<>(false);
    }

    @Override
    public void invoke(Message message, Context context) throws Exception {
      Iterable<SinkOperatorState> sinkOperatorStates = state.get();
      if (sinkOperatorStates == null || !sinkOperatorStates.iterator().hasNext()) {
        clock = new VectorClock(nOperators, operatorIndex);
        List<SinkOperatorState> operatorStates = new ArrayList<>();
        operatorStates
            .add(SinkOperatorState.newBuilder().addAllVTimestamp(clock.getCurrentTime())
                .build());
        state.update(operatorStates);
      } else {
        clock = new VectorClock(sinkOperatorStates.iterator().next().getVTimestampList(),
            operatorIndex);
      }
      //System.out.println("Message " + Arrays.toString(message.getTimeVector().toArray(new Integer[0])));
      clock.updateClock(message.getTimeVector());
      List<SinkOperatorState> operatorStates = new ArrayList<>();
      operatorStates
          .add(SinkOperatorState.newBuilder().addAllVTimestamp(clock.getCurrentTime())
              .build());
      // System.out.println(Arrays.toString(clock.getCurrentTime().toArray(new Integer[0])));
      writer.write(String.format("%s at %s", message.getData(),
          Arrays.toString(clock.getCurrentTime().toArray())));
      state.update(operatorStates);

    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
      writer.open(getRuntimeContext().getIndexOfThisSubtask(),
          getRuntimeContext().getNumberOfParallelSubtasks());
      state = context.getOperatorStateStore().getListState(new ListStateDescriptor<>(
          "sink",
          new ProtobufTypeSerializer<>(SinkOperatorState.class)));
    }


  }

  private static final class TextLineSource extends RichParallelSourceFunction<Message>
      implements CheckpointedFunction {

    private static final long serialVersionUID = 1;

    private volatile boolean canceled;

    private ListState<SourceOperatorState> state;

    private VectorClock clock;

    private int currentProcessIndex = 0;

    private int nOperators = 5;

    int indexOfTask;

    @Override
    public void run(SourceContext<Message> ctx) throws InterruptedException {
      String[] names = {"Stephan", "Igal", "Gordon", "Seth", "Marta"};
      ThreadLocalRandom random = ThreadLocalRandom.current();
      int count = 0;
      while (true) {
        int index = count % 5; // random.nextInt(names.length);
        final String name = names[index];
        synchronized (ctx.getCheckpointLock()) {
          if (canceled) {
            return;
          }
          try {
            List<SourceOperatorState> operatorStates = new ArrayList<>();
            clock.increment();
            operatorStates
                .add(SourceOperatorState.newBuilder().setOffset(count).addAllVTimestamp(
                    clock.getCurrentTime()).build());
            //System.out.println(Arrays.toString(clock.getCurrentTime().toArray(new Integer[0])));
            ctx.collect(new Message(name, clock.getCurrentTime())); //TODO Bug from Order
            state.update(operatorStates);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        if (count++ > 200000) {
          break;
        }
      }


    }

    @Override
    public void cancel() {
      canceled = true;
    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
      indexOfTask = getRuntimeContext().getIndexOfThisSubtask();
      state = context.getOperatorStateStore().getListState(new ListStateDescriptor<>(
          "src" + indexOfTask,
          new ProtobufTypeSerializer<>(SourceOperatorState.class)));

      Iterable<SourceOperatorState> sourceOperatorStates = state.get();
      if (sourceOperatorStates == null || !sourceOperatorStates.iterator().hasNext()) {
        clock = new VectorClock(5, currentProcessIndex);
      } else {
        clock = new VectorClock(state.get().iterator().next().getVTimestampList(),
            currentProcessIndex);
      }
    }
  }
}

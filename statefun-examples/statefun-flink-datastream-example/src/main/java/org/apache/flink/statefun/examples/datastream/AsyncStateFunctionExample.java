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

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.statefun.flink.core.StatefulFunctionsConfig;
import org.apache.flink.statefun.flink.core.message.MessageFactoryType;
import org.apache.flink.statefun.flink.core.message.RoutableMessage;
import org.apache.flink.statefun.flink.core.message.RoutableMessageBuilder;
import org.apache.flink.statefun.flink.datastream.StatefulFunctionDataStreamBuilder;
import org.apache.flink.statefun.flink.datastream.StatefulFunctionEgressStreams;
import org.apache.flink.statefun.sdk.*;
import org.apache.flink.statefun.sdk.annotations.Persisted;
import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.state.PersistedAsyncValue;
import org.apache.flink.statefun.sdk.state.PersistedValue;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.runtime.streamrecord.VectorTimestamp;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class AsyncStateFunctionExample {

    private static final FunctionType GREET = new FunctionType("example", "greet");
    private static final FunctionType GREET2 = new FunctionType("example", "greet2");
    private static final FunctionType REMOTE_GREET = new FunctionType("example", "remote-greet");
    private static final FunctionType REMOTE_GREET2 = new FunctionType("example", "remote-greet2");
    private static final FunctionType GREET3 = new FunctionType("example", "greet3");
    private static final FunctionType REMOTE_GREET3 = new FunctionType("example", "remote-greet3");
    private static final FunctionType GREET4 = new FunctionType("example", "greet4");
    private static final FunctionType REMOTE_GREET4 = new FunctionType("example", "remote-greet4");
    private static final EgressIdentifier<String> GREETINGS =
            new EgressIdentifier<>("example", "out", String.class);
    private static final EgressIdentifier<String> GREETINGS2 =
            new EgressIdentifier<>("example", "out2", String.class);
    private static final EgressIdentifier<String> GREETINGS3 =
            new EgressIdentifier<>("example", "out3", String.class);
    private static final EgressIdentifier<String> GREETINGS4 =
            new EgressIdentifier<>("example", "out4", String.class);

    public static void main(String... args) throws Exception {

        // -----------------------------------------------------------------------------------------
        // obtain the stream execution env and create some data streams
        // -----------------------------------------------------------------------------------------

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(100);
        Configuration conf = new Configuration();
        conf.setString(ConfigConstants.JOB_MANAGER_WEB_LOG_PATH_KEY, "/tmp");
        conf.setString(ConfigConstants.TASK_MANAGER_LOG_PATH_KEY, "/tmp");
        conf.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true);

        //    conf.setInteger(RestOptions.PORT, 8050);

        //    StreamExecutionEnvironment env =
        // StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);

        env.getConfig().enableSysoutLogging();
        //    env.getConfig().setUseDynamicPartitioning(true);
        //    env.getConfig().setDispatchRebalanceEventInterval(10000);
        //    env.setParallelism(1);
        StatefulFunctionsConfig statefunConfig = StatefulFunctionsConfig.fromEnvironment(env);
        statefunConfig.setFactoryType(MessageFactoryType.WITH_KRYO_PAYLOADS);

        System.out.print(env.getConfig());
        DataStream<RoutableMessage> names =
                env.addSource(new NameSource())
                        .map(
                                name ->
                                        RoutableMessageBuilder.builder()
                                                .withTargetAddress(GREET4, "ALL")//name.getData()
                                                .withMessageBody(name)
                                                .build()); // .uid("source step");

        // -----------------------------------------------------------------------------------------
        // wire up stateful functions
        // -----------------------------------------------------------------------------------------

        //    StatefulFunctionEgressStreams out =
        StatefulFunctionDataStreamBuilder builder =
                StatefulFunctionDataStreamBuilder.builder("example")
                        .withDataStreamAsIngress(names)
                        .withFunctionProvider(GREET4, unused -> new MyFunction4())
                        /*.withRequestReplyRemoteFunction(
                                requestReplyFunctionBuilder(
                                        REMOTE_GREET, URI.create("http://localhost:5000/statefun"))
                                        .withMaxRequestDuration(Duration.ofSeconds(60))
                                        .withMaxNumBatchRequests(50000)) */
                        .withEgressId(GREETINGS4);

//                        .withFunctionProvider(GREET2, unused -> new MyFunction2())
//                        .withRequestReplyRemoteFunction(
//                                requestReplyFunctionBuilder(
//                                        REMOTE_GREET2, URI.create("http://localhost:5000/statefun"))
//                                        .withMaxRequestDuration(Duration.ofSeconds(60))
//                                        .withMaxNumBatchRequests(50000))
//                        .withEgressId(GREETINGS);

        //            .withFunctionProvider(GREET3, unused -> new MyFunction3())
        //            .withRequestReplyRemoteFunction(
        //                    requestReplyFunctionBuilder(
        //                            REMOTE_GREET3, URI.create("http://localhost:5001/statefun"))
        //                            .withMaxRequestDuration(Duration.ofSeconds(15))
        //                            .withMaxNumBatchRequests(500)
        //            )
        //            .withEgressId(GREETINGS3)
        //            .withFunctionProvider(GREET4, unused -> new MyFunction4())
        //            .withRequestReplyRemoteFunction(
        //                    requestReplyFunctionBuilder(
        //                            REMOTE_GREET4, URI.create("http://localhost:5001/statefun"))
        //                            .withMaxRequestDuration(Duration.ofSeconds(15))
        //                            .withMaxNumBatchRequests(500)
        //            )
        //            .withEgressId(GREETINGS4);

        StatefulFunctionEgressStreams out = builder.withConfiguration(statefunConfig).build(env);

        // -----------------------------------------------------------------------------------------
        // obtain the outputs
        // -----------------------------------------------------------------------------------------

        //    DataStream<String> output3 = out.getDataStreamForEgressId(GREETINGS3);
        //    DataStream<String> output4 = out.getDataStreamForEgressId(GREETINGS4);
        DataStream<String> output = out.getDataStreamForEgressId(GREETINGS4);

        // -----------------------------------------------------------------------------------------
        // the rest of the pipeline
        // -----------------------------------------------------------------------------------------

        //    output3
        //        .map(
        //            new RichMapFunction<String, String>() {
        //              @Override
        //              public String map(String value) {
        //                System.out.println(value);
        //                return "' output 3 " + value + "'";
        //              }
        //            })
        //        .addSink(new PrintSinkFunction<>());
        //
        //    output4
        //        .map(
        //                new RichMapFunction<String, String>() {
        //                  @Override
        //                  public String map(String value) {
        //                    System.out.println(value);
        //                    return "' output 4 " + value + "'";
        //                  }
        //                })
        //        .addSink(new PrintSinkFunction<>());
        output
                .map(
                        new RichMapFunction<String, String>() {
                            @Override
                            public String map(String value) {
                                System.out.println(value);
                                return "' output  " + value + "'";
                            }
                        })
                .addSink(new PrintSinkFunction<>());

        System.out.println("Plan 4 " + env.getExecutionPlan());
        // System.out.print(env.getStreamGraph("Flink Streaming Job", false));
        env.execute();
    }

    private static final class MyFunction implements StatefulFunction {
//    @Persisted
//    private final PersistedValue<Integer> seenCount = PersistedValue.of("seen", Integer.class);

        @Persisted
        private final PersistedAsyncValue<Integer> asyncSeenCount = PersistedAsyncValue.of("asyncSeen", Integer.class);

        @Override
        public void invoke(Context context, Object input) {

            if (input instanceof AsyncOperationResult) {
                AsyncOperationResult result = (AsyncOperationResult) input;
                if (result.successful()) {
//          if (((metadata)result.metadata()).asyncOrder!=0){
                    if (((metadata) result.metadata()).inputName.contains("Name")) {
//            synchronized (context){
//                  System.out.println("saltStr " + saltStr + " thread " + Thread.currentThread().getName());
                        System.out.println("MyFunction step 2 seen 1 " + result.metadata() + " asyncSeenCount " + result.value() + " thread " + Thread.currentThread().getName());
                        //context.send(GREET2, names[Math.abs(rnd.nextInt())%5], (Strinxeg)input);
//                        synchronized (context){
//                            context.send(GREET2, (String)((metadata) result.metadata()).inputName, (String)((metadata) result.metadata()).inputName);
//                        }
                        context.send(GREETINGS, String.format("MyFunction  seen: Hello %s at the %d-th time", ((metadata) (result.metadata())).inputName, ((metadata) (result.metadata())).asyncOrder));
//            }
                    } else {
                        System.out.println("MyFunction step 1 " + result.value() + " meta " + ((metadata) result.metadata()).inputName + " : " + ((metadata) result.metadata()).asyncOrder + " thread " + Thread.currentThread().getName());
                        CompletableFuture<String> seenFuture2 = CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "OK";
                        });
                        asyncSeenCount.setAsync((int) result.value());
                        synchronized (context) {
                            context.registerAsyncOperation(new metadata(((metadata) result.metadata()).inputName + " Name", 1), seenFuture2);
                        }
                    }
                }
            } else {
                String[] names = {"Stephan", "Igal", "Gordon", "Seth", "Marta"};

                System.out.println("MyFunction: " + input.toString());

                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 18) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                String saltStr = salt.toString();
//                CompletableFuture<Integer> seenFuture = CompletableFuture.supplyAsync(()-> {
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return 0;
//                });
                CompletableFuture<Integer> seenFuture = asyncSeenCount.getAsync(); //asyncSeenCount.updateAndGetAsync(MyFunction::increment);
                System.out.println("ceeating future complete thread " + Thread.currentThread().getName());
                synchronized (context) {
                    context.registerAsyncOperation(new metadata((String) input, 0), seenFuture);
                }
            }
        }

        class metadata {
            String inputName;
            int asyncOrder;

            metadata(String name, int order) {
                inputName = name;
                order = asyncOrder;
            }
        }

        private static int increment(@Nullable Integer n) {
            return n == null ? 1 : n + 1;
        }
    }

    private static final class MyFunction2 implements StatefulFunction {

        //    @Persisted
//    private final PersistedValue<Integer> seenCount2 = PersistedValue.of("seen2", Integer.class);
        @Persisted
        private final PersistedAsyncValue<Integer> asyncSeenCount2 = PersistedAsyncValue.of("asyncSeen2", Integer.class);


        @Override
        public void invoke(Context context, Object input) {

            if (input instanceof AsyncOperationResult) {
                AsyncOperationResult result = (AsyncOperationResult) input;
                if (result.successful()) {
//          if (((metadata)result.metadata()).asyncOrder!=0){
                    if (((metadata2) result.metadata()).inputName.contains("Name")) {
//            synchronized (context){
//                  System.out.println("saltStr " + saltStr + " thread " + Thread.currentThread().getName());
                        System.out.println("MyFunction2 step 2 seen 1 " + result.metadata() + " asyncSeenCount " + ((metadata2) (result.metadata())).asyncOrder + " thread " + Thread.currentThread().getName());
                        //context.send(GREET2, names[Math.abs(rnd.nextInt())%5], (Strinxeg)input);
                        //context.send(GREET2, (String)((MyFunction.metadata) result.metadata()).inputName, (String)((MyFunction.metadata) result.metadata()).inputName);
                        synchronized (context) {
                            context.send(GREETINGS, String.format("MyFunction2  seen: Hello %s at the %d-th time", ((metadata2) (result.metadata())).inputName, ((metadata2) (result.metadata())).asyncOrder));
                        }

//            }
                    } else {
                        System.out.println("MyFunction2 step 1 " + result.value() + " meta " + ((metadata2) result.metadata()).inputName + " : " + ((metadata2) result.metadata()).asyncOrder + " thread " + Thread.currentThread().getName());
                        CompletableFuture<String> seenFuture2 = CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "OK";
                        });//asyncSeenCount2.setAsync((int)result.value());
                        synchronized (context) {
                            context.registerAsyncOperation(new metadata2(((metadata2) result.metadata()).inputName + " Name", (int) result.value()), seenFuture2);
                        }
                    }
                }
            } else {
//        String[] names = {"Stephan", "Igal", "Gordon", "Seth", "Marta"};
//
//        System.out.println("MyFunction: " + input.toString());
//
//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//        while (salt.length() < 18) { // length of the random string.
//          int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//          salt.append(SALTCHARS.charAt(index));
//        }
//        String saltStr = salt.toString();
                CompletableFuture<Integer> seenFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 0;
                });
                //asyncSeenCount2.getAsync(); //asyncSeenCount.updateAndGetAsync(MyFunction::increment);
                System.out.println("MyFunction2 step 0 ceeating future complete thread " + Thread.currentThread().getName() + " input " + input);
                synchronized (context) {
                    //context.registerAsyncOperation(new metadata2((String) input, 0) , seenFuture);
                    context.send(GREETINGS, String.format("MyFunction2  seen: Hello %s at the %d-th time", input, 0));
                }
            }

//      int seen = seenCount2.updateAndGet(MyFunction2::increment);
//      String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//      StringBuilder salt = new StringBuilder();
//      Random rnd = new Random();
//      while (salt.length() < 18) { // length of the random string.
//        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//        salt.append(SALTCHARS.charAt(index));
//      }
//      context.send(GREETINGS, String.format("MyFunction2  seen: Hello %s at the %d-th time", input, seen));
        }

        class metadata2 {
            String inputName;
            int asyncOrder;

            metadata2(String name, int order) {
                inputName = name;
                order = asyncOrder;
            }
        }

        private static int increment(@Nullable Integer n) {
            return n == null ? 1 : n + 1;
        }


    }

    private static final class MyFunction3 implements StatefulFunction {

        @Persisted
        private final PersistedValue<Integer> seenCount3 = PersistedValue.of("seen3", Integer.class);

        @Override
        public void invoke(Context context, Object input) {
            int seen = seenCount3.updateAndGet(MyFunction3::increment);
            System.out.println("MyFunction3: " + input.toString());
            context.send(GREETINGS3, String.format("seen3: Hello %s at the %d-th time", input, seen));
        }

        private static int increment(@Nullable Integer n) {
            return n == null ? 1 : n + 1;
        }
    }

    public static class CountRecord {
        Integer count;
        int[] timeVector;

        public CountRecord(Integer count, int[] timeVector) {
            this.count = count;
            this.timeVector = timeVector;
        }
    }

    private static final class MyFunction4 implements StatefulFunction {

        @Persisted
        private final PersistedValue<CountRecord> seenCount4 = PersistedValue.of("seen4", CountRecord.class);

        final int operatorIndex = 1;
        final int maxOperators = 3;

        @Override
        public void invoke(Context context, Object input) {
            CountRecord seen = seenCount4.get();
            if (seen == null) {
                seen= new CountRecord(1,new int[maxOperators]);
            } else {
                seen.count++;
            }
            seen.timeVector = onIncomingMessage(context.getCurrentTime().getTimeVector(), seen.timeVector);
            seenCount4.set(seen);
            System.out.println("MyFunction4: " + input.toString());
            context.send(GREETINGS4, String.format("seen4: Hello %s at the %d-th time (timestamp-%s)", input, seen.count, Arrays.toString(seen.timeVector)));
        }

        private int[] onIncomingMessage(int[] timestampMessage, int[] currentTimestamp) {
            int[] newTimestamp = Arrays.copyOf(currentTimestamp, currentTimestamp.length);
            for (int i = 0; i < newTimestamp.length; ++i) {
                if (i == operatorIndex) {
                    newTimestamp[i]++;
                } else {
                    newTimestamp[i] = Integer.max(timestampMessage[i], newTimestamp[i]);
                }
            }
            return newTimestamp;
        }


    }

    private static final class NameSource implements SourceFunction<Message> {

        private static final long serialVersionUID = 1;

        private volatile boolean canceled;

        @Override
        public void run(SourceContext<Message> ctx) throws InterruptedException {
            String[] names = {"Stephan", "Igal", "Gordon", "Seth", "Marta"};
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int[] vtime = new int[3];
            vtime[0] = 0;
            vtime[1] = 0;
            vtime[2] = 0;
            int count = 0;
            while (true) {
                int index = count % 5; // random.nextInt(names.length);
                final String name = names[index];
                synchronized (ctx.getCheckpointLock()) {
                    if (canceled) {
                        return;
                    }
                    ++vtime[0];
                    System.out.println(name+" @Time:"+vtime[0]);
                    ctx.collect(new Message(name, vtime));
                }
                Thread.sleep(10);
                if (count++ > 200000) {
                    break;
                }
            }
        }

        @Override
        public void cancel() {
            canceled = true;
        }
    }
}

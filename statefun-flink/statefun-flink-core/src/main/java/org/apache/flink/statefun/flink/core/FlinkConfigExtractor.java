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

package org.apache.flink.statefun.flink.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

final class FlinkConfigExtractor {

  /**
   * Reflectively extracts Flink {@link Configuration} from a {@link StreamExecutionEnvironment}.
   * The Flink configuration contains Stateful Functions specific configurations. This is currently
   * a private method in the {@code StreamExecutionEnvironment} class.
   */
  static Configuration reflectivelyExtractFromEnv(StreamExecutionEnvironment env) {
    try {
      return (Configuration) getConfigurationMethod().invoke(env);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(
          "Failed to acquire the Flink configuration from the current environment", e);
    }
  }

  private static Method getConfigurationMethod() throws NoSuchMethodException {
    Method getConfiguration =
        StreamExecutionEnvironment.class.getDeclaredMethod("getConfiguration");
    getConfiguration.setAccessible(true);
    return getConfiguration;
  }
}

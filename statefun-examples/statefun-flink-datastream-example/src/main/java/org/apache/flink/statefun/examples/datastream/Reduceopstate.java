// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: reduceopstate.proto

package org.apache.flink.statefun.examples.datastream;

public final class Reduceopstate {
  private Reduceopstate() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_vt_ReduceOperatorState_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_vt_ReduceOperatorState_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023reduceopstate.proto\022\002vt\"<\n\023ReduceOpera" +
      "torState\022\021\n\twordCount\030\001 \001(\005\022\022\n\nvTimestam" +
      "p\030\002 \003(\005B1\n-org.apache.flink.statefun.exa" +
      "mples.datastreamP\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_vt_ReduceOperatorState_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_vt_ReduceOperatorState_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_vt_ReduceOperatorState_descriptor,
        new java.lang.String[] { "WordCount", "VTimestamp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: sinkopstate.proto

package org.apache.flink.statefun.examples.datastream;

public final class Sinkopstate {
  private Sinkopstate() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_vt_SinkOperatorState_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_vt_SinkOperatorState_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021sinkopstate.proto\022\002vt\"\'\n\021SinkOperatorS" +
      "tate\022\022\n\nvTimestamp\030\002 \003(\005B1\n-org.apache.f" +
      "link.statefun.examples.datastreamP\001"
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
    internal_static_vt_SinkOperatorState_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_vt_SinkOperatorState_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_vt_SinkOperatorState_descriptor,
        new java.lang.String[] { "VTimestamp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

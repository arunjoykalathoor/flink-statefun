// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mapopstate.proto

package org.apache.flink.statefun.examples.datastream;

public final class Mapopstate {
  private Mapopstate() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_vt_MapOperatorState_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_vt_MapOperatorState_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_vt_MapOperatorState_InputCountsEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_vt_MapOperatorState_InputCountsEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_vt_MapOperatorState_OutputCountsEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_vt_MapOperatorState_OutputCountsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020mapopstate.proto\022\002vt\"\256\002\n\020MapOperatorSt" +
      "ate\022\n\n\002ID\030\001 \001(\005\022\022\n\nvTimestamp\030\002 \003(\005\022:\n\013I" +
      "nputCounts\030\003 \003(\0132%.vt.MapOperatorState.I" +
      "nputCountsEntry\022<\n\014OutputCounts\030\004 \003(\0132&." +
      "vt.MapOperatorState.OutputCountsEntry\022\027\n" +
      "\014OperatorType\030\005 \001(\005:\0012\0322\n\020InputCountsEnt" +
      "ry\022\013\n\003key\030\001 \001(\005\022\r\n\005value\030\002 \001(\005:\0028\001\0323\n\021Ou" +
      "tputCountsEntry\022\013\n\003key\030\001 \001(\005\022\r\n\005value\030\002 " +
      "\001(\005:\0028\001B1\n-org.apache.flink.statefun.exa" +
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
    internal_static_vt_MapOperatorState_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_vt_MapOperatorState_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_vt_MapOperatorState_descriptor,
        new java.lang.String[] { "ID", "VTimestamp", "InputCounts", "OutputCounts", "OperatorType", });
    internal_static_vt_MapOperatorState_InputCountsEntry_descriptor =
      internal_static_vt_MapOperatorState_descriptor.getNestedTypes().get(0);
    internal_static_vt_MapOperatorState_InputCountsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_vt_MapOperatorState_InputCountsEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_vt_MapOperatorState_OutputCountsEntry_descriptor =
      internal_static_vt_MapOperatorState_descriptor.getNestedTypes().get(1);
    internal_static_vt_MapOperatorState_OutputCountsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_vt_MapOperatorState_OutputCountsEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

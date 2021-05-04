// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: reduceopstate.proto

package org.apache.flink.statefun.examples.datastream;

/**
 * Protobuf type {@code vt.ReduceOperatorState}
 */
public  final class ReduceOperatorState extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:vt.ReduceOperatorState)
    ReduceOperatorStateOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ReduceOperatorState.newBuilder() to construct.
  private ReduceOperatorState(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ReduceOperatorState() {
    vTimestamp_ = emptyIntList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ReduceOperatorState(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {
            bitField0_ |= 0x00000001;
            wordCount_ = input.readInt32();
            break;
          }
          case 16: {
            if (!((mutable_bitField0_ & 0x00000002) != 0)) {
              vTimestamp_ = newIntList();
              mutable_bitField0_ |= 0x00000002;
            }
            vTimestamp_.addInt(input.readInt32());
            break;
          }
          case 18: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000002) != 0) && input.getBytesUntilLimit() > 0) {
              vTimestamp_ = newIntList();
              mutable_bitField0_ |= 0x00000002;
            }
            while (input.getBytesUntilLimit() > 0) {
              vTimestamp_.addInt(input.readInt32());
            }
            input.popLimit(limit);
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        vTimestamp_.makeImmutable(); // C
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.apache.flink.statefun.examples.datastream.Reduceopstate.internal_static_vt_ReduceOperatorState_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.apache.flink.statefun.examples.datastream.Reduceopstate.internal_static_vt_ReduceOperatorState_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.apache.flink.statefun.examples.datastream.ReduceOperatorState.class, org.apache.flink.statefun.examples.datastream.ReduceOperatorState.Builder.class);
  }

  private int bitField0_;
  public static final int WORDCOUNT_FIELD_NUMBER = 1;
  private int wordCount_;
  /**
   * <code>optional int32 wordCount = 1;</code>
   */
  public boolean hasWordCount() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional int32 wordCount = 1;</code>
   */
  public int getWordCount() {
    return wordCount_;
  }

  public static final int VTIMESTAMP_FIELD_NUMBER = 2;
  private com.google.protobuf.Internal.IntList vTimestamp_;
  /**
   * <code>repeated int32 vTimestamp = 2;</code>
   */
  public java.util.List<java.lang.Integer>
      getVTimestampList() {
    return vTimestamp_;
  }
  /**
   * <code>repeated int32 vTimestamp = 2;</code>
   */
  public int getVTimestampCount() {
    return vTimestamp_.size();
  }
  /**
   * <code>repeated int32 vTimestamp = 2;</code>
   */
  public int getVTimestamp(int index) {
    return vTimestamp_.getInt(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeInt32(1, wordCount_);
    }
    for (int i = 0; i < vTimestamp_.size(); i++) {
      output.writeInt32(2, vTimestamp_.getInt(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, wordCount_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < vTimestamp_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt32SizeNoTag(vTimestamp_.getInt(i));
      }
      size += dataSize;
      size += 1 * getVTimestampList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.apache.flink.statefun.examples.datastream.ReduceOperatorState)) {
      return super.equals(obj);
    }
    org.apache.flink.statefun.examples.datastream.ReduceOperatorState other = (org.apache.flink.statefun.examples.datastream.ReduceOperatorState) obj;

    if (hasWordCount() != other.hasWordCount()) return false;
    if (hasWordCount()) {
      if (getWordCount()
          != other.getWordCount()) return false;
    }
    if (!getVTimestampList()
        .equals(other.getVTimestampList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasWordCount()) {
      hash = (37 * hash) + WORDCOUNT_FIELD_NUMBER;
      hash = (53 * hash) + getWordCount();
    }
    if (getVTimestampCount() > 0) {
      hash = (37 * hash) + VTIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + getVTimestampList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.apache.flink.statefun.examples.datastream.ReduceOperatorState prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code vt.ReduceOperatorState}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:vt.ReduceOperatorState)
      org.apache.flink.statefun.examples.datastream.ReduceOperatorStateOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.apache.flink.statefun.examples.datastream.Reduceopstate.internal_static_vt_ReduceOperatorState_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.apache.flink.statefun.examples.datastream.Reduceopstate.internal_static_vt_ReduceOperatorState_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.apache.flink.statefun.examples.datastream.ReduceOperatorState.class, org.apache.flink.statefun.examples.datastream.ReduceOperatorState.Builder.class);
    }

    // Construct using org.apache.flink.statefun.examples.datastream.ReduceOperatorState.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      wordCount_ = 0;
      bitField0_ = (bitField0_ & ~0x00000001);
      vTimestamp_ = emptyIntList();
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.apache.flink.statefun.examples.datastream.Reduceopstate.internal_static_vt_ReduceOperatorState_descriptor;
    }

    @java.lang.Override
    public org.apache.flink.statefun.examples.datastream.ReduceOperatorState getDefaultInstanceForType() {
      return org.apache.flink.statefun.examples.datastream.ReduceOperatorState.getDefaultInstance();
    }

    @java.lang.Override
    public org.apache.flink.statefun.examples.datastream.ReduceOperatorState build() {
      org.apache.flink.statefun.examples.datastream.ReduceOperatorState result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.apache.flink.statefun.examples.datastream.ReduceOperatorState buildPartial() {
      org.apache.flink.statefun.examples.datastream.ReduceOperatorState result = new org.apache.flink.statefun.examples.datastream.ReduceOperatorState(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.wordCount_ = wordCount_;
        to_bitField0_ |= 0x00000001;
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        vTimestamp_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000002);
      }
      result.vTimestamp_ = vTimestamp_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.apache.flink.statefun.examples.datastream.ReduceOperatorState) {
        return mergeFrom((org.apache.flink.statefun.examples.datastream.ReduceOperatorState)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.apache.flink.statefun.examples.datastream.ReduceOperatorState other) {
      if (other == org.apache.flink.statefun.examples.datastream.ReduceOperatorState.getDefaultInstance()) return this;
      if (other.hasWordCount()) {
        setWordCount(other.getWordCount());
      }
      if (!other.vTimestamp_.isEmpty()) {
        if (vTimestamp_.isEmpty()) {
          vTimestamp_ = other.vTimestamp_;
          bitField0_ = (bitField0_ & ~0x00000002);
        } else {
          ensureVTimestampIsMutable();
          vTimestamp_.addAll(other.vTimestamp_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.apache.flink.statefun.examples.datastream.ReduceOperatorState parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.apache.flink.statefun.examples.datastream.ReduceOperatorState) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private int wordCount_ ;
    /**
     * <code>optional int32 wordCount = 1;</code>
     */
    public boolean hasWordCount() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional int32 wordCount = 1;</code>
     */
    public int getWordCount() {
      return wordCount_;
    }
    /**
     * <code>optional int32 wordCount = 1;</code>
     */
    public Builder setWordCount(int value) {
      bitField0_ |= 0x00000001;
      wordCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 wordCount = 1;</code>
     */
    public Builder clearWordCount() {
      bitField0_ = (bitField0_ & ~0x00000001);
      wordCount_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.IntList vTimestamp_ = emptyIntList();
    private void ensureVTimestampIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        vTimestamp_ = mutableCopy(vTimestamp_);
        bitField0_ |= 0x00000002;
       }
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public java.util.List<java.lang.Integer>
        getVTimestampList() {
      return ((bitField0_ & 0x00000002) != 0) ?
               java.util.Collections.unmodifiableList(vTimestamp_) : vTimestamp_;
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public int getVTimestampCount() {
      return vTimestamp_.size();
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public int getVTimestamp(int index) {
      return vTimestamp_.getInt(index);
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public Builder setVTimestamp(
        int index, int value) {
      ensureVTimestampIsMutable();
      vTimestamp_.setInt(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public Builder addVTimestamp(int value) {
      ensureVTimestampIsMutable();
      vTimestamp_.addInt(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public Builder addAllVTimestamp(
        java.lang.Iterable<? extends java.lang.Integer> values) {
      ensureVTimestampIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, vTimestamp_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int32 vTimestamp = 2;</code>
     */
    public Builder clearVTimestamp() {
      vTimestamp_ = emptyIntList();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:vt.ReduceOperatorState)
  }

  // @@protoc_insertion_point(class_scope:vt.ReduceOperatorState)
  private static final org.apache.flink.statefun.examples.datastream.ReduceOperatorState DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.apache.flink.statefun.examples.datastream.ReduceOperatorState();
  }

  public static org.apache.flink.statefun.examples.datastream.ReduceOperatorState getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  @java.lang.Deprecated public static final com.google.protobuf.Parser<ReduceOperatorState>
      PARSER = new com.google.protobuf.AbstractParser<ReduceOperatorState>() {
    @java.lang.Override
    public ReduceOperatorState parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ReduceOperatorState(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ReduceOperatorState> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ReduceOperatorState> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.apache.flink.statefun.examples.datastream.ReduceOperatorState getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


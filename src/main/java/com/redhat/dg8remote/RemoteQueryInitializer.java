package com.redhat.dg8remote;

import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

// book.proto file is generated in the compilation automatically
// book_sample must be defined as indexed-entity in the RHDG server cache "books"
@AutoProtoSchemaBuilder(
	      includeClasses = {
	            Book.class
	      },
	      schemaFileName = "book.proto",
	      schemaFilePath = "proto/",
	      schemaPackageName = "book_sample")
public interface RemoteQueryInitializer extends SerializationContextInitializer {

}

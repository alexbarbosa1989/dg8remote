package com.redhat.dg8remote.controller;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

// book_sample must be defined as indexed-entity in the RHDG server cache "books"
@AutoProtoSchemaBuilder(
	      includeClasses = {
	            Book.class
	      },
	      schemaPackageName = "book_sample")
public interface RemoteQueryInitializer extends GeneratedSchema {

}

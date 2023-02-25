package com.redhat.dg8remote;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
	      includeClasses = {
	            Book.class
	      },
	      schemaPackageName = "book_sample")
public interface RemoteQueryInitializer extends GeneratedSchema {
}

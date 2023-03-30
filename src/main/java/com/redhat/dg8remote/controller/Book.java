package com.redhat.dg8remote.controller;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Indexed
public class Book {
	@ProtoField(number = 1)
	@Keyword(projectable = true, sortable = true, normalizer = "lowercase", indexNullAs = "unnamed", norms = false)
	String title;
 
	@ProtoField(number = 2)
	@Keyword(projectable = true, sortable = true, normalizer = "lowercase", indexNullAs = "unnamed", norms = false)
	String description;
 
	@ProtoField(number = 3, defaultValue = "-1")
	@Basic
	int publicationYear;

	@ProtoFactory
	Book(String title, String description, int publicationYear) {
		this.title = title;
		this.description = description;
		this.publicationYear = publicationYear;
	}

}

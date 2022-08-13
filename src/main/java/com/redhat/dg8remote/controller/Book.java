package com.redhat.dg8remote.controller;

import java.util.Objects;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@ProtoDoc("@Indexed")
public class Book {
	@ProtoDoc("@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)")
	@ProtoField(number = 1)
	String title;

	@ProtoDoc("@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)")
	@ProtoField(number = 2)
	String description;

	@ProtoDoc("@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)")
	@ProtoField(number = 3, defaultValue = "0")
	int publicationYear;

	@ProtoFactory
	Book(String title, String description, int publicationYear) {
		this.title = title;
		this.description = description;
		this.publicationYear = publicationYear;
	}

	@Override
   	public String toString() {
      	return "Book{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", publicationYear='" + publicationYear + '\'' +
            '}';
   	}

	@Override
	public int hashCode() {
		  return Objects.hash(title, description,publicationYear);
	}

	@Override
   	public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Book that = (Book) o;
      return Objects.equals(title, that.title) &&
             Objects.equals(description,that.description) &&
			 Objects.equals(publicationYear, that.publicationYear);
   }

}

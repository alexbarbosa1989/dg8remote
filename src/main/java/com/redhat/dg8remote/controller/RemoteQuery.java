package com.redhat.dg8remote.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteQuery {

	private final RemoteCacheManager remoteCacheManager;

	@Autowired
	public RemoteQuery(RemoteCacheManager cacheManager) {
		this.remoteCacheManager = cacheManager;
		// Upload the generated schema in the server
		RemoteCache<String, String> metadataCache = this.remoteCacheManager
				.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
		GeneratedSchema schema = new RemoteQueryInitializerImpl();
		metadataCache.put(schema.getProtoFileName(), schema.getProtoFile());
	}

	@GetMapping(value = "/query-cache", produces = {
			"text/plain; charset=UTF-8" })
	public String queryCache() {

		String queryResult = "";

		// Obtain the 'books' remote cache
		RemoteCache<String, Book> remoteCache = remoteCacheManager.getCache("books");

		// Add some Books
		Map<String, Book> books = new HashMap<>();
		books.put("1", new Book("Infinispan in Action", "Learn Infinispan with using it", 2015));
		books.put("2", new Book("Cloud-Native Applications with Java and Quarkus",
				"Build robust and reliable cloud applications", 2019));

		remoteCache.putAll(books);

		// Execute a full-text query
		QueryFactory queryFactory = Search.getQueryFactory(remoteCache);
		Query<Book> query = queryFactory.create("FROM book_sample.Book WHERE title:'java'");

		List<Book> list = query.execute().list(); // Voila! We have our book back from the cache!

		// iterate query result list for verifying
		for (Book itbook : list) {
			System.out.println(itbook.title);
			System.out.println(itbook.publicationYear);
			queryResult = queryResult + " - " + itbook.title + " - " + itbook.publicationYear;
		}

		return queryResult;

	}

}

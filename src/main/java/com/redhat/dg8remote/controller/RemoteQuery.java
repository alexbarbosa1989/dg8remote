package com.redhat.dg8remote.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
//import org.infinispan.client.hotrod.configuration.SaslQop;
//import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
//import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import org.infinispan.spring.common.provider.SpringCache;
import org.infinispan.spring.remote.provider.SpringRemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteQuery {
	
	private final RemoteCacheManager remoteCacheManager;
	
	@Autowired
	public RemoteQuery(RemoteCacheManager cacheManager) {
	    this.remoteCacheManager = cacheManager;
	}
	
	
	@GetMapping(value = "/query-cache/{cacheName}", produces = {
			"application/json; charset=UTF-8" })
	public String queryCache(@PathVariable String cacheName) {
			
	      String queryResult = "";
	      try{
	      // Grab the generated protobuf schema and registers in the server.
	      Path proto = Paths.get(RemoteQuery.class.getClassLoader()
	            .getResource("proto/book.proto").toURI());
	      String protoBufCacheName = ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;
	      remoteCacheManager.getCache(protoBufCacheName).put("book.proto", Files.readString(proto));

	      // Obtain the 'books' remote cache
	      RemoteCache<Object, Object> remoteCache = remoteCacheManager.getCache(cacheName);

	      // Add some Books
	      Book book1 = new Book("Infinispan in Action", "Learn Infinispan with using it", 2015);
	      Book book2 = new Book("Cloud-Native Applications with Java and Quarkus", "Build robust and reliable cloud applications", 2019);

	      remoteCache.put(1, book1);
	      remoteCache.put(2, book2);

	      // Execute a full-text query
	      QueryFactory queryFactory = Search.getQueryFactory(remoteCache);
	      Query<Book> query = queryFactory.create("FROM book_sample.Book WHERE title:'java'");

	      List<Book> list = query.execute().list(); // Voila! We have our book back from the cache!
	      
	      //iterate query result list for verifying
	      for(Book itbook : list) {
	    	  System.out.println(itbook.title);
	    	  System.out.println(itbook.publicationYear);
	    	  queryResult=queryResult+" - "+itbook.title+" - "+itbook.publicationYear;
	      }
	      }catch (Exception e){
	      		queryResult="Error processing the query";
	      }
	      
	      return queryResult;
	      
	}

}

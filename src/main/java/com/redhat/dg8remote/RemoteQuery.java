package com.redhat.dg8remote;

import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;


public class RemoteQuery {
	public static void main(String[] args) throws Exception {
	      ConfigurationBuilder clientBuilder = new ConfigurationBuilder();
	      // RemoteQueryInitializerImpl is generated
	      clientBuilder.addServer().host("127.0.0.1").port(11222)
	            .security().authentication().username("admin").password("admin")
	            .addContextInitializers(new RemoteQueryInitializerImpl());

	      RemoteCacheManager remoteCacheManager = new RemoteCacheManager(clientBuilder.build());
	      
		   // Create and add the Protobuf schema in the server
		   addBookSchema(remoteCacheManager);

	      // Obtain the 'books' remote cache
	      RemoteCache<Long, Book> remoteCache = remoteCacheManager.getCache("books");

	      // Add some Books
	      Book book1 = new Book("Infinispan in Action", "Learn Infinispan with using it", 2015);
	      Book book2 = new Book("Cloud-Native Applications with Java and Quarkus", "Build robust and reliable cloud applications", 2019);

	      remoteCache.put(1L, book1);
	      remoteCache.put(2L, book2);

	      // Execute a full-text query
	      QueryFactory queryFactory = Search.getQueryFactory(remoteCache);
	      Query<Book> query = queryFactory.create("FROM book_sample.Book WHERE publicationYear = :year");

	      query.setParameter("year",2015);

	      List<Book> list = query.execute().list(); // Voila! We have our book back from the cache!
	     
	      System.out.println("Query resultset:");
	      //iterate query result list for verifying
	      for(Book itbook : list) {
	    	  System.out.println(itbook.title);
	    	  System.out.println(itbook.publicationYear);
	      }
	   }

	   private static void addBookSchema(RemoteCacheManager cacheManager) {
		// Retrieve metadata cache
		RemoteCache<String, String> metadataCache =
			  cacheManager.getCache(PROTOBUF_METADATA_CACHE_NAME);
  
		// Define the new schema on the server too
		GeneratedSchema schema = new RemoteQueryInitializerImpl();
		metadataCache.put(schema.getProtoFileName(), schema.getProtoFile());
	 }

}


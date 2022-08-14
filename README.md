# Remote Query in Data Grid 8 using protobuf schema

Based on Red Hat Data Grid 8 remote quiery example https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.2/html/data_grid_developer_guide/search_api#remote_queries-querying


1. Create the **books** cache in your Data Grid installation :
~~~
    <cache-container>
        <replicated-cache mode="SYNC" name="books">
            <indexing enabled="true">
                <indexed-entities>
                    <indexed-entity>book_sample.Book</indexed-entity>
                </indexed-entities>
            </indexing>
        </replicated-cache>
    </cache-container>
~~~

Json format:
~~~
{
  "replicated-cache": {
    "mode": "SYNC",
    "indexing": {
      "indexed-entities": [
        "book_sample.Book"
      ],
      "enabled": true
    }
  }
}
~~~
**IMPORTANT:** the created cache must include the **book_sample.Book** indexed-entity

2. Create an user and start the data grid instance:
~~~
${RHDG_HOME}/bin/cli user create admin -p admin 
~~~
**IMPORTANT:** the created user credentials will be set on the ConfigurationBuilder in RemoteQuery.java 

3. Clone the project:
~~~
git clone https://github.com/alexbarbosa1989/dg8remote.git
~~~

4. Build the project:
~~~
mvn clean install
~~~

5. Execute the remote query:
~~~
curl -X GET http://${HOST_URL}/redhat/query-cache/
~~~

It will show as output the result list  from the query made on RemoteQuery.java, eg:
~~~
Cloud-Native Applications with Java and Quarkus
2019
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.617 s
[INFO] Finished at: 2022-01-15T17:20:43-05:00
[INFO] ------------------------------------------------------------------------
~~~


# Remote Query in Data Grid 8 using protobuf schema

Based on Red Hat Data Grid 8 remote quiery example https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.2/html/data_grid_developer_guide/search_api#remote_queries-querying


1. Create the **books** cache in your Data Grid installation :
~~~
            <replicated-cache name="books" mode="SYNC" statistics="true">
                <encoding>
                    <key media-type="application/x-protostream"/>
                    <value media-type="application/x-protostream"/>
                </encoding>
                <indexing enabled="true">
                    <indexed-entities>
                        <indexed-entity>book_sample.Book</indexed-entity>
                    </indexed-entities>
                </indexing>
            </replicated-cache>
~~~

Json format:
~~~
{

  "replicated-cache": {
    "mode": "SYNC",
    "statistics": true,
    "encoding": {
      "key": {
        "media-type": "application/x-protostream"
      },
      "value": {
        "media-type": "application/x-protostream"
      }
    },
    "indexing": {
      "enabled": true,
      "indexed-entities": [
        "book_sample.Book"
      ]
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

5. Execute the springboot application jar:

~~~
java -jar target/remote-query-0.0.1-SNAPSHOT.jar 
~~~

6. Execute the remote query:
~~~
curl -X GET http://${HOST_URL}/redhat/query-cache/
~~~

It will show as output the result list  from the query made on RemoteQuery.java, eg:
~~~
- Book title 10 - 2022
~~~

In the springboot log will show the query output as well
~~~
2022-08-14 14:51:29.446  INFO 8270 --- [           main] c.r.dg8remote.RemoteQueryApplication     : Started RemoteQueryApplication in 3.434 seconds (JVM running for 3.886)
...
Book title 10
2022
~~~

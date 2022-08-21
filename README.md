# Remote Query in Data Grid 8 using protobuf schema

Based on Red Hat Data Grid 8 remote query example https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.3/html/querying_data_grid_caches/query-remote#querying-hot-rod_query-remote


1. Create an Openshift project and deploy a Data Grid Cluster via Operator. Here is a CR YAML example used for this example:
~~~
apiVersion: infinispan.org/v1
kind: Infinispan
metadata:
  name: example-infinispan
  namespace: dgtest
spec:
  expose:
    type: LoadBalancer
  service:
    type: DataGrid
  replicas: 2
~~~

2. Create the **books** cache in your Data Grid installation :
XML format:
~~~
            <replicated-cache name="books" mode="SYNC" statistics="true">
                <encoding>
                    <key media-type="application/x-protostream"/>
                    <value media-type="application/x-protostream"/>
                </encoding>
                <indexing>
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
      "indexed-entities": [
        "book_sample.Book"
      ]
    }
  }
}
~~~
**IMPORTANT:** the created **books** cache must include the **book_sample.Book** indexed-entity.

3. Clone the project:
~~~
git clone https://github.com/alexbarbosa1989/dg8remote.git
~~~

**IMPORTANT:** the Data Grid credentials must be set on the **application.properties** file (/src/main/resources/application.properties) before build the project

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

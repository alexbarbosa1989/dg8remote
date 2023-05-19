# Remote Query in Data Grid 8 using protobuf schema
# Important: current branch implements [new Data Grid native indexing annotations](https://github.com/alexbarbosa1989/dg8remote#changes-summary), which is designed to correctly on Data Grid 8.4.0+. For previus Data Grid versions refers the repository branch [openshift](https://github.com/alexbarbosa1989/dg8remote/tree/openshift) 
# For Openshift deployment

For current branch, most of the steps remain as-is in the Red Hat Developer blog: https://developers.redhat.com/articles/2022/11/30/remotely-query-indexed-caches-data-grid-8

**In order to deploy current branch project, there should be make below described Steps Chages**
1. In step [Configuring the application to use the data grid cluster](https://developers.redhat.com/articles/2022/11/30/remotely-query-indexed-caches-data-grid-8#configuring_the_application_to_use_the_data_grid_cluster):
Instead of clone the **openshift** brach, must clone **RHDG_8.4** branch:

**Old step:**
~~~
git clone -b openshift https://github.com/alexbarbosa1989/dg8remote
~~~
**New step:**
~~~
git clone -b RHDG_8.4 https://github.com/alexbarbosa1989/dg8remote
~~~

2. In step [Deploying the remote query application in OpenShift](https://developers.redhat.com/articles/2022/11/30/remotely-query-indexed-caches-data-grid-8#deploying_the_remote_query_application_in_openshift):
Instead of using **fabric8** maven comand, should use **JKube** maven commands for aplication deployment on the Openshift environment:

**Old step:**
~~~
mvn clean fabric8:deploy -Popenshift
~~~
**New step:**
~~~
mvn clean package oc:build -Popenshift
mvn oc:apply -Popenshift
~~~

The other steps remains exactly the same as in the [Red Hat Developer blog](https://developers.redhat.com/articles/2022/11/30/remotely-query-indexed-caches-data-grid-8)

# For local deployment
1. Deploy a local Data Grid 8.4.0 or upper.

2. Create the **books** cache in your Data Grid installation :
XML format:
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
  "books": {
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
}
~~~
**IMPORTANT:** the created **books** cache must include the **book_sample.Book** indexed-entity.

3. Clone the project:
~~~
git clone -b openshift https://github.com/alexbarbosa1989/dg8remote.git
~~~

**IMPORTANT:** the Data Grid credentials must be set on the **application.properties** file (/src/main/resources/application.properties) before build the project and make any request

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
2023-04-05 15:42:34.767  INFO 1 --- [           main] c.r.dg8remote.RemoteQueryApplication     : Started RemoteQueryApplication in 5.103 seconds (JVM running for 5.864)
...
Book title 20
2010
~~~

# Changes summary:
- Implementing new Data Grid native indexing annotations (https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4/html/red_hat_data_grid_8.4_release_notes/rhdg-releases#data_grid_native_indexing_annotations)
- Upgrade to RHDG 8.4 hotrod connector. (Infinispan 14.0.6.Final-redhat-00001)
- Upgrade protostream 4 version. (Protostream 4.6.0.Final-redhat-00001)
- Upgrade to Spring Boot starter 2.7.0
- Removed fabric8 maven plugin (Deprecated)
- Adding JKube maven plugin for Openshift deployment.

# dg8remote
Remote Query in Data Grid 8 using protobuf schema

Based on Red Hat Data Grid 8 remote quiery example https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.2/html/data_grid_developer_guide/search_api#remote_queries-querying


1. Create the **books** cache in your Data Grid installation:
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
**IMPORTANT:** the created cache must include the **book_sample.Book** indexed.entity
3. Clone the project:
~~~
git clone https://github.com/alexbarbosa1989/dg8remote.git
~~~
3. Build the project:
~~~
mvn clean install
~~~

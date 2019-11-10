# querytool

query tool is a cli-based tool to enable the arm treasure data testing easier
 

# New Features!
```sh
query tool using the Treasure Data Java Client Library that allows the user to specify below inputs:
-required: database name 'db_name'
-required: table name 'table_name'
-optional: comma separated list of columns 'col_list' as string (e.g.
'column1,column2,column3’). If not specified, all columns are selected.
-optional: minimum timestamp 'min_time' in unix timestamp or 'NULL'
-optional: maximum timestamp 'max_time' in unix timestamp or 'NULL'.
-Obviously 'max_time' must be greater than 'min_time' or NULL.
-optional: query engine ‘engine’: 'hive' or 'presto'. Defaults to ‘presto’.
-optional: output format ‘format’: ‘csv’ or ‘tabular'. Defaults to ‘tabular’.
-optional: a query limit ‘limit’: ‘100’. If not specified, all records are 
```


This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.

### Tech

query tool uses a number of open source projects to work properly:

* [picocli] - Picocli aims to be the easiest way to create rich command line applications that can run on and off the JVM
* [testng] - TestNG is a testing framework designed to simplify a broad range of testing needs, from unit testing (testing a class in isolation of the others) to integration testing (testing entire systems made of several classes, several packages and even several external frameworks, such as application servers).



### Installation

clone
install maven
java8+

Install the dependencies for arm treasure data like api key as per instructions given on vendor site.

```sh
$ cd project_path
$ mvn clean install
```

### command parameters
```sh
$ -f csv -e hive -c 'my_col1,my_col2,my_col5'
-m 1427347140 -M 1 -e / --engine is optional and specifies the query engine: ‘presto’ by default
```
### options while running project
```sh
● -f / --format is optional and specifies the output format: tabular by default
● -c / --column is optional and specifies the comma separated list of columns to restrict the
result to. Read all columns if not specified.
● -l / --limit is optional and specifies the limit of records returned. Read all records if not
specified.
● -m / --min is optional and specifies the minimum timestamp: NULL by default
● -M / --MAX is optional and specifies the maximum timestamp: NULL by default
● -e / --engine is optional and specifies the query engine: ‘presto’ by default
```
### options configured
```sh
● all the above required options are configured into 'tests.xml' file for testing all the required inputs
● 'tests.xml' file is invoked by pom.xml
```



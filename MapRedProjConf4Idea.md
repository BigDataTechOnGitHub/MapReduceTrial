## Config Project 4 Idea

* download and decompress hadoop binary package in your local storage
```bash
wget http://www.webhostingreviewjam.com/mirror/apache/hadoop/core/stable/hadoop-2.7.1.tar.gz
```
* create new maven project or normal project
* right click ont the root project
* click open module settings 
* Project Settings -> Libraries ->  add Java
* add following jar directories
```
$HADOOP_DIR/share/hadoop/common
$HADOOP_DIR/share/hadoop/hdfs
$HADOOP_DIR/share/hadoop/mapreduce
$HADOOP_DIR/share/hadoop/tools
$HADOOP_DIR/share/hadoop/yarn
$HADOOP_DIR/share/hadoop/common/lib
```
* Project Settings -> Artifacts -> Add JAR
* select your mapred class
* select jar type
* Check Build on Make
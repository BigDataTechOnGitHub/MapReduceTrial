# MapReduceTrial

 * this trial is following Durga Gadiraju's video:
 [https://www.youtube.com/watch?v=nPRY-qGaAMs&list=PLf0swTFhTI8rnNRnVz6n-f1d3ZCDtgqRq]

 * file to mapreduce:
 [https://raw.githubusercontent.com/draculavlad/MapReduceTrial/master/deckofcards.txt]


## Setting up local hadoop cluster##

* I got two vm:

 *domain name* | *ip* | *role*
------------|------------|------------
`yu0.hdp.co`|`192.168.212.177`|`master`
`yu1.hdp.co`|`192.168.212.181`|`slave`

## setup hadoop on master, for my instance, yu0.hdp.co
* download hadoop 2.7.1 binary package & config system env
```bash
cd /home
wget http://www.webhostingreviewjam.com/mirror/apache/hadoop/core/stable/hadoop-2.7.1.tar.gz
tar zxf hadoop-2.7.1.tar.gz
mv hadoop-2.7.1 hadoop
echo "export HADOOP_HOME=/home/hadoop" >> /etc/profile
echo "export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop" >> /etc/profile
echo "export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin"
echo "export HADOOP_OPTS="$HADOOP_OPTS -Djava.library.path=$HADOOP_HOME/lib/" >> /etc/profile
echo "export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native" >> /etc/profile
echo "export HADOOP_ROOT_LOGGER=DEBUG,console"
echo "export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native"
source /etc/profile
```
* config&create hadoop config file and initialize file:
* core-site.xml, hdfs-site.xml, yarn-site.xml, mapred-site.xml, slaves, hadoop-env.sh, mapred-env.sh, yarn-env.sh

* core-site.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
          <name>hadoop.tmp.dir</name>
          <value>/home/hdp/tmp</value>
          <description>Temporary Directory.</description>
        </property>

        <property>
          <name>fs.defaultFS</name>
          <value>hdfs://yu0.hdp.co:9000</value>
          <description>Use HDFS as file storage engine</description>
        </property>
</configuration>
```

* hdfs-site.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
         <name>dfs.replication</name>
         <value>2</value>
         <description>Default block replication.
          The actual number of replications can be specified when the file is created.
          The default is used if replication is not specified in create time.
         </description>
        </property>
        <property>
         <name>dfs.namenode.secondary.http-address</name>
         <value>yu0.hdp.co:9001</value>
        </property>
        <property>
         <name>dfs.namenode.name.dir</name>
         <value>/home/hdp/hdfs/name</value>
         <description>Determines where on the local filesystem the DFS name node should store the name table(fsimage). If this is a comma-delimited list of directories then the name table is replicated in all of the directories, for redundancy.
         </description>
        </property>
        <property>
         <name>dfs.datanode.data.dir</name>
         <value>/home/hdp/hdfs/data</value>
         <description>Determines where on the local filesystem an DFS data node should store its blocks. If this is a comma-delimited list of directories, then data will be stored in all named directories, typically on different devices. Directories that do not exist are ignored.
         </description>
        </property>

</configuration>
```

* yarn-site.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
   <property>
      <name>yarn.nodemanager.aux-services</name>
      <value>mapreduce_shuffle</value>
   </property>
   <property>
      <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
      <value>org.apache.hadoop.mapred.ShuffleHandler</value>
   </property>
   <property>
      <name>yarn.resourcemanager.address</name>
      <value>yu0.hdp.co:8032</value>
   </property>
   <property>
      <name>yarn.resourcemanager.scheduler.address</name>
      <value>yu0.hdp.co:8030</value>
   </property>
   <property>
      <name>yarn.resourcemanager.resource-tracker.address</name>
      <value>yu0.hdp.co:8031</value>
   </property>
   <property>
      <name>yarn.resourcemanager.admin.address</name>
      <value>yu0.hdp.co:8033</value>
   </property>
   <property>
      <name>yarn.resourcemanager.webapp.address</name>
      <value>yu0.hdp.co:8088</value>
   </property>
</configuration>
```

* mapred-site.xml:
```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
         <name>mapreduce.framework.name</name>
         <value>yarn</value>
        </property>
        <property>
         <name>mapreduce.jobhistory.address</name>
         <value>yu0.hdp.co:10020</value>
        </property>
        <property>
         <name>mapreduce.jobhistory.webapp.address</name>
         <value>yu0.hdp.co:19888</value>
        </property>
</configuration>
```

* slaves:
```properties
yu0.hdp.co
yu1.hdp.co
```

* set JAVA_HOME variable in hadoop-env.sh, mapred-env.sh, yarn-env.sh
```bash
source hadoop-env.sh
source mapred-env.sh
source yarn-env.sh
```
* copy this to your other host
```bash
scp -r /home/hadoop root@yu1.hdp.co:/home/hadoop
```

* on your other host, update /etc/profile and source those env file

* on your master, initialze for namenode config
```
cd /home
mkdir -p hdp/tmp hdp/hdfs/name hdp/hdfs/data
hdfs namenode -format
```

## start your cluster
```bash
start-dfs.sh
start-yarn.sh
```

## check running status:
* on master
```bash
jps
$pid ResourceManager
$pid NodeManager
$pid SecondaryNameNode
$pid NameNode
$pid DataNode
$pid Jps
```

* not on master
```bash
jps
$pid NodeManager
$pid DataNode
$pid Jps
```




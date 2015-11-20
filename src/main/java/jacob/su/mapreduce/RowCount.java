package jacob.su.mapreduce;

import java.io.IOException;
import java.util.Iterator;


import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;


/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
public class RowCount extends Configured implements Tool {


    @Override
    public int run(String[] strings) throws Exception {
        JobConf jobConf = new JobConf(getConf());

        jobConf.setMapperClass(RecordMapper.class);
        jobConf.setMapOutputKeyClass(Text.class);
        jobConf.setMapOutputValueClass(IntWritable.class);
        jobConf.setReducerClass(NoKeyRecordCountReducer.class);

        String arg0 = "hdfs://yu0.hdp.co:9000/test/largedeck.txt";
        String arg1 = "hdfs://yu0.hdp.co:9000/test/largedeck0_result";

        FileInputFormat.setInputPaths(jobConf, new Path(arg0));
        FileOutputFormat.setOutputPath(jobConf, new Path(arg1));
        //加载配置文件, 可以注释掉
        Job job = Job.getInstance(jobConf, "Row Count using built in mapper and reducer");
        job.setJarByClass(this.getClass());

        return job.waitForCompletion(true)?0:1;  // TODO
    }

    public static class RecordMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text("Count");

        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            output.collect(word, one);
        }
    }

    public static class NoKeyRecordCountReducer extends MapReduceBase implements Reducer<Text, IntWritable, NullWritable, IntWritable> {

        @Override
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<NullWritable, IntWritable> output, Reporter reporter) throws IOException {
            int count = 0;
            while (values.hasNext()){
                count += values.next().get();
            }
            output.collect(null,new IntWritable(count));
        }

    }
}

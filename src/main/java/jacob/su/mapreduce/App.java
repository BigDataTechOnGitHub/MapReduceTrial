package jacob.su.mapreduce;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.util.ToolRunner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        int exitCode = ToolRunner.run(new RowCount(), args);
        System.exit(exitCode);
    }
}

package ts.hadoop.itembasedsimilarity;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SimilarityDriver {
	
	private static String input, output, temp;

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		  
	  //Job 1: Make user the key and reduce to inverted index
	  Job job1 = new Job();
	
		  job1.setJarByClass(SimilarityDriver.class);
		  job1.setMapperClass( UserKeyMapper.class);
		  job1.setReducerClass(InvertedIndexReducer.class);
		  job1.setJobName( "Inverted Matrix"  );
		  job1.setOutputKeyClass(Text.class);
		  job1.setOutputValueClass(Text.class);
		  
		  input = args[0];
		  temp = "temp";
		  FileInputFormat.setInputPaths(job1, new Path(input));
		  FileOutputFormat.setOutputPath(job1, new Path(temp));
	  
	  job1.waitForCompletion(true);
	  
	  //Job 2: Emit all co-occurred and compute similarities
	  Job job2 = new Job();
		
		  job2.setJarByClass(SimilarityDriver.class);
		  job2.setMapperClass(CooccurrenceMapper.class);
		  job2.setReducerClass(SimilarityReducer.class);
		  job2.setJobName( "Similarity Score"  );
		  job2.setOutputKeyClass(Text.class);
		  job2.setOutputValueClass(Text.class);
		  
		  output = args[1];	
		  FileInputFormat.setInputPaths(job2, new Path(temp));
		  FileOutputFormat.setOutputPath(job2, new Path(output));
	  
	  job2.waitForCompletion(true);

	};
}

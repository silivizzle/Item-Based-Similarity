package ts.hadoop.itembasedsimilarity;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserKeyMapper extends
		Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		String kvPair[] = line.split("\t");
		
		Text user = new Text(); 
		Text rating = new Text();
		
		user.set(kvPair[0].toString());
		rating.set(kvPair[1].toString() + "," + kvPair[2].toString());
		
		context.write(user, rating);
	}

}

package ts.hadoop.itembasedsimilarity;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CooccurrenceMapper extends
		Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		String[] line = value.toString().split("\t");		
		String[] movies = line[1].split("\\|");
		String[] pair1, pair2;
		String kPair, vPair= null;
		Text newKey = new Text();
		Text newVal = new Text();
	
		/*Create a pair with the first element and iterate through the rest.
		 Once each element is matched with the first, strike the first element
		from the list and repeat the process. */
		
		for(int i = 0; i < movies.length; i++){
			
			for(int j = i + 1; j < movies.length - i; j++){
				
				pair1 = movies[i].split(",");
				pair2 = movies[j].split(",");
				kPair = pair1[0] + "," + pair2[0];
				vPair = pair1[1] + "," + pair2[1];
				newKey.set(kPair);
				newVal.set(vPair);
				System.out.println("Key: " + kPair + "Val: " + vPair);
				context.write(newKey, newVal);
			}		
		}		
	}
}

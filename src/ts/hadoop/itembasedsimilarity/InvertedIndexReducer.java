package ts.hadoop.itembasedsimilarity;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class InvertedIndexReducer extends
		Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		Text ratings = new Text();
		StringBuffer sb = new StringBuffer();
		
		while (values.iterator().hasNext()) {
			sb.append(values.iterator().next());
			if(values.iterator().hasNext()){
				sb.append("|");
			}
		}
		
		ratings.set(sb.toString());
		context.write(key, ratings);
		
	}

}

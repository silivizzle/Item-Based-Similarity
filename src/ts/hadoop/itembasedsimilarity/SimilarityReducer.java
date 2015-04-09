package ts.hadoop.itembasedsimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SimilarityReducer extends
		Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		double average1 = 0.0, average2 = 0.0;

		String[] ratingsArr = null;
		Text similarity = new Text();
		int count = 0;
		double numerator = 0.0, denominator1 = 0.0, denominator2 = 0.0, answer = 0.0;
		List<ValuePair> pairs = new ArrayList<ValuePair>();
		
		//calculate the averages
		for(Text value : values){
			double ratings[] = new double[2];
			ValuePair pair = new ValuePair();			
			ratingsArr = value.toString().split(",");
			
			//parse pair to array
			for(int i=0; i < ratingsArr.length; i++){
				ratings[i] = Integer.parseInt(ratingsArr[i]);
			}
			
			//add the values
			average1 += ratings[0];
			average2 += ratings[1];	
			
			//set pair and add it to list
			pair.setRating(ratings);
			pairs.add(pair);
			count++;
		}
		
		//calculate average values for each movie in pair
		average1 = average1 / count;
		average2 = average2 / count;
		
		//Set condition to calculate only if more than one item to item comparison
		if(pairs.size() > 1){
		
			//calculate 
			for(int i=0; i < pairs.size(); i++){
				ValuePair pair = new ValuePair();
				pair = pairs.get(i);
				numerator += (pair.getFirstRating() - average1) * (pair.getSecondRating() - average2);
				denominator1 += Math.pow((pair.getFirstRating() - average1), 2);
				denominator2 += Math.pow((pair.getSecondRating() - average2), 2);
			}		
	
			answer = numerator / ((Math.sqrt(denominator1)) * (Math.sqrt(denominator2)));		
			similarity.set(String.valueOf(answer));		
			context.write(key, similarity);
		}
	
	}

}

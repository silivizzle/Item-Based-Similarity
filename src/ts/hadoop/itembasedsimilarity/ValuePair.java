package ts.hadoop.itembasedsimilarity;

public class ValuePair {
	
	private double[] rating;
	
	public ValuePair(){
		
	}
	
	public ValuePair(int rating1, int rating2){
		
		rating = new double[] {rating1, rating2};
		
	}

	public double[] getRating() {
		return rating;
	}

	public void setRating(double[] ratings) {
		this.rating = ratings;
	}
	
	public double getFirstRating(){
		int index = 0;
		return rating[index];
	}
	
	public double getSecondRating(){
		int index = 1;
		return rating[index];
	}
	
}

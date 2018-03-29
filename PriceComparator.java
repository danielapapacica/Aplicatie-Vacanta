import java.util.Comparator;

public class PriceComparator implements Comparator<Place>{
		@Override
		public int compare(Place p1, Place p2) {			
			if(p2.getMediumPricePerDay() - p1.getMediumPricePerDay() < 0)
				return -1;
			return 1;
		}	
	}
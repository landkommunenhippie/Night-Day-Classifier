package classifier;

public class ClassificationResult {

	private final String environment;
	private final String name;
	private final double probIsDay;
	private final double probIsNight;
	private final double probIsInside;
	private final double probIsBadWeather;

	public ClassificationResult(String name,int maxi, double probIsDay, double probIsNight,
			double probIsInside, double probIsNotClassifiable) {
		super();
		this.name = name;

		// Auswertung
		if (maxi == 0) {
			this.environment = "Nacht";
		} else if (maxi == 1) {
			this.environment =  "Tag";
		} else if (maxi == 2) {
			this.environment =  "Innenraum";
		} else {
			this.environment =  "Schlechtes Wetter";
		}
		this.probIsDay = probIsDay;
		this.probIsNight = probIsNight;
		this.probIsInside = probIsInside;
		this.probIsBadWeather = probIsNotClassifiable;
	}

	public String getEnvironment() {
		return environment;
	}

	public double getProbIsDay() {
		return probIsDay;
	}

	public double getProbIsNight() {
		return probIsNight;
	}

	public double getProbIsInside() {
		return probIsInside;
	}

	public double getProbIsBadWeather() {
		return probIsBadWeather;
	}

	public String getName() {
		return name;
	}

}

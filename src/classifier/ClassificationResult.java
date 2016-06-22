package classifier;

public class ClassificationResult {

	private final String environment;
	private final String name;
	private final double probIsDay;
	private final double probIsNight;
	private final double probIsGarage;
	private final double probIsBadWeather;
	private double probIsTunnel;

	public ClassificationResult(String name,int maxi, double probIsDay, double probIsNight,
			double probIsGarage, double probIsWeather, double probIsTunnel) {
		super();
		this.name = name;

		// Auswertung
		if (maxi == 0) {
			this.environment = "Nacht";
		} else if (maxi == 1) {
			this.environment =  "Tag";
		} else if (maxi == 2 || maxi == 4) {
			this.environment =  "Innenraum";
		} else {
			this.environment =  "Wetter";
		}
		this.probIsDay = probIsDay;
		this.probIsNight = probIsNight;
		this.probIsGarage = probIsGarage;
		this.probIsBadWeather = probIsWeather;
		this.probIsTunnel = probIsTunnel;
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

	public double getProbIsGarage() {
		return probIsGarage;
	}
	
	public double getProbIsTunnel() {
		return probIsTunnel;
	}


	public double getProbIsBadWeather() {
		return probIsBadWeather;
	}

	public String getName() {
		return name;
	}

}

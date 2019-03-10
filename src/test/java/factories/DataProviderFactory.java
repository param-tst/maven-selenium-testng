package factories;

import dataprovider.ConfigDataProvider;

public class DataProviderFactory {
	public static ConfigDataProvider getConfig() {

		ConfigDataProvider config = new ConfigDataProvider();

		return config;
	}

}

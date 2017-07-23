package com.wallet;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class Driver {




	/**
	 * Creates a new webdriver instance.
	 *
	 * @return the webdriver instance
	 * @throws Exception the exception
	 */
	public static WebDriver create() throws Exception {
		WebDriver webdriver=null;
		DesiredCapabilities capabilities;

		FirefoxProfile profile;
		ProfilesIni iniProfile = new ProfilesIni();
		profile = iniProfile.getProfile("default");

		//else
		//profile = new FirefoxProfile();

		loadFFExtensions(profile);
		profile.setEnableNativeEvents(true);
		//setPreferences(profile);
      /*          if (wdUrl == null) {
                	String binariesPath = new File(Thread.currentThread().getContextClassLoader().getResource("//").getFile()).getParentFile().getParent() +
                    		File.separator + File.separator + "src" + File.separator + "main" + File.separator + "resources";
                	System.setProperty("wdm.targetPath", binariesPath);
                	InternetExplorerDriverManager.getInstance().setup(Architecture.x32, DriverVersion.LATEST.name());
                	webdriver = new FirefoxDriver(profile);
                } else {*/
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		webdriver = new RemoteWebDriver(new URL("http://192.168.8.1"), capabilities);
	//	webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//  }
		return webdriver;
	}

	/**
	 * Creates the FF profile.
	 *
	 * @return the firefox profile
	 */
//	private static FirefoxProfile createFFProfile() {
//		FirefoxProfile profile;
//		String profPath = Configuration.browserProfile;
//		if (profPath != null && !profPath.isEmpty()) {
//			profile = new FirefoxProfile(new File(profPath));
//		} else {
//			profile = new FirefoxProfile();
//		}
//		// these properties are always overridden
//		// attempt to fix
//		// http://code.google.com/p/selenium/issues/detail?id=2863 (occurs in VM tests)
//		profile.setPreference("capability.policy.default.HTMLIFrameElement.name", "allAccess");
//		profile.setPreference("capability.policy.default.Window.frameElement", "allAccess");
//		profile.setPreference("capability.policy.default.HTMLDocument.compatMode", "allAccess");
//		profile.setPreference("capability.policy.default.Window.pageXOffset", "allAccess");
//		profile.setPreference("capability.policy.default.Window.pageYOffset", "allAccess");
//		// to disable any startup pages loaded by default
//		profile.setPreference("browser.startup.page", 0);
//		profile.setPreference("browser.startup.homepage", "about:blank");
//		// to fix 13524645
//		int timeout = Integer.parseInt(getProperty("defaultTimeout")) / 1000;
//		timeout = timeout < 30 ? 30 : timeout;
//		profile.setPreference("dom.max_script_run_time", timeout);
//		profile.setPreference("dom.max_chrome_script_run_time", timeout);
//        //Issue 3314:	WebDriver: Plugin Container for Firefox has stopped working.
//        profile.setPreference("plugin.state.flash", 0);
//        profile.setEnableNativeEvents(false);   //[SM] Ask me before enable
//
//        // This section tells firefox to download all files of the user specified mimeTypes to the user specified downloadDirectory
//        if (Config.getDownloadFolderPath() != null) {
//            profile.setPreference("browser.download.folderList", 2);
//            profile.setPreference("browser.download.dir", Config.getDownloadFolderPath());
//            profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
//                    "application/pdf,application/x-pdf,application/vnd.fdf,application/vnd.adobe.xfdf");
//            profile.setPreference("pdfjs.disabled", true);
//            profile.setPreference("pdfjs.firstRun", false);
//        }
//		return profile;
//	}

    /**
     * Set preferences for FireFox
     * @param profile - see {@link org.openqa.selenium.firefox.FirefoxProfile}
     */
//	private static void setPreferences(FirefoxProfile profile){
//		String[] preferences, prefContainer;
//		String preferencesContainer = Config.getProperty("FirefoxPreferences");
//		if (preferencesContainer != null && !preferencesContainer.isEmpty()){
//			preferences = preferencesContainer.split("\\|");
//			for(String preference: preferences){
//				prefContainer = preference.split("\\=");
//				profile.setPreference(prefContainer[0], Boolean.valueOf(prefContainer[1]));
//			}			
//		}
//        //Set Using a Proxy
//        if(getProperty("BrowserProxy") != null && getProperty("BrowserProxyEnable") != null){
//            if(Boolean.parseBoolean(getProperty("BrowserProxyEnable"))){
//                profile.setPreference("network.proxy.type", 1);
//                profile.setPreference("network.proxy.http", getProperty("BrowserProxy").split(":")[0]);
//                profile.setPreference("network.proxy.http_port", Integer.parseInt(getProperty("BrowserProxy").split(":")[1]));
//            }
//        }
//	}
	
	/**
	 * Load FF extensions.
	 *
	 * @param profile the FF profile
	 */
	private static void loadFFExtensions(FirefoxProfile profile) {
		/*String[] extensionPaths;
		String exprop = Config.firefoxExtensions;
		if (exprop != null && !exprop.isEmpty()) {
			extensionPaths = exprop.split("\\|");
			for (int i = 0; i < extensionPaths.length; i++) {
				profile.addExtension(new File(extensionPaths[i]));
				// disable firebug first run popup
				if (extensionPaths[i].toLowerCase().contains("firebug")) {
					profile.setPreference("extensions.firebug.showFirstRunPage", false);
				}
			}
		}*/
	}

	private static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		//windows
		return (os.indexOf( "win" ) >= 0);
	}

}

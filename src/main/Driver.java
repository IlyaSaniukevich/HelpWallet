package main;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;


public class Driver {




	/**
	 * Creates a new webdriver instance.
	 *
	 * @return the webdriver instance
	 * @throws Exception the exception
	 */
	public static WebDriver create() throws Exception {
		WebDriver webdriver=null;
        /*DesiredCapabilities capabilities;
        String wdUrl = Config.remoteWebDriverURL;
        String browser = Config.browserType;
        switch (browser.toLowerCase()) {
            case "ff":
         
            	FirefoxProfile profile;
            	boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
                //if (isDebug){
                	//ProfilesIni iniProfile = new ProfilesIni();
                	//profile = iniProfile.getProfile("default");}
                //else    
                profile = new FirefoxProfile();
                
                loadFFExtensions(profile);
                profile.setEnableNativeEvents(true); 
                //setPreferences(profile);
                if (wdUrl == null) {
//                	String binariesPath = new File(Thread.currentThread().getContextClassLoader().getResource("//").getFile()).getParentFile().getParent() + 
//                    		File.separator + File.separator + "src" + File.separator + "main" + File.separator + "resources";
//                	System.setProperty("wdm.targetPath", binariesPath);
//                	InternetExplorerDriverManager.getInstance().setup(Architecture.x32, DriverVersion.LATEST.name());
                	webdriver = new FirefoxDriver(profile);
                } else {
                    capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                    webdriver = new RemoteWebDriver(new URL(wdUrl), capabilities);
                }
                break;

            case "ie":
                if(!System.getProperty("os.name").toLowerCase().contains("win")){
                    throw new RuntimeException("iexplore is NOT supported by no win os!");
                }
                capabilities = DesiredCapabilities.internetExplorer();
                
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//                capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//                capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        		logger.debug("IE NATIVE_EVENTS: " + Selenium.nativeEvents);
            	capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, Selenium.nativeEvents);
//                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                if (wdUrl == null) {
///                    file = new File(Thread.currentThread().getContextClassLoader().getResource("IEDriverServer.exe").getFile());
///    				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                    String binariesPath = new File(Thread.currentThread().getContextClassLoader().getResource("//").getFile()).getParentFile().getParent() + 
                    		File.separator + File.separator + "src" + File.separator + "main" + File.separator + "resources";
                	System.setProperty("wdm.targetPath", binariesPath);
                	InternetExplorerDriverManager.getInstance().setup(Architecture.x32, DriverVersion.LATEST.name());
//                	System.getProperty("webdriver.ie.driver");

//                    String ieDriverPath = Config.getCommonFolderPath() + File.separator + "resources" + File.separator + "IEDriverServer.exe";
//                    System.setProperty("webdriver.ie.driver", ieDriverPath);

                    webdriver = new InternetExplorerDriver(capabilities);
                } else {
                    webdriver = new RemoteWebDriver(new URL(wdUrl), capabilities);
                }

                break;

            case "ch":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
				if (!isWindows()) chromeOptions.addArguments("--kiosk");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--test-type");
                chromeOptions.addArguments("chrome.switches","--disable-extensions");
                
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                //capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
                //capabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, "true");
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                if (wdUrl == null) {
//                	file = new File(Driver.class.getResource(".chromedriver.exe").getFile());
///                	file = new File(Thread.currentThread().getContextClassLoader().getResource("chromedriver.exe").getFile());
///                	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                    String binariesPath = new File(Thread.currentThread().getContextClassLoader().getResource("//").getFile()).getParentFile().getParent() + 
                    		File.separator + File.separator + "src" + File.separator + "main" + File.separator + "resources";
                	System.setProperty("wdm.targetPath", binariesPath);
                	ChromeDriverManager.getInstance().setup(Architecture.x32, DriverVersion.LATEST.name());
//                	System.getProperty("webdriver.chrome.driver");
//                	String chromeDriverPath = Config.getCommonFolderPath() + File.separator + "resources" + File.separator + "chromedriver";
//                    if(System.getProperty("os.name").toLowerCase().contains("win")){
//                        chromeDriverPath += ".exe";
//                    }
//                    System.setProperty("webdriver.chrome.driver", chromeDriverPath);

                	webdriver = new ChromeDriver(capabilities);
                } else {
                    webdriver = new RemoteWebDriver(new URL(wdUrl), capabilities);
                }

                break;

            case "safari":
                SafariOptions safariOptions = new SafariOptions();
                //TODO: make sure that we need it
                safariOptions.setUseCleanSession(true);

                capabilities = DesiredCapabilities.safari();
                capabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);

                if (wdUrl == null) {
                    webdriver = new SafariDriver(capabilities);
                } else {
                    webdriver = new RemoteWebDriver(new URL(wdUrl), capabilities);
                }

                break;
                
            case "android":
            	capabilities = DesiredCapabilities.android();
            	capabilities.setCapability("deviceName",Config.AndroidDeviceName);
      		  	capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
      		  	capabilities.setCapability(MobileCapabilityType.VERSION, "5.1.0");
      		  	capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
      		    capabilities.setCapability(MobileCapabilityType.NO_RESET,"true");
      		    capabilities.setCapability("appPackage", "com.android.chrome");
      		    capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
      		    webdriver = new RemoteWebDriver(new URL(appiunUrl), capabilities);
      			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                break;


			case "iphone":
				startAppiumServer();
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.3");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE");
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "500");
				capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.6.3");
				webdriver = new RemoteWebDriver(new URL(appiunUrl), capabilities);
				webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				break;

			case "ipad":
				startAppiumServer();
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.3");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Air 2");
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "500");
				capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.6.3");
				webdriver = new RemoteWebDriver(new URL(appiunUrl), capabilities);
				webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				break;

            default:
                throw new Exception("Unsupported browser: " + browser);
        }

		// if wdUrl != null, it is a RemoteWebDriver which needs to be augmented
		if (wdUrl != null) {
			webdriver = new Augmenter().augment(webdriver);
		}

		Browser.setMainWindow(webdriver.getWindowHandle());

		webdriver.manage().timeouts().implicitlyWait(Config.implicitlyWaitTimeout, TimeUnit.MILLISECONDS);
		webdriver.manage().timeouts().setScriptTimeout(Config.pageLoadTimeout, TimeUnit.MILLISECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(Config.appWakeUpTimeout, TimeUnit.MILLISECONDS);
*/
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

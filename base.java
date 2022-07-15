package base;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;



import org.testng.annotations.BeforeTest;















/*
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
*/
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
/*
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
*/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.ExtentManager;
import utilities.dbConnector;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.File;
import java.lang.reflect.Method;














import java.nio.charset.Charset;





/*
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
*/
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class base extends dbConnector
{
	
	public static WebDriver driver;
    public static FileInputStream fis;
	public static Properties prop;
    public static String rootFolderPath;
	public static String DOWNLOADFILEPATH;
	public static String ExtentReportPath;
	 public static String ExtentConfigPath;
	 
	public static ExtentReports extent;
    public static ExtentTest test;
    public static WebDriverWait wait;
	
	public ExtentReports rep = ExtentManager.getInstance();
	 
	protected static Calendar cal = Calendar.getInstance();
	
	//Code for log4j
	public static Logger log = Logger.getLogger("devpinoyLogger");
	
	static{
	String log4jConfPath = System.getProperty("user.dir")+"\\src\\resources\\properties\\log4j.properties";
	PropertyConfigurator.configure(log4jConfPath);
	}
	


	
	@BeforeSuite
	public void beforeSuite() throws IOException
	{
		
		
	ExtentReportPath=(getPropertyValue())[4].toString();
	ExtentConfigPath=(getPropertyValue())[5].toString();
	extent=new ExtentReports(ExtentReportPath);
	extent.loadConfig(new File(ExtentConfigPath));
		
	//test=extent.startTest(this.getClass().getSimpleName());
		//test.assignAuthor("TCS");
		//test.assignCategory("HeroWeb automation Report-INT");
	
	}
	
	
	/*@BeforeTest
	public void beforeMethod(Method method)
	{
		//test=extent.startTest((this.getClass().getSimpleName()+ "::" +method.getName()),method.getName());
		
		test=extent.startTest(this.getClass().getSimpleName());
		test.assignAuthor("TCS");
		test.assignCategory("HeroWeb automation Report-INT");
		}

	
	@AfterTest
	public void afterMethod()
	{
	extent.endTest(test);	
		
	}
	
	@AfterSuite
	public void afterSuite()
	{
	extent.flush();
	extent.close();
		
	}

	
*/
	
	
	
	
	 
    public static WebDriver initilizeDriver() throws IOException
   
    {
    	prop=new Properties();
    	
    	 rootFolderPath = new File("").getAbsolutePath();
   	     System.out.println(rootFolderPath);
    	
    	fis=new FileInputStream(rootFolderPath+"\\src\\Resources\\properties\\config.properties");	

	     prop.load(fis);
	     
	    // log.debug("Config File Loaded");
	 
	 String browserName=prop.getProperty("browser");
	 String chromedriverpath=prop.getProperty("chromedriverpath");
	 String IEDriver=prop.getProperty("IEDriver");
	 
	 if(browserName.equals("chrome"))
	 {
		 System.setProperty("webdriver.chrome.driver",chromedriverpath);
		 
		 
		
		 String DOWNLOADFILEPATH=(getPropertyValue())[2];

		 HashMap<String, Object> chromePrefs = new HashMap<String, Object>();        
		 chromePrefs.put("profile.default_content_settings.popups", 0);
		 chromePrefs.put("download.prompt_for_download", false);
	     chromePrefs.put("download.default_directory", DOWNLOADFILEPATH);
	     
	     ChromeOptions options = new ChromeOptions();
	   
	     options.setExperimentalOption("prefs", chromePrefs);
	     options.addArguments("--test-type");
		 options.addArguments("--disable-extensions"); //to disable browser extension popup
		 options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
		 
	     driver = new ChromeDriver(options);

	 }
	
	 else if(browserName.equals("firefox"))
	 {
		driver= new FirefoxDriver(); 
		 
	 }
    
	 else if(browserName.equals("IE"))
	 {
		 System.setProperty("webdriver.ie.driver",IEDriver);
		 driver= new InternetExplorerDriver();
		 
	 }
    
    
 
   // driver.manage().window().maximize();
	
    return driver;
    }

 @BeforeMethod
   public static void launchApp() throws IOException
   {
	  driver=initilizeDriver(); 
	  
	
     // test.log(LogStatus.INFO, "Launching Browser");
	  System.out.println("Launching Browser");
     // log.debug("Launching Browser");
	  
	//  String URL = (getPropertyValue())[1];
	  
	 //  driver.get(URL);
	  
	 String URL = (getPropertyValue())[1];
	  
	 
	 
	 driver.get(URL);
	   
       // test.log(LogStatus.INFO, "Opening->"+URL);
	  System.out.println("Opening->"+URL);
	  // log.debug("Opening->"+URL);
    
	   driver.manage().deleteAllCookies();
	   driver.manage().window().maximize();
	   
	   
	    wait=new WebDriverWait(driver,50000);
	  
	/*   
	   wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='apps-heading']")));
	
	   driver.findElement(By.xpath("//a[@id='apps-heading']")).click();
	   
	   wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Humana Self Service (HSS)')]")));
	   
	   driver.findElement(By.xpath("//a[contains(text(),'Humana Self Service (HSS)')]")).click();
	 
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='secondary list'][contains(text(),'HERO')]")));

	  
	// test.log(LogStatus.INFO, "Opening HERO Application URL");
	  System.out.println("Opening HERO Application URL");
	 // log.debug("Opening HERO Application URL");
	  

	  driver.findElement(By.xpath("//a[@class='secondary list'][contains(text(),'HERO')]")).click();
		 */
  
	 //  test.log(LogStatus.INFO, "Opening HERO Application URL");
	   System.out.println("Opening HERO Application URL");
	//   log.debug("Opening HERO Application URL");
	
	   /*
   	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='username']"))); 
	  driver.findElement(By.xpath("//input[@id='username']")).sendKeys("NXV6908");
	  driver.findElement(By.xpath("//input[@id='password']")).sendKeys("apr#2020");
	  driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
	*/
	   
	 // return driver;
	   
	   
   }
   

 //Going to Post login screen

public static void gotoPostLoginScreen() throws InterruptedException
{
	 Thread.sleep(2000);
   
   Actions action = new Actions(driver);
   
   action.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'HERO')]"))).perform();
	//action.moveToElement(driver.findElement(By.xpath("//app-navigation-bar[1]/div[1]/ul[1]/li/a")));
	//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin Dashboard')]")));
	//action.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Admin Dashboard')]"))).click().release().build().perform();
	action.release().build().perform();
	
}
 
 
 
 
 
 
 
 
@AfterMethod
   public static void closeApp() throws InterruptedException
   {
	 Thread.sleep(5000);
//Clicking on Logout button
//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-header/div[1]/div[2]/div[1]/ul[1]/li[3]/a[1]/em[1]")));	 
//driver.findElement(By.xpath("//app-header/div[1]/div[2]/div[1]/ul[1]/li[4]/a[1]/em[1]")).click();
	 
 System.out.println("Closing Browser");
   //test.log(LogStatus.INFO, "Closing Browser");
  driver.quit();
	 
	// extent.endTest(test);	
	 
	// extent.flush();
	 
	//extent.close();
	 
	 }
    
 
 
 
//Getting Property Values 
 public static String[] getPropertyValue() throws IOException
 {
 	prop=new Properties(); 
 	
 	rootFolderPath = new File("").getAbsolutePath();
 
 	fis=new FileInputStream(rootFolderPath+"\\src\\Resources\\properties\\config.properties");	

 	
	     prop.load(fis);
	 
	//String path = prop.getProperty("testdatapath1");
	String xlinputfile=prop.getProperty("xlInputPath");
	String URL=prop.getProperty("url");
	String DOWNLOADFILEPATH =prop.getProperty("DownloadFilepath");
	String xlReportfile=prop.getProperty("xlReportPath");
	String ExtentReportPath=prop.getProperty("ExtentReportPath");
	String ExtentConfigPath=prop.getProperty("ExtentConfigPath");
	String NonUIURL=prop.getProperty("NonUIURL");
	
	String Property[]={xlinputfile,URL,DOWNLOADFILEPATH,xlReportfile,ExtentReportPath,ExtentConfigPath,NonUIURL};
	 
  return Property;
	 }
 
 
 
 public static String getPropertyFileValue(String s) throws IOException
 {
	 prop=new Properties(); 
	 rootFolderPath = new File("").getAbsolutePath();
	 fis=new FileInputStream(rootFolderPath+"\\src\\Resources\\properties\\config.properties");
	 prop.load(fis); 
	 
	 String Keyvalue=prop.getProperty(s);
	return Keyvalue;
 
 }

 
 
 public static int getYear ()
 {
    int year=cal.get(Calendar.YEAR);
    return year;
 } 
 
 public static int getMonth()
 {
int month=cal.get(Calendar.MONTH)+1;
   return month; 
 }
 
  public static int getCurrentDate (){
        //Create a Calendar Object
    	
    	 int date=cal.get(Calendar.DATE);
       return date; 
    } 
    

  
  
  
  
  
  
  
  
    
   public static String[] getPlaceholdertext(String xpath1,String xpath2)
   {
	
	WebDriverWait wait=new WebDriverWait(driver,200);
	

     wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath1)));
	
	String SelectedDropDownValue=driver.findElement(By.xpath(xpath1)).getText() ;
	
	System.out.println(SelectedDropDownValue);
	
	driver.findElement(By.xpath(xpath1)).click();
	
	
	
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath2)));
	
	   
	
	
	
	
	String Expected_placeholderTXT = null,Actual_placeholderTXT = null;
	
	Expected_placeholderTXT="Enter "+SelectedDropDownValue;
	
	System.out.println(Expected_placeholderTXT);

	
    
    boolean enabledFlag=driver.findElement(By.xpath(xpath2)).isEnabled();
	 
	 boolean displayFlag=driver.findElement(By.xpath(xpath2)).isDisplayed();
	 
	       if(enabledFlag==true && displayFlag==true)
	          {	
                Actual_placeholderTXT=driver.findElement(By.xpath(xpath2)).getAttribute("placeholder");
                
                System.out.println(Actual_placeholderTXT);
	           }
	        else
	            {
		         System.out.println("Plceholder Textbox not enabled and not visible");
	             }

    
	String placeholderTXT[]={Expected_placeholderTXT,Actual_placeholderTXT};
	
    return placeholderTXT;  
   }
    
    
    
    
    
    
    
   
    public static String jsonWriter(String jsonString, String filePath) 
    
    {
    	
    	
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(jsonString);
        String prettyJson = null;
        try(FileWriter writer = new FileWriter(filePath)) 
        {
            gson.toJson(el, writer);
            prettyJson=gson.toJson(el);
            writer.close();
            
          }
        catch (IOException e) 
        {
            e.getMessage();
        }
		return prettyJson;
    }
   
    
    
    
    public static String toPrettyFormat(String jsonString) 
    {
      JsonParser parser = new JsonParser();
       Gson gson = new GsonBuilder().setPrettyPrinting().create();

       JsonElement el = parser.parse(jsonString);
       String prettyJson = gson.toJson(el);
        return prettyJson;
    }
    
 /*   
   public static void findJSONDiff(String json1,String json2) throws JsonMappingException, JsonProcessingException
   {
	   
	// Using GSON API
	   
	   
	   
	   
	   Gson g = new Gson();
	   JsonReader reader1 = new JsonReader(new StringReader(json1));
	   reader1.setLenient(true);
	   
	   JsonReader reader2 = new JsonReader(new StringReader(json2));
	   reader1.setLenient(true);
	   
	   Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
	   
	  
	   Map<String, Object> secondMap = g.fromJson(reader1, mapType);
	   Map<String, Object> firstMap = g.fromJson(reader2, mapType);
	
	   
  TypeReference<HashMap<String, Object>> type = 
	       new TypeReference<HashMap<String, Object>>() {};

	
	   Map<String, Object> leftFlatMap = FlatMapUtil.flatten(firstMap);
	   Map<String, Object> rightFlatMap = FlatMapUtil.flatten((Map<String, Object>) secondMap);

	   MapDifference<String, Object> difference = Maps.difference(leftFlatMap, rightFlatMap);
	   
	   
	  

	   System.out.println("Entries only on the left\n--------------------------");
	   difference.entriesOnlyOnLeft()
	             .forEach((key, value) -> System.out.println(key + ": " + value));

	   System.out.println("\n\nEntries only on the right\n--------------------------");
	   difference.entriesOnlyOnRight()
	             .forEach((key, value) -> System.out.println(key + ": " + value));

	   System.out.println("\n\nEntries differing\n--------------------------");
	   difference.entriesDiffering()
	             .forEach((key, value) -> System.out.println(key + ": " + value));
	  
	   
	   
	   System.out.println(difference);
	   
	
   }
  

   
   public final static class FlatMapUtil {

	    private FlatMapUtil() {
	        throw new AssertionError("No instances for you!");
	    }

	   

		public static Map<String, Object> flatten(Map<String, Object> map) {
	        return map.entrySet().stream()
	                .flatMap(FlatMapUtil::flatten)
	                .collect(LinkedHashMap::new, (m, e) -> m.put("/" + e.getKey(), e.getValue()), LinkedHashMap::putAll);
	    }

	    private static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {

	        if (entry == null) {
	            return Stream.empty();
	        }

	        if (entry.getValue() instanceof Map<?, ?>) {
	            return ((Map<?, ?>) entry.getValue()).entrySet().stream()
	                    .flatMap(e -> flatten(new AbstractMap.SimpleEntry<>(entry.getKey() + "/" + e.getKey(), e.getValue())));
	        }

	        if (entry.getValue() instanceof List<?>) {
	            List<?> list = (List<?>) entry.getValue();
	            return IntStream.range(0, list.size())
	                    .mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + "/" + i, list.get(i)))
	                    .flatMap(FlatMapUtil::flatten);
	        }

	        return Stream.of(entry);
	    }
	}
    
   
 */  
   
   
   
  
    
    
   
    public File getLastDownloadedFile(String path) {
        File choice = null;
        try {
            File fl = new File(path);
            File[] files = fl.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.isFile();
                }
            });

            long lastMod = Long.MIN_VALUE;

            for (File file : files) {
                if (file.lastModified() > lastMod) {
                    choice = file;
                    lastMod = file.lastModified();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while getting the last download file :"
                    + e.getMessage());
        }
         
            return choice;
    }

    public static  File getLatestFilefromDir(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }
	
	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}

    
    
 public static boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	  	    
	    for (int i = 0; i < dir_contents.length; i++) {
	        if (dir_contents[i].getName().equals(fileName))
	            return flag=true;
	            }

	    return flag;
	
	}
 
 
 
 
 public void parse(String json) throws JsonMappingException, JsonProcessingException  {
     JsonFactory factory = new JsonFactory();

     ObjectMapper mapper = new ObjectMapper(factory);
     JsonNode rootNode = mapper.readTree(json);  

     Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
     while (fieldsIterator.hasNext()) {

         Map.Entry<String,JsonNode> field = fieldsIterator.next();
         System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
         
         
          
     }
}
 
/*

    public static ChromeOptions setProfileChrome() throws IOException
    {
   	 //Added for Download scenarios
		 
		
	       
	      // return options;
   	 
    }
    	
*/
  
 public static void selectDateFromDatePicker(String date) throws InterruptedException
	{
		By today_datePicker=By.xpath("//span[contains(text(),'Today')]");
		if(!(date.equalsIgnoreCase("today")))
		{
			String month=date.substring(0, 2);
			String day=date.substring(3, 5);
			String year=date.substring(6, 10);
			System.out.println("Month, Date, Year="+month+"  "+day+"  "+year);
			
				//resetting the implicit wait because, explicit wait is not being implemented
			try {
				By dd_month_datepicker=By.xpath("//button[contains(@class,'p-datepicker-year')]");
				By month_datePicker=By.xpath("//select[contains(@class,'p-datepicker-month')]/option[@value='"+(Integer.parseInt(month)-1)+"']");
				By dd_year_datepicker=By.xpath("//select[contains(@class,'p-datepicker-year')]");
				By year_datePicker=By.xpath("//select[contains(@class,'p-datepicker-year')]/option[text()='"+year+"']");
				By day_datePicker=By.xpath("//table[contains(@class,'p-datepicker-calendar')]/descendant::td/span[text()='"+(Integer.parseInt(day))+"'][not(contains(@class,'p-disabled'))]");

				driver.findElement(dd_month_datepicker).click();
				driver.findElement(month_datePicker).click();

				Thread.sleep(1000);
				driver.findElement(dd_year_datepicker).click();
				driver.findElement(year_datePicker).click();

				Thread.sleep(1000);
				wait.until(ExpectedConditions.presenceOfElementLocated(day_datePicker));
				int dateinstances=driver.findElements(day_datePicker).size();
				System.out.println("Active dates in calendar with same day="+dateinstances);
				if(dateinstances==2)
				{
					if(Integer.parseInt(day)>20)
					day_datePicker=By.xpath("(//table[contains(@class,'p-datepicker-calendar')]/descendant::td/span[text()='"+(Integer.parseInt(day))+"'][not(contains(@class,'p-disabled'))])[2]");
					else if(Integer.parseInt(day)<10)
							day_datePicker=By.xpath("(//table[contains(@class,'p-datepicker-calendar')]/descendant::td/span[text()='"+(Integer.parseInt(day))+"'][not(contains(@class,'p-disabled'))])[1]");
				}
				
				driver.findElement(day_datePicker).click();
				Thread.sleep(1000);

			}
			catch(TimeoutException e)
			{
				System.out.println("Date to be selected is disabled");
				driver.findElement(By.xpath("html/body")).click();//click to close date picker
			}
			
		}
		else driver.findElement(today_datePicker).click();
	}
	   
	
	 public static String getAlphaNumericString(int n)
		{

			// length is bounded by 256 Character
			byte[] array = new byte[256];
			new Random().nextBytes(array);

			String randomString
				= new String(array, Charset.forName("UTF-8"));

			// Create a StringBuffer to store the result
			StringBuffer r = new StringBuffer();

			// remove all spacial char
			String AlphaNumericString
				= randomString
					.replaceAll("[^A-Za-z0-9]", "");

			// Append first 20 alphanumeric characters
			// from the generated random String into the result
			for (int k = 0; k < AlphaNumericString.length(); k++) {

				if (Character.isLetter(AlphaNumericString.charAt(k))
						&& (n > 0)
					|| Character.isDigit(AlphaNumericString.charAt(k))
						&& (n > 0)) {

					r.append(AlphaNumericString.charAt(k));
					n--;
				}
			}

			// return the resultant string
			return r.toString();
		}
  
	public static String getDate(int i)
	{  
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
    
	     Calendar date = Calendar.getInstance();
	     date.add(Calendar.DATE,i);
	     String calendarDate = formatter.format(date.getTime()); 
	     System.out.println("CalendarDate is->"+calendarDate); 
    
	     return calendarDate;
		
	}

	public static void printFailure(String testCaseNumber, Throwable e)	//Anuja-07/15/21
	 {
		 System.out.println(testCaseNumber+" Failed");
		 Assert.fail(e.getMessage());
	 }
	
	 public static void selectDate(String expDate) throws Exception{
	     
	     List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	
	      
	       
	       // Getting year from Calendar
	        
	        String yearCal= driver.findElement(By.xpath("//button[contains(@class,'p-datepicker-year')]")).getText();
	        System.out.println("Current calendar year->"+yearCal); 
	      //click on center button
	      driver.findElement(By.xpath("//button[contains(@class,'p-datepicker-month')]")).click();
	        //splitting date
	        String Date[]=expDate.split("/");
	        String expDay = null;
	        if(Date[1].contains("0")){
	         expDay=Date[1].replaceAll("0", "");
	        }
	        else{
	         expDay=Date[1];
	        }
	        String expMonth=Date[0];
	        String expYear = Date[2];
	        
	        System.out.println("Entered Date->"+expDay); 
	        System.out.println("Entered Month->"+expMonth); 
	        System.out.println("Entered Year->"+expYear); 
	        
	  
	        
	        System.out.println("Current year->"+Calendar.getInstance().get(Calendar.YEAR));
	        
	       
	        if(Integer.parseInt(expYear)<Integer.parseInt(yearCal))
	        {
	            int count=(Integer.parseInt(yearCal)-Integer.parseInt(expYear));
	            System.out.println("count->"+count);
	             for(int i=0;i<count;i++)
	             {
	             driver.findElement(By.xpath("//span[contains(@class,'p-datepicker-prev-icon')]")).click(); 
	             }
	        }
	        
	         else if(Integer.parseInt(expYear)>Integer.parseInt(yearCal))
	         {
	             int count1=(Integer.parseInt(expYear)-Integer.parseInt(yearCal));
	             System.out.println("count->"+count1);
	          for(int i=0;i<count1;i++)
	          {
	          driver.findElement(By.xpath("//span[contains(@class,'p-datepicker-next-icon')]")).click(); 
	         }
	         
	        }
	           Thread.sleep(1000);
	           String months_xpath="//span[contains(text(),'"+(months.get(Integer.parseInt(expMonth)-1))+"')]";
	         //  System.out.println("months_xpath->"+months_xpath);
	           driver.findElement(By.xpath(months_xpath)).click();
	           Thread.sleep(3000); 
	           String day_xpath="//table[contains(@class,'p-datepicker-calendar')]/descendant::td/span[text()='"+(Integer.parseInt(expDay))+"'][not(contains(@class,'p-disabled'))]";

	           
	           
	           driver.findElement(By.xpath(day_xpath)).click();

	        }
	
	
	
	
	
	
	
	
	
}

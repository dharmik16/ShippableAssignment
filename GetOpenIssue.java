import java.awt.Container;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetOpenIssue extends JFrame {
	
	private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private Document htmlDocument;
	private List<String> time_list = new LinkedList<String>();
	private List<Long> day_diff = new LinkedList<Long>();
	boolean flag = true;
	private int sameDay=0, sameWeek=0,moreThanWeek=0;
	
	/**
	 * Iterate over all the issue links and get the time stamp for each issues and store it in a list 
	*/
	public void crawl(String url){
		do{
			try{
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
	            Document htmlDocument = connection.get();
	            this.htmlDocument = htmlDocument;
	            if(connection.response().statusCode() == 200){
	            //    System.out.println("\n**Visiting** Received web page at " + url);
	            }
	            if(!connection.response().contentType().contains("text/html"))
	            {
	                System.out.println("**Failure** Retrieved something other than HTML");
	                return;
	            }
	            Elements linksOnPage = htmlDocument.select("div.issue-title");
	            Elements mydoc = linksOnPage.select("div.issue-meta");
	            Elements mydoc2 = mydoc.select("span.opened-by");
	            Elements mydoc3 = mydoc2.select("time");
	            for(Element link:mydoc3){
	            	String myTime = link.attr("datetime");
	            	myTime = myTime.replaceAll("T", " ");
	            	myTime = myTime.replaceAll("[^0-9-:. ]", "");
	            	myTime = myTime.replaceAll("-","/");
	            	time_list.add(myTime);
	            }
	          
	            Elements navigate = htmlDocument.select("div.paginate-container");
	            Element nextPage = navigate.select(".next_page").first();
	            if(nextPage.hasClass("disabled")){
	            	flag = false;
	            }else{
	            	url = nextPage.attr("abs:href");
	            }
			}catch(IOException ioe)
	        {
				return;
	        }
		}while(flag);
	}
	
	/**
	 * Get the duration of open issue 
	*/
	public void getDiff() throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String curr_date = dateFormat.format(date);
		Date currentDate = null;
		try {
			currentDate = dateFormat.parse(curr_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String eachDate:time_list){
			Date temporaryDate = dateFormat.parse(eachDate);			
			long diff = currentDate.getTime() - temporaryDate.getTime();
		    day_diff.add(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		}
		
	}
	
	/*
	 * Categories the issue based on duration and increase count accordingly
	*/
	public void countIssues(){
		
		for(Long diff:day_diff){
			if(diff < 1)
				sameDay++;
			else if(diff <= 7)
				sameWeek++;
			else
				moreThanWeek++;
		}
	}
	
	/**
	 *Prints the open issue statistics in form of a table
	*/
	public void printTable(){
		String[] columnNames = {"Today","This Week","Over a Week","Total"};
		Object[][] data = {{sameDay,sameWeek,moreThanWeek,sameDay+sameWeek+moreThanWeek}};
		JTable table = new JTable(data, columnNames);
		this.add(new JScrollPane(table));
        this.setTitle("Open Issue Statistics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.pack();
        this.setVisible(true);
	}
}

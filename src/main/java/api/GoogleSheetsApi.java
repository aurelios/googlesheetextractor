package api;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
/**
 * 
 * @author aurelio.gimenes
 *
 */
public class GoogleSheetsApi {

    // Generate a service account and P12 key:
    // https://developers.google.com/identity/protocols/OAuth2ServiceAccount
    private final String CLIENT_ID = "testeaddon@tensile-nebula-144317.iam.gserviceaccount.com";
    // Add requested scopes.
    private final List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds/"/*,
                                                      "https://spreadsheets.google.com/feeds/spreadsheets/private/full/",
                                                      "https://docs.google.com/feeds/"*/);

    // The name of the p12 file you created when obtaining the service account
    private final String P12FILE = "credential.p12";
    
    public Map<String, ListFeed> loadWorksheetsFromSpreadsheet(String sheetKey) throws GeneralSecurityException,
            IOException, URISyntaxException, ServiceException {
        
        SpreadsheetService service = new SpreadsheetService("google-spreadsheet");
        GoogleCredential credential = getCredentials();
        credential.refreshToken();
        service.setOAuth2Credentials(credential);
        
        URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full/");
        
        // Make a request to the API and get all spreadsheets.
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
       // service.setConnectTimeout(10);
        if(feed == null){
            return null;            
        }
        
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        if (spreadsheets.size() == 0) {
           return null;
        }
        
        Map<String, ListFeed> feeds = new HashMap<String, ListFeed>();
        for(SpreadsheetEntry spreadsheet : spreadsheets){
            if(spreadsheet.getKey().equals(sheetKey)){                
                WorksheetFeed worksheetFeed = service.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
                List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
                if (worksheets != null){
                    for (WorksheetEntry worksheet : worksheets) {
                        URL listFeedUrl = worksheet.getListFeedUrl();
                        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
                        feeds.put(worksheet.getTitle().getPlainText(), listFeed);
                    }
                }
            }
        }
        return feeds;
    }
    
    private GoogleCredential getCredentials() throws GeneralSecurityException,
            IOException, URISyntaxException {
        JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport httpTransport = GoogleNetHttpTransport
                                                            .newTrustedTransport();

        URL fileUrl = GoogleSheetsApi.class.getClassLoader().getResource(P12FILE);
        GoogleCredential credential = new GoogleCredential.Builder()
                                                                    .setTransport(httpTransport)
                                                                    .setJsonFactory(JSON_FACTORY)
                                                                    .setServiceAccountId(CLIENT_ID)
                                                                    .setServiceAccountPrivateKeyFromP12File(new File(fileUrl.toURI()))
                                                                    .setServiceAccountScopes(SCOPES)
                                                                    .build();

        return credential;
    }
}
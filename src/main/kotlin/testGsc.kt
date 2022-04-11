import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.searchconsole.v1.SearchConsole
import com.google.auth.oauth2.GoogleCredentials
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


private const val CLIENT_ID = "YOUR_CLIENT_ID"
private const val CLIENT_SECRET = "YOUR_CLIENT_SECRET";

private const val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

private const val OAUTH_SCOPE = "https://www.googleapis.com/auth/webmasters.readonly";

class WebmastersCommandLine {
    private val httpTransport = NetHttpTransport();
    private val jsonFactory: GsonFactory = GsonFactory.getDefaultInstance()

    fun create() {
        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(OAUTH_SCOPE))
            .setAccessType("online")
            .setApprovalPrompt("auto").build();

        val url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build()
        println("Please open the following URL in your browser then type the authorization code:")
        println("  " + url)
        println("Enter authorization code:");
        val br = BufferedReader(InputStreamReader(System.in))
        val code = br.readLine()

        val response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute()

        /*
         * I don't understand this credential part
         */
//        val credential = GoogleCredentials.Builder(setFromTokenResponse(response))
//        val creds = GoogleCredentials(AccessToken(accessToken, expirationTime)))


        // Create a new authorized API client
        val service = SearchConsole.Builder(httpTransport, jsonFactory, creds)
            .setApplicationName("WebmastersCommandLine")
            .build();

        val verifiedSites = ArrayList<String>();
        val request = service.sites().list();

        // Get all sites that are verified
        try {
            val siteList = request.execute()
            for (currentSite in siteList.siteEntry) {
                val permissionLevel = currentSite.permissionLevel
                if (permissionLevel == "siteOwner") {
                    verifiedSites.add(currentSite.siteUrl)
                }
            }
        } catch (e: IOException) {
            println("An error occurred: $e")
        }

        // Print all verified sites
        for (currentSite in verifiedSites) {
            println(currentSite)
        }
    }


}
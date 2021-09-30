# At Everyone GroupMe Bot
## Abandoned, not sure if it still works 4+ years later :)

This is my personal implementation of a simple Java GroupMe Bot.
This is a [Google App Engine](https://cloud.google.com/appengine/) project with [Maven](https://maven.apache.org/) and [GSON](https://github.com/google/gson).


The bot gets sent every message in the group through HTTP POST, then if the message contains '@everyone' it hits the [GroupMe's REST API](https://dev.groupme.com/) with various HTTP POST's and GET's before finally POSTing the message @mentioning everyone in the chat.




GroupMe's documentation on @Mentioning people is non-existent, and some of there example JSON is not even what they send you so this project involved lots of debugging. I made it with Java because... I wanted to.

If you would like to have a bot in your groupme that @everyone's I recommend using the javascript one available on the internet first, as it is easier and cheaper to set up and I don't feel like providing a full installation guide.



## Installation


clone this repo.

Create src/main/java/calvin/williams/accessToken.java
with
 
```
package calvin.williams;


public class accessToken {

	static final String token = "YOUR OWN API TOKEN";

	static final String botID = "YOUR OWN BOTID";

}
```


Then maven build/update into google cloud deploy, then point your bots callback url to http://your-google-app-link.appspot.com/ping



Then it should be working.

import java.util.Random;

public class Main
{
    public static void main(String[] args) {
        runTestSuite();
    }

    static Random rand = new Random();

    static final String[] urls = new String[] {
        "www.google.com",
        "www.facebook.com",
        "www.x.com",
        "www.twitter.com",
        "www.youtube.com",
        "www.utdallas.edu",
        "search.brave.com",
        "www.github.com",
        "www.gitlab.com",
        "www.linkedin.com",
        "www.microsoft.com",
        "www.openai.com",
        "www.perplexity.ai",
        "www.claude.ai",
        "www.mit.edu",
        "www.imdb.com",
        "www.tmdb.com",
        "www.example.com",
    };

    // get a random url from the array
    public static String randomURL() {
        return urls[rand.nextInt(urls.length - 1)];
    }

    // run some tests
    public static void runTestSuite() {
        BrowserNavigation browser = new BrowserNavigation();

        System.out.println("-----Visiting Sites-----");
        for (int i = 0; i < 5; i++)
            System.out.println(browser.visitWebsite(randomURL()));

        System.out.println("-----Going Back to Beginning-----");
        for (int i = 0; i < 5; i++)
            System.out.println(browser.goBack());

        System.out.println("-----Back to Front-----");
        for (int i = 0; i < 5; i++)
            System.out.println(browser.goForward());

        System.out.println("-----Printing History-----");
        System.out.println(browser.showHistory()); 

        System.out.println("-----Clear History-----");
        System.out.println(browser.clearHistory());

        System.out.println("-----Printing History-----");
        System.out.println(browser.showHistory()); 

        System.out.println("-----Back to Halfway-----");
        for (int i = 0; i < 3; i++)
            System.out.println(browser.goBack());

        System.out.println("-----Printing History-----");
        System.out.println(browser.showHistory()); 

        System.out.println("-----Add new page to clear fwd navigation-----");
        System.out.println(browser.visitWebsite(randomURL()));

        System.out.println("-----Attempting forward after overriding with new page-----");
        System.out.println(browser.goForward());

        System.out.println("-----Close browser-----");
        System.out.println(browser.closeBrowser());

        System.out.println("-----Printing an empty History-----");
        System.out.println(browser.showHistory());

        System.out.println("-----Restore sesion-----");
        System.out.println(browser.restoreLastSession());
        System.out.println("-----Printing restored History-----");
        System.out.println(browser.showHistory());

        System.out.println("-----Clear all data (fwd/back + history)-----");
        System.out.println(browser.clearBrowsingData());

        System.out.println("-----Attempting back/fwd on empty data-----");
        System.out.println(browser.goBack());
        System.out.println(browser.goForward());
    }
}
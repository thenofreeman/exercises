import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BrowserNavigation {
    private BrowserQueue<String> history = new BrowserQueue<>();
    private BrowserStack<String> backNavigation = new BrowserStack<>();
    private BrowserStack<String> forwardNavigation = new BrowserStack<>();

    private String currentPage = "Browser Homepage";
    private String sessionDataFilename = "session_data.txt";

    public BrowserNavigation() { }

    // emulates typing a string in the address bar
    public String visitWebsite(String url) {
        history.enqueue(url);

        if (currentPage != null) 
            backNavigation.push(currentPage);

        forwardNavigation.clear();

        currentPage = url;

        return "Now at " + currentPage;
    }

    // move down in the stack of backward history
    public String goBack() {
        if (backNavigation.isEmpty())
            return "No previous page available";

        forwardNavigation.push(currentPage);

        currentPage = backNavigation.pop();

        history.enqueue(currentPage);

        return "Now at " + currentPage;
    }

    // move up in the stack of forward history
    public String goForward() {
        if (forwardNavigation.isEmpty())
            return "No forward page available.";

        backNavigation.push(currentPage);
        currentPage = forwardNavigation.pop();

        history.enqueue(currentPage);

        return "Now at " + currentPage;
    }

    // get a string containing all of the history
    public String showHistory() {
        if (history.isEmpty())
            return "No browsing history available.";

        String historyString = "";

        for (String page : history)
            historyString += page + '\n';

        // strip to remove the extraneous final newline
        return historyString.stripTrailing();
    }

    // clear the history queue (not navigation stacks)
    public String clearHistory() {
        history.clear();

        return "Browsing history cleared.";
    }

    // clears ALL data. not just history
    public String clearBrowsingData() {
        history.clear();
        backNavigation.clear();
        forwardNavigation.clear();
        currentPage = "Browser Homepage";

        return "Browsing data cleared.";
    }

    // open a file to write session data and wipe current browsing history after saved
    public String closeBrowser() {
        try {

            try (FileOutputStream fos = new FileOutputStream(sessionDataFilename)) { 
                // truncate file and close
                fos.close();
            }

            try (FileWriter sessionData = new FileWriter(sessionDataFilename, true)) {

                sessionData.write("---SESSION-HISTORY---\n");
                for (String h : history) {
                    sessionData.write(h);
                    sessionData.write('\n');
                }

                sessionData.write("---SESSION-BACK---\n");
                for (String s : backNavigation) {
                    sessionData.write(s);
                    sessionData.write('\n');
                }

                sessionData.write("---SESSION-CURRENTPAGE---\n");
                if (currentPage != null) {
                    sessionData.write(currentPage);
                    sessionData.write('\n');
                }

                sessionData.write("---SESSION-FORWARD---\n");
                for (String s : forwardNavigation) {
                    sessionData.write(s);
                    sessionData.write('\n');
                }

                sessionData.write("---END---\n");

                sessionData.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        clearBrowsingData();

        return "Browser session saved.";
    }

    // for storing current heading to know which dsa to add line to from file
    private enum SessionDataSection {
        HISTORY, BACK, CURRENT, FORWARD, NONE,
    }

    // iterate through a file and add it to the current browsing session dsas
    public String restoreLastSession() {

        SessionDataSection currentSection = SessionDataSection.NONE;

        clearBrowsingData();

        if (!Files.exists(Paths.get(sessionDataFilename)))
            return "No previous session found.";

        try (BufferedReader sessionData = new BufferedReader(new FileReader(sessionDataFilename))) {
            String line;

            while ((line = sessionData.readLine()) != null) {
                switch (line) {
                    case "---SESSION-HISTORY---" 
                        -> currentSection = SessionDataSection.HISTORY;
                    case "---SESSION-BACK---" 
                        -> currentSection = SessionDataSection.BACK;
                    case "---SESSION-CURRENTPAGE---" 
                        -> currentSection = SessionDataSection.CURRENT;
                    case "---SESSION-FORWARD---" 
                        -> currentSection = SessionDataSection.FORWARD;
                    default -> { 
                        switch (currentSection) {
                            case SessionDataSection.HISTORY
                                -> history.enqueue(line);
                            case SessionDataSection.BACK
                                -> backNavigation.push(line);
                            case SessionDataSection.CURRENT
                                -> currentPage = line;
                            case SessionDataSection.FORWARD
                                -> forwardNavigation.push(line);
                            case SessionDataSection.NONE
                                -> { }
                            // no default, all possible enum values cased
                        }
                    }
                } 

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Previous session restored.";
    }

}

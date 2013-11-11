package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: artemlobachev
 * Date: 02.11.13
 */
public class AdminPage extends WebPageImp implements WebPage {
    private static final int REQ_TYPE_SHUTDOWN = 1;
    private int sleepBeworeShutdown;


    @Override
    public String handleGET(HttpServletRequest request){
        this.Status = HttpServletResponse.SC_OK;
        return WebPageImp.generatePage("admin.tml");
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return 0;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        String timeString = request.getParameter("shutdown");
        if (timeString != null) {
            this.sleepBeworeShutdown = Integer.valueOf(timeString);
            return REQ_TYPE_SHUTDOWN;
        }
        return 0;
    }

    @SuppressWarnings("UnusedDeclaration") //все по хитрому - он вызывается неявно используя рефлексию
    @CaseHandler(routine = REQ_TYPE_SHUTDOWN, reqType = RequestType.POST)
    public String shutdown(){
        ShutdownTask shutdownTask = new ShutdownTask(this.sleepBeworeShutdown);
        Thread shutdownTaskThread = new Thread(shutdownTask);
        shutdownTaskThread.start();
        return new String("Server will shut down after "+ this.sleepBeworeShutdown + " ms");
    }

    private class ShutdownTask implements Runnable {
        final int sleepTime;

        public ShutdownTask(int sleepTime) {
            super();
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();  //TODO make class Utils
            }
            System.out.append("Shutdown requested");
            System.exit(0);
        }
    }
}

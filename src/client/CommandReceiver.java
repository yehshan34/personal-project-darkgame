package client;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public interface CommandReceiver {
    public void receive(int serialNum, int commandCode, ArrayList<String> strs);
}

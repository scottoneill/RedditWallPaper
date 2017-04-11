package RedditWallpaper;

import com.sun.jna.platform.win32.WinDef;

public class WindowsOS implements OSState {
    
    @Override
    public void setBackground(String filePath) {
        WindowsAPI.INSTANCE.SystemParametersInfo(
                new WinDef.UINT_PTR(WindowsAPI.SPI_SETDESKWALLPAPER),
                new WinDef.UINT_PTR(0),
                filePath,
                new WinDef.UINT_PTR(WindowsAPI.SPIF_UPDATEINIFILE | WindowsAPI.SPIF_SENDWININICHANGE));
    }
}
